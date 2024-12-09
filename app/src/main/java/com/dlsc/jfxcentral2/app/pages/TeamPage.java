package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.components.FeaturesContainer;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.StripView;
import com.dlsc.jfxcentral2.components.TeamView;
import com.dlsc.jfxcentral2.components.headers.CategoryHeader;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public class TeamPage extends PageBase {

    public TeamPage(ObjectProperty<Size> size) {
        super(size, Mode.LIGHT);
    }

    @Override
    public String title() {
        return "JFXCentral - Team";
    }

    @Override
    public String description() {
        return "JFXCentral2 main developers";
    }

    @Override
    public Node content() {

        CategoryHeader header = new CategoryHeader();
        header.setMode(Mode.LIGHT);
        header.sizeProperty().bind(sizeProperty());
        header.setTitle("Team");
        header.setIkon(JFXCentralIcon.TEAM);

        // team view
        TeamView teamView = new TeamView();
        teamView.sizeProperty().bind(sizeProperty());
        teamView.getMembers().setAll(DataRepository.getInstance().getMembers());

        // features
        FeaturesContainer featuresContainer = new FeaturesContainer();
        featuresContainer.sizeProperty().bind(sizeProperty());

        StripView stripView = new StripView(header, teamView,featuresContainer);
        stripView.sizeProperty().bind(sizeProperty());
        stripView.getStyleClass().add("simple-page-wrapper");

        return wrapContent(stripView);
    }
}
