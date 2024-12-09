package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LearnRaspberryPiCategoryPage extends LearnCategoryPage {

    public LearnRaspberryPiCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }


    @Override
    public String title() {
        return "JFXCentral - Learn Raspberry Pi";
    }

    @Override
    public String description() {
        return "A curated list of tutorials covering JavaFX development on Raspberry Pi.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Learn Raspberry Pi";
    }

    @Override
    protected String getCategoryDescription() {
        return "Explore tutorials focused on using JavaFX in Raspberry Pi development, providing guidance for creating JavaFX applications on this versatile platform.";
    }

    @Override
    protected ObservableList<Learn> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getLearnRaspberryPi());
    }
}
