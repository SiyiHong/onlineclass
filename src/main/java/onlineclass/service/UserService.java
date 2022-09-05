package onlineclass.service;

import onlineclass.model.entity.User;

import java.util.Map;

public interface UserService {
    int save(Map<String,String> userInfo);

    String findByPhoneAndPwd(String phone, String pwd);

    User findById(Integer userId);
//    User finByPhone(String phone);
}
