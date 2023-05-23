package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.filters.DownloadsFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.DownloadTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class DownloadsCategoryPage extends CategoryPageBase<Download> {

    public DownloadsCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Downloads";
    }

    @Override
    public String description() {
        return "A curated list of downloads with JavaFX showcase applications or development tools.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Downloads";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Download.class);
    }

    @Override
    protected Callback<Download, TileViewBase<Download>> getTileViewProvider() {
        return DownloadTileView::new;
    }

    @Override
    protected SearchFilterView createSearchFilterView() {
        return new DownloadsFilterView();
    }

    @Override
    protected ObservableList<Download> getCategoryItems() {
        return DataRepository.getInstance().getDownloads();
    }
}
