package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.service.AdminService;
import xiaozaiyi.crowd.util.ResultEntity;

/**
 * @author : Crazy_August
 * @description : 管理员 Controller
 * @Time: 2022-03-31   16:09
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("/admin/login")
    @ResponseBody
    public ResultEntity adminLogin(@RequestBody Admin admin) {
        Admin getAdmin = adminService.getAdminByLoginAcct(admin.getLoginAcct(), admin.getUserPswd());
        System.out.println(getAdmin);
        return ResultEntity.success("登录成功");
    }


    /**
     * 映射资源不存在
     *
     * @return
     */
    @RequestMapping("/")
    @ResponseBody
    public ResultEntity index() {
        return ResultEntity.error(404, CrowdConstant.MESSAGE_ERROR_404);
    }

}
