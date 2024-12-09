package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Company;
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

public class MobileCompaniesCategoryPage extends MobileCategoryPageBase<Company> {

    public MobileCompaniesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Company>, ListCell<Company>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Company item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().companyImageProperty(item));
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Companies";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Company.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a company";
    }

    @Override
    protected ObservableList<Company> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getCompanies());
    }
}
