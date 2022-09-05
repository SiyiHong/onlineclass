package onlineclass.service.impl;

import onlineclass.config.CacheKeyManager;
import onlineclass.model.entity.Video;
import onlineclass.model.entity.VideoBanner;
import onlineclass.mapper.VideoMapper;
import onlineclass.service.VideoService;
import onlineclass.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    @Override
    public List<Video> listVideo() {
        try {
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST_KEY,()->{
                System.out.println("从数据库里取结果");
                return videoMapper.listVideo();
            });
            if(cacheObj instanceof List){
                return (List<Video>) cacheObj;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;//可以返回兜底数据
    }

    @Override
    public List<VideoBanner> listBanner() {
        try {
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY,()->{
                System.out.println("从数据库里取结果");
                return videoMapper.listBanner();
            });
            if(cacheObj instanceof List){
                return (List<VideoBanner>) cacheObj;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Video findById(int videoId) {//复杂关联查询，包含章，集信息
        //针对每一个视频生成KEY
        String videoCacheKey = String.format(CacheKeyManager.VIDEO_DETAIL_KEY,videoId);
        try {
            Object cacheObj = baseCache.getOneHourCache().get(videoCacheKey,()->{
                System.out.println("从数据库里取结果");
                return videoMapper.findById(videoId);
            });
            if(cacheObj instanceof Video){
                return (Video) cacheObj;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
