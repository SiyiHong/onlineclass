package onlineclass.controller;

import onlineclass.model.entity.VideoOrder;
import onlineclass.model.request.OrderRequest;
import onlineclass.service.VideoOrderService;
import onlineclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @RequestMapping("save")
    public JsonData saveOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        int rows = videoOrderService.save(userId,orderRequest.getVideoId());
        return rows==1?JsonData.buildSuccess():JsonData.buildError("下单失败");
    }
    //用户查询已购买视频列表
    @GetMapping("list")
    public JsonData listOrder(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        List<VideoOrder> videoOrderList = videoOrderService.listOrderByUserId(userId);
        return JsonData.buildSuccess(videoOrderList);
    }
}
