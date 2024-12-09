package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PageUtil;
import com.dlsc.jfxcentral2.utils.PlatformLinkUtil;
import com.dlsc.jfxcentral2.utils.SocialUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SimpleTileView: PersonTileView; ToolTileView; BlogTileView
 *
 * @param <T>
 */
public class SimpleTileView<T extends ModelObject> extends TileViewBase<T> {
    private final Button detailButton;
    protected HBox badgeBox = new HBox();
    protected SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();

    private final HBox linkedObjectBox;

    public SimpleTileView(T item) {
        super(item);

        getStyleClass().addAll("simple-tile-view");

        setLinkForItem(this, item);

        AvatarView avatarView = new AvatarView();
        avatarView.imageProperty().bind(imageProperty());
        avatarView.typeProperty().bind(avatarTypeProperty());
        avatarView.visibleProperty().bind(avatarView.imageProperty().isNotNull());
        avatarView.managedProperty().bind(avatarView.imageProperty().isNotNull());
        avatarView.setMouseTransparent(true);

        Label nameLabel = new Label();
        nameLabel.getStyleClass().add("title");
        nameLabel.textProperty().bind(titleProperty());
        if (item instanceof Library) {
            nameLabel.setGraphic(avatarView);
        }

        detailButton = new Button();
        detailButton.getStyleClass().add("detail-button");
        detailButton.setGraphic(new FontIcon(IkonUtil.link));
        detailButton.setFocusTraversable(false);
        setLinkForItem(detailButton, item);

        badgeBox.getStyleClass().add("badge-box");

        HBox topBox = new HBox(nameLabel, badgeBox, new Spacer(), detailButton);
        topBox.getStyleClass().add("top-box");

        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.textProperty().bind(descriptionProperty());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setAlignment(Pos.TOP_LEFT);
        descriptionLabel.setGraphic(new FontIcon(BootstrapIcons.CHAT_RIGHT_TEXT));

        VBox.setVgrow(descriptionLabel, Priority.ALWAYS);

        linkedObjectBox = new HBox();
        linkedObjectBox.getStyleClass().add("linked-object-box");

        HBox bottomBox = new HBox(linkedObjectBox, new Spacer());
        List<Node> extraNodes = createExtraNodes();
        if (extraNodes != null) {
            bottomBox.getChildren().addAll(extraNodes);
        }
        bottomBox.getStyleClass().add("bottom-box");

        VBox leftBox = new VBox(topBox, descriptionLabel, bottomBox);
        leftBox.getStyleClass().add("left-box");
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        HBox contentBox = new HBox(leftBox);
        if (!(item instanceof Library)) {
            contentBox.getChildren().add(0, avatarView);
        }
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    protected void setLinkForItem(Node target, ModelObject item) {
        if (item instanceof Documentation doc) {
            //The document link is external, and there's no detailed page.
            ExternalLinkUtil.setExternalLink(target, doc.getUrl());
        } else {
            PlatformLinkUtil.setLink(target, PageUtil.getLink(item));
        }
    }

    public Button getDetailButton() {
        return detailButton;
    }

    private final ObjectProperty<AvatarView.Type> avatarType = new SimpleObjectProperty<>(this, "avatarType", AvatarView.Type.CIRCLE);

    public AvatarView.Type getAvatarType() {
        return avatarType.get();
    }

    public ObjectProperty<AvatarView.Type> avatarTypeProperty() {
        return avatarType;
    }

    public void setAvatarType(AvatarView.Type avatarType) {
        this.avatarType.set(avatarType);
    }

    protected List<Node> createExtraNodes() {
        if (SocialUtil.isSocialFeaturesEnabled()) {
            saveSelectedProperty().addListener((ob, ov, nv) -> saveAndLikeButton.setSaveButtonSelected(nv));
            likeSelectedProperty().addListener((ob, ov, nv) -> saveAndLikeButton.setLikeButtonSelected(nv));
            saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, saved) -> {
                setSaveSelected(saved);
                if (getData() != null) {
                    System.out.println(MessageFormat.format("{0}: {1}", getData().getName(), saved ? "SELECTED:" : "UNSELECTED:"));
                }
            });

            saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, liked) -> {
                setLikeSelected(liked);
                if (getData() != null) {
                    System.out.println(MessageFormat.format("{0}: {1}", getData().getName(), liked ? "LIKED:" : "UNLIKED:"));
                }
            });

            return List.of(saveAndLikeButton);
        }
        return Collections.emptyList();
    }

    protected void updateLinkedObjectBadges() {
        //add linked objects
        linkedObjectBox.getChildren().clear();
        T data = getData();
        if (data == null) {
            return;
        }

        DataRepository dataRepository = DataRepository.getInstance();
        List<LinkedObjectBadge> linkedObjectBadge = List.of(
                new LinkedObjectBadge("Books", IkonUtil.getModelIkon(Book.class), dataRepository.getLinkedObjects(data, Book.class).size()),
                new LinkedObjectBadge("Apps", IkonUtil.getModelIkon(RealWorldApp.class), dataRepository.getLinkedObjects(data, RealWorldApp.class).size()),
                new LinkedObjectBadge("Libraries", IkonUtil.getModelIkon(Library.class), dataRepository.getLinkedObjects(data, Library.class).size()),
                new LinkedObjectBadge("Downloads", IkonUtil.getModelIkon(Download.class), dataRepository.getLinkedObjects(data, Download.class).size()),
                new LinkedObjectBadge("Tools", IkonUtil.getModelIkon(Tool.class), dataRepository.getLinkedObjects(data, Tool.class).size()),
                new LinkedObjectBadge("Videos", IkonUtil.getModelIkon(Video.class), dataRepository.getLinkedObjects(data, Video.class).size()),
                new LinkedObjectBadge("Tips", IkonUtil.getModelIkon(Tip.class), dataRepository.getLinkedObjects(data, Tip.class).size()),
                new LinkedObjectBadge("Learn", IkonUtil.getModelIkon(Learn.class),
                        dataRepository.getLinkedObjects(data, LearnJavaFX.class).size()
                                + dataRepository.getLinkedObjects(data, LearnMobile.class).size()
                                + dataRepository.getLinkedObjects(data, LearnRaspberryPi.class).size()

                ));

        AtomicInteger index = new AtomicInteger(0);
        int maxSize = isLarge() ? 3 : 2;
        linkedObjectBadge.stream().filter(item -> item.count() > 0).limit(maxSize).forEach(item -> {
            Label label = new Label(item.name() + "(" + item.count() + ")");
            label.getStyleClass().add("linked-badge");
            linkedObjectBox.getChildren().add(label);
            if (index.getAndIncrement() > maxSize - 2) {
                label.setText("...");
                label.getStyleClass().add("linked-badge-more");
            } else {
                label.setGraphic(new FontIcon(item.ikon));
            }
        });
    }

    public record LinkedObjectBadge(String name, Ikon ikon, int count) {
    }

    protected HBox getLinkedObjectBox() {
        return linkedObjectBox;
    }

}
