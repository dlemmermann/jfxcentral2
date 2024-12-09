package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Documentation;
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

public class MobileDocPage extends MobileCategoryPageBase<Documentation> {

    public MobileDocPage(ObjectProperty<Size> size) {
        super(size);
        getStyleClass().add("doc-category-page");
    }

    @Override
    protected Callback<ListView<Documentation>, ListCell<Documentation>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Documentation item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().documentationImageProperty(item));
            }

            @Override
            protected String getSummary(Documentation item) {
                return item.getDescription();
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Documentation";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Documentation.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a documentation";
    }

    @Override
    protected ObservableList<Documentation> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getDocumentation());
    }
}
