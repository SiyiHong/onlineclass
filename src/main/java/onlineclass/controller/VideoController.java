package onlineclass.controller;

import onlineclass.model.entity.Video;
import onlineclass.model.entity.VideoBanner;
import onlineclass.service.VideoService;
import onlineclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pub/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("banner")//轮播图
    public JsonData indexBanner(){

        List<VideoBanner> bannerList = videoService.listBanner();
        return JsonData.buildSuccess(bannerList);
    }

    @RequestMapping("list")
    public JsonData listVideo(){//列出所有视频
        List<Video> videoList = videoService.listVideo();
        return JsonData.buildSuccess(videoList);
    }

    @GetMapping("find_by_id")//查看视频详情
    public JsonData findById(@RequestParam(value = "video_id")int videoId){
        Video video = videoService.findById(videoId);
        return JsonData.buildSuccess(video);
    }
}
