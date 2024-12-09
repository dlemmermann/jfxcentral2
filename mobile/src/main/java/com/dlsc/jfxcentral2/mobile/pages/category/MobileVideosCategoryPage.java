package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.mobile.components.ModelListCell;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileVideosCategoryPage extends MobileCategoryPageBase<Video> {

    public MobileVideosCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Video>, ListCell<Video>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Video item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().youTubeImageProperty(item));
            }
        };
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
    protected String getSearchPromptText() {
        return "Search for a video";
    }

    @Override
    protected ObservableList<Video> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getVideos());
    }
}
