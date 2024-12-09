package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Library;
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

public class MobileLibrariesCategoryPage extends MobileCategoryPageBase<Library> {

    public MobileLibrariesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Library>, ListCell<Library>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Library item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().libraryFeaturedImageProperty(item));
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Libraries";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Library.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a library";
    }

    @Override
    protected ObservableList<Library> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getLibraries());
    }
}
