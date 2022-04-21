package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.entity.Admin;

import java.util.Map;


public interface LoginService {

    Map<String, Object> login(Admin admin);

    boolean logout();
}
