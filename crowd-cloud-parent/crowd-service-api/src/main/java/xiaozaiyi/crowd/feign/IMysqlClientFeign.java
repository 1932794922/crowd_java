package xiaozaiyi.crowd.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.*;

import java.util.List;

/**
 * 调用远程 mysql provider
 */
@FeignClient("august-mysql")
//浏览器请求到达这里
public interface IMysqlClientFeign {

    /**
     * 用户登录操作
     *
     * @param loginAcct
     * @return
     */
    @GetMapping("/client/member/login/acct")
    R<MemberPO> getMemberPOByLoginAcct(@RequestParam("loginAcct") String loginAcct);

    /**
     * 判断登录账号是否存在
     *
     * @param loginAcct
     * @return
     */
    @GetMapping("/client/member/acct/exist")
    R<MemberPO> getMemberPOByAcct(@RequestParam("loginAcct") String loginAcct);


    /**
     * 保存注册信息
     *
     * @param memberPO
     * @return
     */
    @PostMapping("/client/member/save")
    R<MemberPO> saveMemberPO(@RequestBody MemberPO memberPO);


    @PostMapping("/client/member/login")
    R<MemberPO> memberLoginByAcctPassword(@RequestBody MemberPO memberPO);

    /**
     * 保存项目信息
     *
     * @param projectVO projectVO对象对象
     * @return 返回保存后的主键
     */
    @RequestMapping("/client/project/save")
    R<ProjectVO> saveProject(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);

    @RequestMapping("/client/project/type/get")
    R<List<ProjectTypeVO>> getProject();

    @RequestMapping("/client/project/detail/get")
    R<DetailProjectVO> queryProjectDetail(@RequestParam(value = "id") Integer id);

    @RequestMapping("/client/project/order/get")
    R<OrderProjectVO> getReturnConfirmInfo(@RequestParam("projectId") Integer projectId);

    @RequestMapping("/client/project/order/address/get")
    R<List<AddressVO>> getAddress(@RequestParam("memberId") String memberId);

    @RequestMapping("/client/project/order/address/save")
    R<AddressVO> saveAddress(@RequestBody AddressVO addressVO);

    @RequestMapping("/client/project/order/address/delete")
    R<AddressVO> deleteAddress(@RequestParam("id") Integer id);
}
