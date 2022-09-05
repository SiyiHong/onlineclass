package onlineclass.service;

import onlineclass.model.entity.Video;
import onlineclass.model.entity.VideoBanner;

import java.util.List;

public interface VideoService {
    List<Video> listVideo();

    List<VideoBanner> listBanner();

    Video findById(int videoId);
}
