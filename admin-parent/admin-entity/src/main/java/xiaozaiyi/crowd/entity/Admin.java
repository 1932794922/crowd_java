package xiaozaiyi.crowd.entity;

import lombok.Data;

@Data
public class Admin {
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