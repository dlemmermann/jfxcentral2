package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HelloIkonPackTileView extends JFXCentralSampleBase {

    private IkonliPackTileView ikonPackModelTileView;

    @Override
    protected Region createControl() {
        IkonliPack ikonPackModel = DataRepository.getInstance().getIkonliPacks().get(0);
        ikonPackModel.setDescription("Some description");

        ikonPackModelTileView = new IkonliPackTileView(ikonPackModel);
        return new StackPane(ikonPackModelTileView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        ikonPackModelTileView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "IconTileView";
    }
}
