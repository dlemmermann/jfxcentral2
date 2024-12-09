package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class LearnPaginationBox extends HBox {
    public enum Position {
        TOP, RIGHT, BOTTOM, LEFT
    }

    public LearnPaginationBox(Learn learn, Position position, ObjectProperty<Size> sizeProperty) {
        getStyleClass().addAll("pagination-box", "learn-pagination-box", position.name().toLowerCase());

        List<? extends Learn> learnList;
        if (learn instanceof LearnJavaFX) {
            learnList = DataRepository.getInstance().getLearnJavaFX();
        } else if (learn instanceof LearnMobile) {
            learnList = DataRepository.getInstance().getLearnMobile();
        } else {
            learnList = DataRepository.getInstance().getLearnRaspberryPi();
        }

        Button previousButton = new Button();
        previousButton.getStyleClass().addAll("prev-button", "fill-button");
        previousButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_LEFT_CIRCLE_FILL));
        previousButton.textProperty().bind(sizeProperty.map(size -> size==Size.SMALL ? "Prev" : "Previous"));
        previousButton.setMouseTransparent(false);
        if (learnList.indexOf(learn) == 0) {
            previousButton.setDisable(true);
        } else {
            LinkUtil.setLink(previousButton, ModelObjectTool.getModelLink(learnList.get(learnList.indexOf(learn) - 1)));
        }

        Label pageLabel = new Label();
        pageLabel.getStyleClass().add("page-label");
        pageLabel.setText(String.format("%d / %d", learnList.indexOf(learn) + 1, learnList.size()));
        pageLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pageLabel, Priority.ALWAYS);

        Button nextButton = new Button("Next");
        nextButton.getStyleClass().addAll("next-button", "fill-button");
        nextButton.setMouseTransparent(false);
        nextButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_RIGHT_CIRCLE_FILL));
        if (learnList.indexOf(learn) == learnList.size() - 1) {
            nextButton.setDisable(true);
        } else {
            LinkUtil.setLink(nextButton, ModelObjectTool.getModelLink(learnList.get(learnList.indexOf(learn) + 1)));
        }

        getChildren().setAll(previousButton, pageLabel, nextButton);

        managedProperty().bind(visibleProperty());
        visibleProperty().bind(Bindings.createBooleanBinding(() -> {
            Size size = sizeProperty.get();
            if (position == Position.LEFT || position == Position.RIGHT) {
                return learnList.size() > 1 && size == Size.LARGE;
            } else if (position == Position.TOP) {
                return size != Size.LARGE;
            } else {
                return true;
            }
        }, sizeProperty));
    }
}
