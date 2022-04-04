package xiaozaiyi.crowd.controller;

import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.service.AdminService;
import xiaozaiyi.crowd.util.ResultEntity;

import java.util.List;
import java.util.Map;


/**
 * @author : Crazy_August
 * @description : 管理员 Controller
 * @Time: 2022-03-31   16:09
 */
@Controller
@ResponseBody
public class AdminController {

    @Autowired
    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/admin/login")
    public ResultEntity adminLogin(@RequestBody Admin admin) {
        Admin AdminBd = adminService.getAdminByLoginAcct(admin.getLoginAcct(), admin.getUserPswd());
        // 把 查询到的Admin保存到session中
        logger.info(AdminBd.toString());
        return ResultEntity.success("登录成功").add("sessionId", "123456");
    }

    @RequestMapping("/admin/list")
    public ResultEntity getAdminList(@RequestParam(value = "keyWord", defaultValue = "", required = false) String keyWord,
                                     @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "20", required = false) Integer pageSize) {
        logger.info(keyWord, pageNum, pageSize);
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyWord, pageNum, pageSize);
        return ResultEntity.success().add("record", pageInfo.getList())
                .add("pageNum", pageInfo.getPageNum())
                .add("pageSize", pageSize)
                .add("size", pageInfo.getSize())
                .add("total", pageInfo.getTotal());
    }

    @RequestMapping("admin/delete")
    public ResultEntity deleteAdmin(@RequestBody Map<String, Object> map) {
        List<Integer> ids = (List<Integer>) map.get("id"); // 获取前端传过来的ids
        logger.info(map.toString());
        System.out.println(ids);
        int i;
        // 判断是否是批量删除
        if (ids.size() > 1) {
            i = adminService.batchDeleteByIds(ids);
        } else {
            i = adminService.deleteAdmin(ids.get(0));
        }

        if (i > 0) {
            return ResultEntity.success(200, "删除成功");
        }
        return ResultEntity.error(100, "删除失败");
    }

    @RequestMapping("admin/update")
    public ResultEntity updateAdmin(@RequestBody Admin admin) {
        int i = adminService.updateAdmin(admin);
        if (i > 0) {
            return ResultEntity.success(200, "更新成功");
        }
        return ResultEntity.success(100, "更新失败");
    }

    @RequestMapping("admin/add")
    public ResultEntity addAdmin(@RequestBody Admin admin) {
        int i = adminService.addAdmin(admin);
        if (i > 0) {
            return ResultEntity.success(200, "添加成功");
        }
        return ResultEntity.success(100, "添加失败");
    }

    /**
     * 映射资源不存在
     *
     * @return
     */
    @RequestMapping("/")
    public ResultEntity index() {
        return ResultEntity.error(404, CrowdConstant.MESSAGE_ERROR_404);
    }


}
