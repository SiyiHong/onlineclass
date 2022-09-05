package onlineclass.mapper;

import onlineclass.model.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int save(User user);
    User findByPhone(@Param("phone") String phone);

    User findByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);

    User findById(@Param("user_id") Integer userId);
}
