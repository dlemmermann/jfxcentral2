package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloPackGridView extends JFXCentralSampleBase {

    private ModelGridView<IkonliPack> gridView;

    @Override
    protected Region createControl() {

        gridView = new ModelGridView<>();
        gridView.setTileViewProvider(IkonliPackTileView::new);
        gridView.getItems().setAll(DataRepository.getInstance().getIkonliPacks());

        return new ScrollPane(gridView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        gridView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "ModelGridView<IkonPackModel>";
    }
}
