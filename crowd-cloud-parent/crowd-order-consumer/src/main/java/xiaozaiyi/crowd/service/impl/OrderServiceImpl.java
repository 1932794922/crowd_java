package xiaozaiyi.crowd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaozaiyi.crowd.feign.IMysqlClientFeign;
import xiaozaiyi.crowd.service.OrderService;
import xiaozaiyi.crowd.util.CustomUtils;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.AddressVO;
import xiaozaiyi.crowd.vo.OrderProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-13   13:21
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IMysqlClientFeign iMysqlClientFeign;

    @Override
    public R<OrderProjectVO> getReturnConfirmInfo(Integer projectId) {
        R<OrderProjectVO> orderProjectVO =  iMysqlClientFeign.getReturnConfirmInfo(projectId);
        return orderProjectVO;
    }

    @Override
    public R<List<AddressVO>> getAddress(HttpServletRequest request) {
        // 1.获取token解析 获取当前登录id
        String memberId = CustomUtils.getJwt2Value(request);
        R<List<AddressVO>> AddressVO =  iMysqlClientFeign.getAddress(memberId);
        return AddressVO;
    }

    @Override
    public R<AddressVO> saveAddress(AddressVO addressVO, HttpServletRequest request) {
        // 1.获取token解析 获取当前登录id
        String memberId = CustomUtils.getJwt2Value(request);
        addressVO.setMemberId(Integer.valueOf(memberId));
        R<AddressVO> addressVO1 =  iMysqlClientFeign.saveAddress(addressVO);
        return addressVO1;
    }

    @Override
    public R<AddressVO> deleteAddress(Integer id) {
        R<AddressVO> addressVO =  iMysqlClientFeign.deleteAddress(id);
        return addressVO;
    }
}
