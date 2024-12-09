package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import com.jpro.webapi.WebAPI;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

public class LearnTileView extends SimpleTileView<Learn> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LearnTileView(Learn learn) {
        super(learn);
        getStyleClass().addAll("learn-tile-view", "tool-tile-view");
        if (WebAPI.isBrowser()) {
            getStyleClass().add("browser");
        }
        saveAndLikeButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(learn));
        saveAndLikeButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(learn));
        setTitle(learn.getName());
        setDescription(DATE_FORMATTER.format(learn.getCreatedOn()));
        updateLinkedObjectBadges();

    }

    protected void updateLinkedObjectBadges() {
        HBox developmentStatusBox = getLinkedObjectBox();
        developmentStatusBox.getChildren().clear();
        Learn learn = getData();
        learn.getPersonIds().forEach(id -> DataRepository.getInstance().getPersonById(id)
                .ifPresent(person -> {
                    AvatarView avatarView = new AvatarView();
                    avatarView.setTooltip(new Tooltip(person.getName()));
                    avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
                    developmentStatusBox.getChildren().add(avatarView);
                }));
    }

}
