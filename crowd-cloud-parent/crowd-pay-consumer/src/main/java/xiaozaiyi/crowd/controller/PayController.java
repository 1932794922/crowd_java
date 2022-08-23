package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.service.Payservice;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.OrderVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-11   20:47
 */

@RequestMapping("payorder")
@RestController
public class PayController {

    @Autowired
    private Payservice payservice;


    @RequestMapping("creat/order")
    @ResponseBody
    public R<String> creatOrder(@RequestBody OrderVO orderVO,HttpServletRequest request) {
        R<String> OrderVOR = payservice.creatOrder(orderVO,request);
        boolean success = OrderVOR.isSuccess();
        if (!success) {
            return R.fail(OrderVOR.getMessage());
        }
        return R.data(OrderVOR.getData());
    }

    @RequestMapping("return")
    public R<String> returnUrlMethod(HttpServletRequest request) {
        R<String> OrderVOR = payservice.returnUrlMethod(request);
        boolean success = OrderVOR.isSuccess();
        if (!success) {
            return R.fail(OrderVOR.getMessage());
        }
        return R.data(OrderVOR.getData());
    }

    @RequestMapping("notify")
    public R<String> notifyUrlMethod(HttpServletRequest request) {
        R<String> OrderVOR = payservice.notifyUrlMethod(request);
        boolean success = OrderVOR.isSuccess();
        if (!success) {
            return R.fail(OrderVOR.getMessage());
        }
        return R.data(OrderVOR.getData());
    }

}
