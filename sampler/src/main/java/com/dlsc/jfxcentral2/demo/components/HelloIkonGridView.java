package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import org.kordamp.ikonli.Ikon;

import java.util.List;

public class HelloIkonGridView extends JFXCentralSampleBase {

    private IkonGridView ikonGridView;

    @Override
    protected Region createControl() {
        ikonGridView = new IkonGridView();
        List<Ikon> ikonList = IkonliPackUtil.getInstance().getIkonList(DataRepository.getInstance().getIkonliPacks().get(0));
        ikonGridView.getItems().addAll(ikonList);
        return new ScrollPane(ikonGridView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        ikonGridView.sizeProperty().bind(sizeComboBox.valueProperty());
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "IkonGridView";
    }


}
