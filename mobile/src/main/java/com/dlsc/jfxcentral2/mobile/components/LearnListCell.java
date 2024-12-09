package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.components.AvatarView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class LearnListCell extends ListCell<Learn> {

    private static final String DEFAULT_STYLE_CLASS = "learn-list-cell";
    private final HBox authorBox;
    private final Label titleLabel;
    private final HBox container;

    public LearnListCell() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setPrefWidth(0);

        titleLabel = new Label();
        titleLabel.getStyleClass().add("title");
        titleLabel.setWrapText(true);

        authorBox = new HBox();
        authorBox.getStyleClass().add("author-box");

        container = new HBox(titleLabel, new Spacer(), authorBox);
        container.getStyleClass().add("container");
    }

    @Override
    protected void updateItem(Learn item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        titleLabel.setText(null);
        authorBox.getChildren().clear();

        if (empty || item == null) {
            setGraphic(null);
        } else {
            titleLabel.setText(item.getName());
            item.getPersonIds().forEach(id -> DataRepository.getInstance().getPersonById(id)
                    .ifPresent(person -> {
                        AvatarView avatarView = new AvatarView();
                        avatarView.setTooltip(new Tooltip(person.getName()));
                        avatarView.setMouseTransparent(true);
                        avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
                        authorBox.getChildren().add(avatarView);
                    }));

            setGraphic(container);
        }
    }
}
