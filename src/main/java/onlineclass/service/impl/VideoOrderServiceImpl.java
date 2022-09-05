package onlineclass.service.impl;

import onlineclass.exception.SelfException;
import onlineclass.mapper.EpisodeMapper;
import onlineclass.mapper.PlayRecordMapper;
import onlineclass.mapper.VideoMapper;
import onlineclass.mapper.VideoOrderMapper;
import onlineclass.model.entity.Episode;
import onlineclass.model.entity.PlayRecord;
import onlineclass.model.entity.Video;
import onlineclass.model.entity.VideoOrder;
import onlineclass.service.VideoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 下单操作
 * 未来版本：优惠券抵扣，风控用户检查，生成订单基础信息
 */
@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private VideoOrderMapper videoOrderMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private EpisodeMapper episodeMapper;

    @Autowired
    private PlayRecordMapper playRecordMapper;

    @Override
    @Transactional
    public int save(int userId, int videoId) {
        //查看用户是否已购买
        VideoOrder videoOrder = videoOrderMapper.checkState(userId,videoId,1);
        if(videoOrder!=null){
            return 0;
        }
        Video video = videoMapper.SimplefindById(videoId);
        VideoOrder newVideoOrder = new VideoOrder();
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        newVideoOrder.setState(1);
        newVideoOrder.setTotalFee(video.getPrice());
        newVideoOrder.setUserId(userId);
        newVideoOrder.setVideoId(videoId);
        newVideoOrder.setVideoImg(video.getCoverImg());//注入冗余字段
        newVideoOrder.setVideoTitle(video.getTitle());
        newVideoOrder.setCreateTime(new Date());
        int rows = videoOrderMapper.saveOrder(newVideoOrder);
        //生成播放记录，可以异步进行
        if(rows==1){
            Episode episode = episodeMapper.findFirstEpisode(videoId);
            if(episode==null){
                throw new SelfException(-1,"视频没有集信息");//抛出自定义异常
            }
            PlayRecord playRecord = new PlayRecord();
            playRecord.setCreateTime(new Date());
            playRecord.setEpisodeId(episode.getId());
            playRecord.setCurrentNum(1);
            playRecord.setUserId(userId);
            playRecord.setVideoId(videoId);
            playRecordMapper.saveRecord(playRecord);
        }
        return rows;
    }

    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {
        return videoOrderMapper.listOrderByUserId(userId);
    }
}
