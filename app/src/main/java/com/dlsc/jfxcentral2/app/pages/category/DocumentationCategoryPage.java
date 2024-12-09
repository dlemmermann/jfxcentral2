package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.DocumentationFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.DocumentationTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class DocumentationCategoryPage extends CategoryPageBase<Documentation> {

    public DocumentationCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Documentation";
    }

    @Override
    public String description() {
        return "A curated list of documentation for JavaFX and its related topics.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Documentation";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Documentation.class);
    }

    @Override
    protected String getCategoryDescription() {
        return "Access comprehensive resources including JavaFX API documentation, plugin guides, CSS references, and default style sheets.";
    }

    @Override
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<Documentation, TileViewBase<Documentation>> getTileViewProvider() {
        return DocumentationTileView::new;
    }

    @Override
    protected SearchFilterView<Documentation> createSearchFilterView() {
        return new DocumentationFilterView();
    }

    @Override
    protected ObservableList<Documentation> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getDocumentation());
    }
}
