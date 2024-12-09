package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.PeopleFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.components.tiles.PersonTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

import java.util.Objects;

public class PeopleCategoryPage extends CategoryPageBase<Person> {

    private static final Image BANNER_IMAGE = new Image(Objects.requireNonNull(PeopleCategoryPage.class.getResource("people-banner.jpg")).toExternalForm());

    public PeopleCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - People";
    }

    @Override
    public String description() {
        return "A curated list of people connected to JavaFX. They develop libraries, applications, tools or they present at conferences and evangelise JavaFX.";
    }

    @Override
    protected String getCategoryDescription() {
        return "Meet influential JavaFX experts, columnists, and notable figures who have significantly contributed to the field, including Java champions.";
    }

    @Override
    protected CategoryHeader createCategoryHeader() {
        CategoryHeader header = super.createCategoryHeader();
        header.setBackgroundImage(BANNER_IMAGE);
        header.getStyleClass().add("dark-header");
        return header;
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
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<Person, TileViewBase<Person>> getTileViewProvider() {
        return PersonTileView::new;
    }

    @Override
    protected SearchFilterView<Person> createSearchFilterView() {
        return new PeopleFilterView();
    }

    @Override
    protected ObservableList<Person> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getPeople());
    }
}
