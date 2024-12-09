package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MobileLearnRaspberryPiCategoryPage extends MobileLearnCategoryPage {

    public MobileLearnRaspberryPiCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected String getCategoryTitle() {
        return "Learn Raspberry Pi";
    }

    @Override
    protected ObservableList<Learn> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getLearnRaspberryPi());
    }
}
