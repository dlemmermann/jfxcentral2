package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.LibraryPreviewBox;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.model.NameProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public class LibraryOverviewBox extends PaneBase implements NameProvider {

    public LibraryOverviewBox(Library library) {
        getStyleClass().add("overview-box");

        Header header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(iconProperty());
        header.managedProperty().bind(header.visibleProperty());
        header.visibleProperty().bind(titleProperty().isNotEmpty().and(iconProperty().isNotNull()));

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "libraries/" + library.getId());
        markdownView.setMdString(DataRepository.getInstance().getLibraryReadMe(library));

        LibraryPreviewBox libraryPreviewBox = new LibraryPreviewBox(library);
        libraryPreviewBox.sizeProperty().bind(sizeProperty());
        VBox.setMargin(libraryPreviewBox, new Insets(20, 0, 0, 0));

        VBox bodyBox = new VBox(markdownView, libraryPreviewBox);
        bodyBox.getStyleClass().add("body-box");

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.getChildren().addAll(header, bodyBox);

        layoutBySize();
        getChildren().setAll(contentBox);
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "Overview");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final ObjectProperty<Ikon> icon = new SimpleObjectProperty<>(this, "icon", MaterialDesignP.PLAYLIST_EDIT);

    public Ikon getIcon() {
        return icon.get();
    }

    public ObjectProperty<Ikon> iconProperty() {
        return icon;
    }

    public void setIcon(Ikon icon) {
        this.icon.set(icon);
    }

    @Override
    public String getName() {
        return "Overview";
    }
}
