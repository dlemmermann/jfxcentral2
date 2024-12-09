package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LearnJavaFXCategoryPage extends LearnCategoryPage {

    public LearnJavaFXCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Learn JavaFX";
    }

    @Override
    public String description() {
        return "A curated list of lesson covering JavaFX.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Learn JavaFX";
    }

    @Override
    protected String getCategoryDescription() {
        return "Explore JavaFX tutorials, ranging from basic usage to practical techniques, continuously updated to enrich your learning journey.";
    }

    @Override
    protected ObservableList<Learn> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getLearnJavaFX());
    }

}
