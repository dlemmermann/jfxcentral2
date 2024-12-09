package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.LibraryTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloLibraryTileView extends JFXCentralSampleBase {

    private LibraryTileView libraryTileView;

    @Override
    protected Region createControl() {
        Library library = DataRepository.getInstance().getLibraries().stream().filter(l -> l.getId().equalsIgnoreCase("FXGL")).findFirst().get();

        libraryTileView = new LibraryTileView(library);
        return new ScrollPane(libraryTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        libraryTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "LibraryTileView";
    }
}
