package onlineclass.mapper;

import onlineclass.model.entity.Video;
import onlineclass.model.entity.VideoBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    List<Video> listVideo();//查询视频列表

    List<VideoBanner> listBanner();

    Video findById(@Param("video_id") int videoId);
    Video SimplefindById(@Param("video_id") int videoId);
}
