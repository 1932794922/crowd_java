package xiaozaiyi.crowd.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-04   16:22
 */

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    private Integer id;

    private String name;

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
