package com.dlsc.jfxcentral2.demo;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.components.TeamView;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class HelloTeamView extends JFXCentralSampleBase {

    private TeamView teamView;

    @Override
    protected Region createControl() {
        teamView = new TeamView();

        teamView.getMembers().setAll(DataRepository.getInstance().getMembers());
        return new ScrollPane(teamView);
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        teamView.sizeProperty().bind(sizeComboBox.valueProperty());
        sizeComboBox.valueProperty().addListener((ob, ov, nv) -> {
            teamView.setMaxWidth(nv.isLarge() ? 1080 : (nv.isMedium() ? 800 : 317));
        });
        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "TeamView";
    }
}
