package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.OSUtil;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;

public class ShowcaseOverviewBox extends OverviewBox<RealWorldApp> {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM yyyy");

    private Label locationLabel;
    private Label domainLabel;
    private Label createdByLabel;
    private Label createdOnLabel;

    public ShowcaseOverviewBox(RealWorldApp app) {
        super(app);
        getStyleClass().add("showcase-overview-box");
        fillData();
    }

    @Override
    protected Node createTopNode() {
        FieldGroup locationGroup = new FieldGroup("LOCATION", new String[]{"field-value"});
        locationLabel = locationGroup.getLabel();

        FieldGroup domainGroup = new FieldGroup("DOMAIN", new String[]{"field-value"});
        domainLabel = domainGroup.getLabel();

        FieldGroup createdByGroup = new FieldGroup("CREATED BY", new String[]{"field-value", "created-by-label"});
        createdByLabel = createdByGroup.getLabel();

        FieldGroup createdOnGroup = new FieldGroup("CREATED ON", new String[]{"field-value"});
        createdOnLabel = createdOnGroup.getLabel();

        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "realworld/" + getModel().getId());
        fillData();

        if (!isSmall()) {
            GridPane gridPane = new GridPane();
            gridPane.setVisible(!OSUtil.isAndroidOrIOS());
            gridPane.getStyleClass().add("top-grid");
            for (int i = 0; i < 4; i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setHalignment(HPos.LEFT);
                if (i == 0) {
                    columnConstraints.hgrowProperty().bind(locationLabel.visibleProperty().map(b -> b ? Priority.ALWAYS : Priority.NEVER));
                }else if (i == 1) {
                    columnConstraints.hgrowProperty().bind(domainLabel.visibleProperty().map(b -> b ? Priority.ALWAYS : Priority.NEVER));
                }else if (i == 2) {
                    columnConstraints.hgrowProperty().bind(createdByLabel.visibleProperty().map(b -> b ? Priority.ALWAYS : Priority.NEVER));
                } else {
                    columnConstraints.hgrowProperty().bind(createdOnLabel.visibleProperty().map(b -> b ? Priority.ALWAYS : Priority.NEVER));
                }
                gridPane.getColumnConstraints().add(columnConstraints);
            }
            for (int i = 0; i < 2; i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.TOP);
                gridPane.getRowConstraints().add(rowConstraints);
            }
            gridPane.add(locationGroup.getHeader(), 0, 0);
            gridPane.add(locationLabel, 0, 1);
            gridPane.add(domainGroup.getHeader(), 1, 0);
            gridPane.add(domainLabel, 1, 1);
            gridPane.add(createdByGroup.getHeader(), 2, 0);
            gridPane.add(createdByLabel, 2, 1);
            gridPane.add(createdOnGroup.getHeader(), 3, 0);
            gridPane.add(createdOnLabel, 3, 1);
            return gridPane;
        } else {
            VBox topBox = new VBox(
                    locationGroup.getHeader(),
                    locationLabel,
                    domainGroup.getHeader(),
                    domainLabel,
                    createdByGroup.getHeader(),
                    createdByLabel,
                    createdOnGroup.getHeader(),
                    createdOnLabel
            );
            topBox.setVisible(!OSUtil.isAndroidOrIOS());
            createdOnLabel.getStyleClass().add("last");
            topBox.getStyleClass().add("top-box");
            return topBox;
        }
    }

    private void fillData() {
        RealWorldApp app = getModel();
        if (app != null) {
            locationLabel.setText(app.getLocation());
            domainLabel.setText(app.getDomain());
            createdByLabel.setText(app.getCompany());
            if (app.getCreationOrUpdateDate() != null) {
                createdOnLabel.setText(app.getCreationOrUpdateDate().format(DEFAULT_DATE_FORMATTER));
            }
            setMarkdown(DataRepository.getInstance().getRealWorldReadMe(app));
        } else {
            locationLabel.setText("");
            domainLabel.setText("");
            createdByLabel.setText("");
            createdOnLabel.setText("");
            setMarkdown("");
        }
    }

    private static class FieldGroup {
        private final Label header;
        private final Label label;

        public FieldGroup(String headerText, String[] labelStyles) {
            header = new Label(headerText);
            header.getStyleClass().add("field-title");
            header.managedProperty().bind(header.visibleProperty());

            label = new Label();
            label.getStyleClass().addAll(labelStyles);
            label.setWrapText(true);
            label.managedProperty().bind(label.visibleProperty());
            label.visibleProperty().bind(label.textProperty().map(s -> !StringUtils.isEmpty(s) && !StringUtils.equalsIgnoreCase(s, "Unknown")));
            header.visibleProperty().bind(label.visibleProperty());
        }

        public Label getHeader() {
            return header;
        }

        public Label getLabel() {
            return label;
        }
    }
}
