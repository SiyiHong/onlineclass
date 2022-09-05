package onlineclass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import onlineclass.utils.JWTUtils;
import onlineclass.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    //进入到controller之前的方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("token");
        if(accessToken==null){
            accessToken = request.getParameter("token");
        }
        if(StringUtils.isNotBlank(accessToken)){
            Claims claims = JWTUtils.checkJWT(accessToken);
            if(claims==null){
                sendJsonMsg(response, JsonData.buildError("登录失败"));
                return false;
            }
            Integer id = (Integer)claims.get("id");
            String name = (String) claims.get("name");
            request.setAttribute("user_id",id);//for what
            request.setAttribute("name",name);
            return true;
        }
        //此前可以加一个try catch方法
        sendJsonMsg(response, JsonData.buildError("登录失败"));
        return false;
    }
    public static void sendJsonMsg(HttpServletResponse response, Object obj){//响应Json数据给前端
        try{
            ObjectMapper objectMapper = new ObjectMapper();//用于将jsonData序列化
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
