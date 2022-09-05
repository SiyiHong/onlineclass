package onlineclass.controller;

import onlineclass.model.entity.User;
import onlineclass.model.request.LoginRequest;
import onlineclass.service.UserService;
import onlineclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("pri/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo){
        int rows = userService.save(userInfo);
        return rows==1? JsonData.buildSuccess():JsonData.buildError("注册失败，请重试");
    }

    @PostMapping("login")
    public JsonData login(@RequestBody LoginRequest loginRequest){
        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(),loginRequest.getPwd());
        return token==null?JsonData.buildError("登录失败，账户密码错误"):JsonData.buildSuccess(token);
    }

    @GetMapping("find_by_token")
    public JsonData findUserInfoByToken(HttpServletRequest request){//用户需携带token
        Integer userId = (Integer) request.getAttribute("user_id");
        if(userId==null){
            return JsonData.buildError("查询失败");
        }
        User user = userService.findById(userId);
        return JsonData.buildSuccess(user);
    }
}
