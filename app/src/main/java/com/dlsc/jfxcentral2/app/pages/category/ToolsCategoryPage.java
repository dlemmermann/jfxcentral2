package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.ToolsFilterView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.components.tiles.ToolTileView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class ToolsCategoryPage extends CategoryPageBase<Tool> {

    public ToolsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Tools";
    }

    @Override
    public String description() {
        return "A curated list of tools for developing JavaFX applications.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Tools";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Tool.class);
    }

    @Override
    protected String getCategoryDescription() {
        return "Delve into our selection of JavaFX tools, including plugins, layout and CSS tools, testing, packaging, and more.";
    }

    @Override
    protected int getNumberOfGridViewRows() {
        return 5;
    }

    @Override
    protected Callback<Tool, TileViewBase<Tool>> getTileViewProvider() {
        return ToolTileView::new;
    }

    @Override
    protected SearchFilterView<Tool> createSearchFilterView() {
        return new ToolsFilterView();
    }

    @Override
    protected ObservableList<Tool> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getTools());
    }
}
