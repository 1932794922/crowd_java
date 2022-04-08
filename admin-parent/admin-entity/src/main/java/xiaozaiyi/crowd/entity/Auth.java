package xiaozaiyi.crowd.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限操作类
 */
@Data
public class Auth {
    private Integer id;

    private String name;

    private String label;

    private Integer categoryId;

    // 存放子节点
    private List<Auth> children;

    public Auth() {
    }

    public Auth(Integer id, String name, String label, Integer categoryId, List<Auth> children) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.categoryId = categoryId;
        this.children = children;
    }


    public List<Auth> getChildren() {
        if (children == null) {
            children = new ArrayList<Auth>();
        }
        return children;
    }
}