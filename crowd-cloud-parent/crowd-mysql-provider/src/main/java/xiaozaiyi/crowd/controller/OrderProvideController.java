package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.service.OrderService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.AddressVO;
import xiaozaiyi.crowd.vo.OrderProjectVO;

import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-13   13:26
 */
@RestController
public class OrderProvideController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/client/project/order/get")
    public R<OrderProjectVO> getReturnConfirmInfo(@RequestParam("projectId") Integer projectId) {
        R<OrderProjectVO> orderProjectVO = orderService.getReturnConfirmInfo(projectId);
        return orderProjectVO;
    }


    @RequestMapping("/client/project/order/address/get")
    public R<List<AddressVO>> getAddress(@RequestParam("memberId") String memberId){
        R<List<AddressVO>> AddressVO = orderService.getAddress(memberId);
        return AddressVO;
    }

    @RequestMapping("/client/project/order/address/save")
    R<AddressVO> saveAddress(@RequestBody AddressVO addressVO){
        R<AddressVO> addressVOR = orderService.saveAddress(addressVO);
        return addressVOR;
    }

    @RequestMapping("/client/project/order/address/delete")
    R<AddressVO> deleteAddress(@RequestParam("id") Integer id){
        R<AddressVO> addressVOR = orderService.deleteAddress(id);
        return addressVOR;
    }
}
