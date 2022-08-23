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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-13   13:12
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("launch/name/get")
    public R<OrderProjectVO> getReturnConfirmInfo(@RequestParam("projectId") Integer projectId) {
        R<OrderProjectVO> orderProjectVO = orderService.getReturnConfirmInfo(projectId);
        boolean success = orderProjectVO.isSuccess();
        if (!success) {
            return R.fail(orderProjectVO.getMessage());
        }
        return R.data(orderProjectVO.getData());
    }

    @RequestMapping("address/get")
    public R<List<AddressVO>> getAddress(HttpServletRequest request) {
        R<List<AddressVO>> addressVO = orderService.getAddress(request);
        boolean success = addressVO.isSuccess();
        if (!success) {
            return R.fail(addressVO.getMessage());
        }
        return R.data(addressVO.getData());
    }


    @RequestMapping("address/delete")
    public R<AddressVO> deleteAddress(@RequestParam(value = "id") Integer id) {
        R<AddressVO> addressVOR = orderService.deleteAddress(id);
        boolean success = addressVOR.isSuccess();
        return R.status(success);
    }

    @RequestMapping("address/save")
    public R<AddressVO> saveAddress(@RequestBody AddressVO addressVO, HttpServletRequest request) {
        R<AddressVO> addressVOR = orderService.saveAddress(addressVO, request);
        boolean success = addressVOR.isSuccess();
        if (!success) {
            return R.fail(addressVOR.getMessage());
        }
        return R.data(addressVOR.getData());
    }




}
