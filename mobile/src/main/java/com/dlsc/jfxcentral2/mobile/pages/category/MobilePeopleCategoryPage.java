package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
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
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

import java.util.function.Predicate;

public class MobilePeopleCategoryPage extends MobileCategoryPageBase<Person> {

    public MobilePeopleCategoryPage(ObjectProperty<Size> size) {
        super(size);
        getStyleClass().add("rounded-preview-page");
    }

    @Override
    protected Callback<ListView<Person>, ListCell<Person>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Person person, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().personImageProperty(person));
            }

            @Override
            protected String getSummary(Person person) {
                return DataRepository.getInstance().getPersonReadMe(person);
            }
        };
    }

    @Override
    protected Callback<String, Predicate<Person>> getFilter() {
        return text -> person -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(person.getName(), text)
                || StringUtils.containsIgnoreCase(DataRepository.getInstance().getPersonReadMe(person), text)
                || StringUtils.containsIgnoreCase(person.getDescription(), text);
    }

    @Override
    protected String getCategoryTitle() {
        return "People";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Person.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a JFX person";
    }

    @Override
    protected ObservableList<Person> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getPeople());
    }
}
