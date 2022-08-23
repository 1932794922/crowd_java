package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.AddressVO;
import xiaozaiyi.crowd.vo.OrderProjectVO;
import xiaozaiyi.crowd.vo.OrderVO;

import java.util.List;

public interface OrderService {
    R<OrderProjectVO> getReturnConfirmInfo(Integer projectId);

    R<List<AddressVO>> getAddress(String memberId);

    R<AddressVO> saveAddress(AddressVO addressVO);

    R<AddressVO> deleteAddress(Integer id);

    R<OrderVO> creatOrder(OrderVO orderVO);
}
