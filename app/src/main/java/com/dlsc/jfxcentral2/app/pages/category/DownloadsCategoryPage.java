package com.dlsc.jfxcentral2.app.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.app.pages.CategoryPageBase;
import com.dlsc.jfxcentral2.components.DownloadsBox;
import com.dlsc.jfxcentral2.components.filters.DownloadsFilterView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.tiles.DownloadTileView;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
    protected String getCategoryDescription() {
        return "Find downloadable resources for JavaFX development, including game demo apps, development tools, and more.";
    }

    @Override
    protected Callback<Download, TileViewBase<Download>> getTileViewProvider() {
        return download -> {
            DownloadTileView tileView = new DownloadTileView(download);
            tileView.getButton2().setOnAction(evt -> tileView.getOnShowDetailNode().run());
            return tileView;
        };
    }
    @Override
    protected SearchFilterView<Download> createSearchFilterView() {
        return new DownloadsFilterView();
    }

    @Override
    protected ObservableList<Download> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getDownloads());
    }

    @Override
    protected Callback<Download, Node> getDetailNodeProvider() {
        return  download -> {
            DownloadsBox downloadsBox = new DownloadsBox(download);
            downloadsBox.sizeProperty().bind(sizeProperty());
            return downloadsBox;
        };
    }
}
