package onlineclass.mapper;

import onlineclass.model.entity.Episode;
import org.apache.ibatis.annotations.Param;

public interface EpisodeMapper {
    Episode findFirstEpisode(@Param("video_id") int videoId);
}
