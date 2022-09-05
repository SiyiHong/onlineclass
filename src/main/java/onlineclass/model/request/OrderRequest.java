package onlineclass.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {//后续可以加优惠券等信息

    @JsonProperty("video_id")
    private int videoId;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}
