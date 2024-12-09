package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.filters.ShowcaseFilterView;
import com.dlsc.jfxcentral2.components.tiles.AppTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class ShowcasesCategoryPage extends CategoryPageBase<RealWorldApp> {

    public ShowcasesCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Showcases";
    }

    @Override
    public String description() {
        return "A curated list of applications written in JavaFX.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Showcases";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(RealWorldApp.class);
    }

    @Override
    protected String getCategoryDescription() {
        return "Explore real-world JavaFX applications, notable for their professional functionality and aesthetically pleasing UI, demonstrating the practical versatility of JavaFX.";
    }

    @Override
    protected Callback<RealWorldApp, TileViewBase<RealWorldApp>> getTileViewProvider() {
        return AppTileView::new;
    }

    @Override
    protected SearchFilterView<RealWorldApp> createSearchFilterView() {
        return new ShowcaseFilterView();
    }

    @Override
    protected ObservableList<RealWorldApp> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getRealWorldApps());
    }
}
