package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tutorial;
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

public class MobileTutorialsCategoryPage extends MobileCategoryPageBase<Tutorial> {

    public MobileTutorialsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Tutorial>, ListCell<Tutorial>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Tutorial item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().tutorialImageLargeProperty(item));
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Tutorials";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tutorial.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a tutorial";
    }

    @Override
    protected ObservableList<Tutorial> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getTutorials());
    }
}
