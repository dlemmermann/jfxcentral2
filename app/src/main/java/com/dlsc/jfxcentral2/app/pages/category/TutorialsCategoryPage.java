package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.TutorialsFilterView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.TutorialTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class TutorialsCategoryPage extends CategoryPageBase<Tutorial> {

    public TutorialsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Tutorials";
    }

    @Override
    public String description() {
        return "A curated list of online tutorials covering JavaFX topics.";
    }

    @Override
    protected String getCategoryDescription() {
        return "Browse tutorials ranging from JavaFX basics to advanced application and game development.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Tutorials";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tutorial.class);
    }

    @Override
    protected Callback<Tutorial, TileViewBase<Tutorial>> getTileViewProvider() {
        return TutorialTileView::new;
    }

    @Override
    protected SearchFilterView<Tutorial> createSearchFilterView() {
        return new TutorialsFilterView();
    }

    @Override
    protected ObservableList<Tutorial> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getTutorials());
    }
}
