package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.FeatureView;
import com.dlsc.jfxcentral2.model.Feature;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class FeatureViewSkin extends ControlBaseSkin<FeatureView> {

    public FeatureViewSkin(FeatureView control) {
        super(control);
        updateUI();
        control.featureProperty().addListener((observable, oldValue, newValue) -> updateUI());
    }

    protected void layoutBySize() {
        if (isLgToMdOrMdToLg()) {
            return;
        }
        updateUI();
    }

    private void updateUI() {
        Feature feature = getSkinnable().getFeature();

        Label tagLabel = NodeUtil.createLabel("tag");
        tagLabel.setText(feature.tagName());
        tagLabel.managedProperty().bind(tagLabel.visibleProperty());

        FontIcon typeIcon = new FontIcon();
        typeIcon.getStyleClass().add("type-icon");
        typeIcon.setIconCode(feature.type() == Feature.Type.VIDEO ? IkonUtil.play : IkonUtil.link);

        Label titleLabel = NodeUtil.createLabel("title");
        titleLabel.setText(feature.title());
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());

        HBox topBox = new HBox(NodeUtil.createVBox(tagLabel, titleLabel), new Spacer(), typeIcon);
        topBox.getStyleClass().add("top-box");

        CustomImageView imageView = null;
        if (feature.imageProperty() != null) {
            imageView = new CustomImageView();
            imageView.imageProperty().bind(feature.imageProperty());
            if (!getSkinnable().getStyleClass().contains("with-image")) {
                getSkinnable().getStyleClass().add("with-image");
            }
        }else {
            getSkinnable().getStyleClass().remove("with-image");
        }

        VBox bottomBox = new VBox();
        bottomBox.getStyleClass().add("bottom-box");

        Label descriptionLabel = NodeUtil.createLabel("description");
        descriptionLabel.setText(feature.description());
        descriptionLabel.setWrapText(true);
        bottomBox.getChildren().add(descriptionLabel);

        Label remarkLabel;
        if (!isSmall() && (feature.remark() != null || feature.remarkIcon() != null)) {
            remarkLabel = NodeUtil.createLabel("remark");
            remarkLabel.managedProperty().bind(remarkLabel.visibleProperty());
            remarkLabel.setText(feature.remark());
            remarkLabel.setGraphic(new FontIcon(feature.remarkIcon()));
            bottomBox.getChildren().add(remarkLabel);
        }

        VBox contentBox = NodeUtil.createVBox(topBox, imageView,new Spacer(), bottomBox);
        VBox.setVgrow(descriptionLabel, Priority.ALWAYS);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }
}
