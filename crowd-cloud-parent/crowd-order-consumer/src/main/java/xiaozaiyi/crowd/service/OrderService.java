package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.AddressVO;
import xiaozaiyi.crowd.vo.OrderProjectVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    /**
     * 查询订单详情
     * @param projectId
     * @return
     */
    R<OrderProjectVO> getReturnConfirmInfo(Integer projectId);

    /**
     * 获取收货地址
     * @param request
     * @return
     */
    R<List<AddressVO>> getAddress(HttpServletRequest request);

    /**
     * 保存收获地址
     * @param addressVO
     * @param request
     * @return
     */
    R<AddressVO> saveAddress(AddressVO addressVO, HttpServletRequest request);

    /**
     * 删除收货地址
     * @param id
     * @return
     */
    R<AddressVO> deleteAddress(Integer id);



}
