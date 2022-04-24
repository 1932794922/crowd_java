package xiaozaiyi.crowd.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.util.api.R;

/**
 * 调用远程 mysql provider
 */
@FeignClient("august-mysql")
//浏览器请求到达这里
public interface IMysqlClient {

    @GetMapping("/client/member/login/acct")
    R<MemberPO> getMemberPOByLoginAcct(@RequestParam("loginAcct") String loginAcct);

}
