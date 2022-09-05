package onlineclass.service.impl;

import onlineclass.model.entity.User;
import onlineclass.mapper.UserMapper;
import onlineclass.service.UserService;
import onlineclass.utils.CommonUtils;
import onlineclass.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(Map<String, String> userInfo) {
        User user = parseToUser(userInfo);
        if(user!=null){
            return userMapper.save(user);
        }else{
            return -1;
        }
    }

    @Override
    public String findByPhoneAndPwd(String phone, String pwd) {
        User user = userMapper.findByPhoneAndPwd(phone,CommonUtils.MD5(pwd));
        if(user==null){
            return null;
        }else{
            String token = JWTUtils.genToken(user);
            return token;
        }
    }

    @Override
    public User findById(Integer userId) {
        User user = userMapper.findById(userId);
        return user;
    }

    private User parseToUser(Map<String, String> userInfo) {//解析user对象
        if(userInfo.containsKey("phone")&&userInfo.containsKey("pwd")&&userInfo.containsKey("name")){
            User user = new User();
            user.setName(userInfo.get("name"));
            user.setHeadImg(getRandomImg());//随机头像
            user.setCreateTime(new Date());
            user.setPhone(userInfo.get("phone"));
            user.setPwd(CommonUtils.MD5(userInfo.get("pwd")));//md5加密
            return user;
        }else{
            return null;
        }
    }
    private static final String [] headImg = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };
    private String getRandomImg(){
        int size = headImg.length;
        Random random = new Random();
        int index = random.nextInt(size);
        return headImg[index];
    }
}
