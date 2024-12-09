package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.VideosFilterView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.VideoTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.VideoViewFactory;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class VideosCategoryPage extends CategoryPageBase<Video> {

    public VideosCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Videos";
    }

    @Override
    public String description() {
        return "A curated list of videos covering JavaFX topics. Some videos might show tutorials, others present final applications or tips and tricks.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Videos";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Video.class);
    }

    @Override
    protected String getCategoryDescription() {
        return "Browse JavaFX videos featuring conference talks, application demos, and tutorials, offering insights for all levels of JavaFX expertise.";
    }

    @Override
    protected SearchFilterView<Video> createSearchFilterView() {
        return new VideosFilterView();
    }

    @Override
    protected Callback<Video, Node> getDetailNodeProvider() {
        return video -> VideoViewFactory.createVideoViewNode(video, true);
    }

    @Override
    protected Callback<Video, TileViewBase<Video>> getTileViewProvider() {
        return video -> {
            VideoTileView tileView = new VideoTileView(video);
            tileView.getButton1().setOnAction(evt -> tileView.getOnShowDetailNode().run());
            return tileView;
        };
    }

    @Override
    protected ObservableList<Video> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getVideos());
    }
}
