package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tool;
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

public class MobileToolsCategoryPage extends MobileCategoryPageBase<Tool> {

    public MobileToolsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Tool>, ListCell<Tool>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Tool item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().toolImageProperty(item));
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Tools";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tool.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a tool";
    }

    @Override
    protected ObservableList<Tool> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getTools());
    }
}
