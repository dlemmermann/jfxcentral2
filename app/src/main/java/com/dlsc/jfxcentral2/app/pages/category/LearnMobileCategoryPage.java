package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LearnMobileCategoryPage extends LearnCategoryPage {

    public LearnMobileCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }


    @Override
    public String title() {
        return "JFXCentral - Learn Mobile";
    }

    @Override
    public String description() {
        return "A curated list of tutorials covering JavaFX development on mobile platforms.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Learn Mobile";
    }

    @Override
    protected String getCategoryDescription() {
        return "Discover tutorials and guides on developing with JavaFX in the mobile domain, offering insights and techniques for mobile application development.";
    }

    @Override
    protected ObservableList<Learn> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getLearnMobile());
    }
}
