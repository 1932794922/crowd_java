package xiaozaiyi.crowd.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Admin implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String createTime;

    public Admin() {
    }

    public Admin(Integer id, String loginAcct, String userPswd, String userName, String email, String createTime) {
        this.id = id;
        this.loginAcct = loginAcct;
        this.userPswd = userPswd;
        this.userName = userName;
        this.email = email;
        this.createTime = createTime;
    }

}