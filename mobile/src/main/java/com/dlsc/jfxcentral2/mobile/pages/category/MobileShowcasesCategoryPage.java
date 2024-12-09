package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.mobile.components.ModelListCell;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.images.CentralImageManager;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileShowcasesCategoryPage extends MobileCategoryPageBase<RealWorldApp> {

    public MobileShowcasesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<RealWorldApp>, ListCell<RealWorldApp>> cellFactory() {
       return param -> new ModelListCell<>() {
           @Override
           protected void handleImage(RealWorldApp app, ObjectProperty<Image> imageProperty) {
               imageProperty.set(CentralImageManager.getRealWorldAppBannerImage2(app));
           }
       };
    }

    @Override
    protected String getCategoryTitle() {
        return "Showcases";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(RealWorldApp.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for an application";
    }

    @Override
    protected ObservableList<RealWorldApp> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getRealWorldApps());
    }

}
