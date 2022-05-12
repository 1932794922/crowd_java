package xiaozaiyi.crowd.service.impl;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.feign.IMysqlClientFeign;
import xiaozaiyi.crowd.feign.IRedisClientFeign;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.service.MemberService;
import xiaozaiyi.crowd.util.JwtUtil;
import xiaozaiyi.crowd.util.SendUtil;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.MemberVO;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-24   23:29
 */
@Service
@Slf4j
public class MemberServiceImp implements MemberService {


    @Autowired
    private IRedisClientFeign iRedisClientFeign;

    @Autowired
    private IMysqlClientFeign iMysqlClientFeign;



    @Override
    public R<MemberVO> memberRegister(MemberVO memberVO) {

        // 0. 把 VO 转为 PO
        MemberPO memberPO = new MemberPO();

        BeanUtils.copyProperties(memberVO, memberPO);

        // 1.判断用户是否唯一
        R<MemberPO> memberPOByAcct = iMysqlClientFeign.getMemberPOByAcct(memberPO.getLoginAcct());
        boolean getMysqlSuccess = memberPOByAcct.isSuccess();
        if (!getMysqlSuccess) {
            //表示账号已经存在
            return R.fail(CustomConstant.ACCT_EXIST);
        }
        // 2.到 redis 查询验证码
        String REDIS_PREFIX = "PHONE_REDIS:";
        String redisPhoneCodeKey = REDIS_PREFIX + memberVO.getPhone();

        R<String> redisValueByKey = iRedisClientFeign.getRedisValueByKey(redisPhoneCodeKey);

        boolean getRedisSuccess = redisValueByKey.isSuccess();

        if (!getRedisSuccess) {
            // 在 redis中获取不到
            return R.fail(CustomConstant.PHONE_OR_CODE_EXIST);
        }
        // 3.比较验证码
        // 从redis中获取验证码
        String code = redisValueByKey.getData();
        if (!Objects.equals(memberVO.getCode(), code)) {
            return R.fail(CustomConstant.CODE_ERROR);
        }

        // 验证码正确,删除 redis 中的验证码
        R<String> deleteCode = iRedisClientFeign.removeRedisValueByKey(redisPhoneCodeKey);
        boolean deleteCodeSuccess = deleteCode.isSuccess();
        if (!deleteCodeSuccess) {
            return R.status(false);
        }
        // 4.密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(memberPO.getUserPassword());
        memberPO.setUserPassword(password);

        // 5.存储到数据库(调用 mysql-provider)
        R<MemberPO> memberPOR = iMysqlClientFeign.saveMemberPO(memberPO);

        boolean success = memberPOR.isSuccess();

        // 6.返回成功消息
        return R.status(success, CustomConstant.REGISTER_SUCCESS);
    }

    @Override
    public R<MemberVO> getPhoneCode(String phone) {
        Map<String, String> messageAndCodeMap = SendUtil.sendSmS(phone);
        if (messageAndCodeMap == null) {
            return R.fail(CustomConstant.CODE_SEND_ERROR);
        }
        // 获取验证码发送返回的状态信息
        String message = messageAndCodeMap.get("message");
        if (!"OK".equals(message)) {
            return R.fail(message);
        }
        String REDIS_PREFIX = "PHONE_REDIS:";
        String redisPhoneCodeKey = REDIS_PREFIX + phone;
        String code = messageAndCodeMap.get("code");
        String redisPhoneCodeValue = code;
        // 把验证码保存到 redis 中
        R<String> stringR = iRedisClientFeign.setRedisKeyValueWithTimeout(redisPhoneCodeKey, redisPhoneCodeValue, 300, TimeUnit.SECONDS);
        boolean success = stringR.isSuccess();
        if (!success) {
            return R.fail("验证码存储异常");
        }
        // 返回验证码
        MemberVO memberVO = new MemberVO();
        memberVO.setCode(code);
        memberVO.setPhone(phone);
        return R.data(memberVO, CustomConstant.CODE_SEND_SUCCESS);
    }

    @Override
    public R<MemberVO> memberLogin(MemberVO memberVO) {
        if (memberVO == null) {
            return R.status(false,CustomConstant.USERNAME_NOT_EXIST);
        }
        // 1. 把 VO 转为 PO
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);

        // 查询数据库
        R<MemberPO> memberPOR = iMysqlClientFeign.memberLoginByAcctPassword(memberPO);

        boolean success = memberPOR.isSuccess();
        if (!success) {
            return R.status(false,memberPOR.getMessage());
        }
        // 获取 登录用户 id
        Integer id = memberPOR.getData().getId();

        // 生成 jwt
        String jwt = JwtUtil.createJWT(id.toString());

        // 序列化用户信息保存到 redis
        String memberPORString = JSON.toJSONString(memberPOR.getData());

        String redisKey = CustomConstant.REDIS_PREFIX + id;

        // 保存到 redis
        iRedisClientFeign.setRedisKeyValueWithTimeout(redisKey,memberPORString,CustomConstant.EXPIRED_TIME,TimeUnit.MINUTES);
        // 返回给前端 token
        memberVO.setToken(jwt);
        return R.data(memberVO);
    }

    @Override
    public R<MemberVO> getMemberUser(String token) {
        String userId = null;
        try {
            // 获取 jwt 解析出用户 id;
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            R.fail(CustomConstant.IDENTITY_IS_OVERDUE);
        }
        String redisValueByUserId = CustomConstant.REDIS_PREFIX + userId;
        // 根据 id 查询 redis 用户信息
        R<String> MemberUser = iRedisClientFeign.getRedisValueByKey(redisValueByUserId);

        MemberPO memberPO = JSON.parseObject(String.valueOf(MemberUser.getData()), MemberPO.class);

        MemberVO memberVO = new MemberVO();
        BeanUtils.copyProperties(memberPO,memberVO);
        // 返回 用户 信息
        return R.data(memberVO);
    }

    @Override
    public R<MemberVO> memberLogout(String token) {
        String userId = null;
        try {
            // 获取 jwt 解析出用户 id;
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            R.fail(CustomConstant.IDENTITY_IS_OVERDUE);
        }
        String redisValueByUserId = CustomConstant.REDIS_PREFIX + userId;
        // 删除 redis 信息
        R<String> stringR = iRedisClientFeign.removeRedisValueByKey(redisValueByUserId);
        boolean success = stringR.isSuccess();
        if (!success){
            return R.fail(CustomConstant.LOGOUT_ERROR);
        }
        return R.success(CustomConstant.LOGOUT_SUCCESS);
    }
}
