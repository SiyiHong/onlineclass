package onlineclass.mapper;

import onlineclass.model.entity.VideoOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoOrderMapper {
    //查询用户是否购买过
    VideoOrder checkState(@Param("user_id") int userId, @Param("video_id") int videoId, @Param("state") int state);
    int saveOrder(VideoOrder videoOrder);

    List<VideoOrder> listOrderByUserId(@Param("user_id") Integer userId);
}
