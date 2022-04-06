package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xiaozaiyi.crowd.constant.CrowdConstant;
import xiaozaiyi.crowd.entity.Menu;
import xiaozaiyi.crowd.service.MenuService;
import xiaozaiyi.crowd.util.ResultEntity;


/**
 * @author : Crazy_August
 * @description : 菜单控制器
 * @Time: 2022-04-06   14:33
 */
@Controller
@ResponseBody
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("list")
    public ResultEntity getAllMenu() {
        Menu menu = menuService.getAllMenus();
        return ResultEntity.success().add("records", menu);
    }

    @RequestMapping("add")
    public ResultEntity addMenu(@RequestBody Menu menu) {
        boolean success = menuService.saveMenu(menu);
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.ADD_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.ADD_SUCCESS);
    }

    @RequestMapping("update")
    public ResultEntity updateMenu(@RequestBody Menu menu) {
        boolean success = menuService.updateMenu(menu);
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.UPDATE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.UPDATE_SUCCESS);
    }

    @RequestMapping("delete")
    public ResultEntity deleteMenu(@RequestParam(value = "id", required = false) Integer id) {
        boolean success = menuService.deleteMenu(id);
        if (!success) {
            return ResultEntity.error(100, CrowdConstant.DELETE_FAILED);
        }
        return ResultEntity.success(200, CrowdConstant.DELETE_SUCCESS);
    }

}
