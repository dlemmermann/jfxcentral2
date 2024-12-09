package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.topcontent.TopContentView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloTopContentView extends JFXCentralSampleBase {

    private TopContentView<Tool> topContentView;

    @Override
    protected Region createControl() {

        topContentView = new TopContentView<>();

        topContentView.getItems().setAll(DataRepository.getInstance().getTools());

        return new ScrollPane(topContentView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        topContentView.sizeProperty().bind(sizeComboBox.valueProperty());
        topContentView.maxWidthProperty().bind(Bindings.createDoubleBinding(() ->
                switch (sizeComboBox.getValue()) {
            case SMALL -> 317d;
            case MEDIUM -> 600d;
            case LARGE -> 1080d;
        }, sizeComboBox.valueProperty()));
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "Top Content View";
    }
}
