package xiaozaiyi.crowd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.mapper.AddressMapper;
import xiaozaiyi.crowd.mapper.MemberLaunchInfoMapper;
import xiaozaiyi.crowd.mapper.OrderMapper;
import xiaozaiyi.crowd.po.AddressPO;
import xiaozaiyi.crowd.po.MemberLaunchInfoPO;
import xiaozaiyi.crowd.service.OrderService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.AddressVO;
import xiaozaiyi.crowd.vo.MemberLaunchInfoVO;
import xiaozaiyi.crowd.vo.OrderProjectVO;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-13   13:27
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberLaunchInfoMapper memberLaunchInfoMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public R<OrderProjectVO> getReturnConfirmInfo(Integer projectId) {
        try {
            LambdaQueryWrapper<MemberLaunchInfoPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MemberLaunchInfoPO::getProjectId, projectId);
            MemberLaunchInfoPO memberLaunchInfoPO = memberLaunchInfoMapper.selectOne(queryWrapper);
            OrderProjectVO orderProjectVO = new OrderProjectVO();
            MemberLaunchInfoVO memberLaunchInfoVO = new MemberLaunchInfoVO();
            BeanUtils.copyProperties(memberLaunchInfoPO, memberLaunchInfoVO);
            orderProjectVO.setMemberLaunchInfoVO(memberLaunchInfoVO);
            return R.data(orderProjectVO);
        } catch (Exception e) {
            log.error("获取项目发起人失败，项目id：{}", projectId);
            return R.fail("获取项目发起人失败");
        }

    }

    @Override
    public R<List<AddressVO>> getAddress(String memberId) {
        try {
            LambdaQueryWrapper<AddressPO> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
            objectLambdaQueryWrapper.eq(AddressPO::getMemberId, memberId);

            List<AddressPO> addressPOS = addressMapper.selectList(objectLambdaQueryWrapper);
            // 将AddressPO转换为AddressVO
            List<AddressVO> addressVOS = addressPOS.stream().map(addressPO -> {
                AddressVO addressVO = new AddressVO();
                BeanUtils.copyProperties(addressPO, addressVO);
                return addressVO;
            }).collect(java.util.stream.Collectors.toList());
            return R.data(addressVOS);
        } catch (Exception e) {
            log.error("获取地址失败，用户id：{}", memberId);
            return R.fail("获取地址失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public R<AddressVO> saveAddress(AddressVO addressVO) {
        try {
            if (addressVO == null) {
                return R.fail(CustomConstant.ADDRESS_IS_NULL);
            }
            AddressPO addressPO = new AddressPO();
            BeanUtils.copyProperties(addressVO, addressPO);
            addressMapper.insert(addressPO);
            Integer id = addressPO.getId();
            addressVO.setId(id);
            return R.data(addressVO);
        } catch (Exception e) {
            log.error("保存地址失败，用户id：{}", addressVO.getMemberId());
            return R.fail("保存地址失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public R<AddressVO> deleteAddress(Integer id) {
        try {
            addressMapper.deleteById(id);
            return R.success(CustomConstant.DELETE_SUCCESS);
        }catch (Exception e){
            log.error("删除地址失败，地址id：{}", id);
            return R.fail(CustomConstant.DELETE_FAILED);
        }
    }
}
