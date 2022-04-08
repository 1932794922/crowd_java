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
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("login")
    public ResultEntity adminLogin(@RequestBody Admin admin) {
        Admin AdminBd = adminService.getAdminByLoginAcct(admin.getLoginAcct(), admin.getUserPswd());
        // 把 查询到的Admin保存到session中
        logger.info(AdminBd.toString());
        return ResultEntity.success(CrowdConstant.LOGIN_SUCCESS).add("sessionId", "123456");
    }

    @RequestMapping("list")
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

    @RequestMapping("delete")
    public ResultEntity deleteAdmin(@RequestBody Map<String, List<Integer>> map) {
        List<Integer> ids = map.get("id"); // 获取前端传过来的ids
        boolean success;
        // 判断是否是批量删除
        if (ids.size() > 1) {
            success = adminService.batchDeleteByIds(ids);
        } else {
            success = adminService.deleteAdmin(ids.get(0));
        }
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.DELETE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.DELETE_SUCCESS);
    }

    @RequestMapping("update")
    public ResultEntity updateAdmin(@RequestBody Admin admin) {
        boolean success = adminService.updateAdmin(admin);
        if (!success) {
            return ResultEntity.success(100, CrowdConstant.UPDATE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.UPDATE_SUCCESS);
    }

    @RequestMapping("add")
    public ResultEntity addAdmin(@RequestBody Admin admin) {
        boolean success = adminService.addAdmin(admin);
        if (!success) {
            return ResultEntity.success(100, CrowdConstant.ADD_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.ADD_SUCCESS);
    }



}
