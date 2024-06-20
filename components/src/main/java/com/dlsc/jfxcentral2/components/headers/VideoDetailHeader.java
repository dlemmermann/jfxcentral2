package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.PagePath;

public class VideoDetailHeader extends SimpleDetailHeader<Video> {

    public VideoDetailHeader(Video video) {
        super(video);

        setShareUrl("videos/" + video.getId());
        setShareText("Found this video on @JFXCentral: " + video.getName());
        setShareTitle(video.getName());
        setBackText("ALL VIDEOS");
        setBackUrl(PagePath.VIDEOS);
    }
}
