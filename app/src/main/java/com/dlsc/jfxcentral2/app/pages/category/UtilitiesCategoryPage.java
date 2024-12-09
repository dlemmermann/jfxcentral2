package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.UtilitiesFilterView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.UtilityTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class UtilitiesCategoryPage extends CategoryPageBase<Utility> {

    public UtilitiesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Utilities";
    }

    @Override
    public String description() {
        return "Utilities for developing JavaFX applications.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Utilities";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Utility.class);
    }

    @Override
    protected String getCategoryDescription() {
        return """
                Access a suite of ready-to-use JavaFX development tools directly on this page. Explore \
                functionalities like SVG processing, gradient adjustment, effect generation, and style customization and more.
                """;
    }

    @Override
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<Utility, TileViewBase<Utility>> getTileViewProvider() {
        return UtilityTileView::new;
    }

    @Override
    protected SearchFilterView<Utility> createSearchFilterView() {
        return new UtilitiesFilterView();
    }

    @Override
    protected ObservableList<Utility> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getUtilities())
                .sorted((o1, o2) -> Boolean.compare(o2.isOnlineSupported(), o1.isOnlineSupported()));
    }
}
