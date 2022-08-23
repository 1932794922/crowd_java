package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.OrderVO;

import javax.servlet.http.HttpServletRequest;

public interface Payservice {
    R<String> creatOrder(OrderVO orderVO,HttpServletRequest request);

    R<String> returnUrlMethod(HttpServletRequest request);

    R<String> notifyUrlMethod(HttpServletRequest request);
}
