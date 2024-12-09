package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tip;
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

public class MobileTipCategoryPage extends MobileCategoryPageBase<Tip> {

    public MobileTipCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Tip>, ListCell<Tip>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Tip item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().tipBannerImageProperty(item));
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Tips & Tricks";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tip.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for tips and tricks";
    }

    @Override
    protected ObservableList<Tip> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getTips());
    }
}
