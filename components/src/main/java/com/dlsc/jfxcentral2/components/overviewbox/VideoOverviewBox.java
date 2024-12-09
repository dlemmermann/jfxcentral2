package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class VideoOverviewBox extends SimpleOverviewBox<Video> {

    public VideoOverviewBox(Video video) {
        super(video);
        getStyleClass().add("video-overview-box");

        // there is no markdown for videos, but we might add it in the future,
        // so let's specify the base url ... just to be sure
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "videos/" + video.getId());
    }

    @Override
    protected Node createTopNode() {
        return VideoViewFactory.createVideoViewNode(getModel());
    }

    @Override
    protected Node createBottomNode() {
        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.setMdString(getModel().getDescription());
        return markdownView;
    }
}
