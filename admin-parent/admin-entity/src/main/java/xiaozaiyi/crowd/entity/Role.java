package xiaozaiyi.crowd.entity;

import lombok.Data;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-04   16:22
 */

@Data
public class Role {

    private Integer id;

    private String name;

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
