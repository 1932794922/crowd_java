package xiaozaiyi.crowd.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    private Integer id;

    private Integer pid;

    private String name;

    private String router;
    // 存放子节点
    private List<Menu> children;

    public Menu() {

    }

    public Menu(Integer id, Integer pid, String name, String router, List<Menu> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.router = router;
        this.children = children;
    }

    public List<Menu> getChildren() {
        if (children == null) {
            children = new ArrayList<Menu>();
        }
        return children;
    }

}