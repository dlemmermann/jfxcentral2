package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;

public class MobilePersonDetailView extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "mobile-person-detail-view";
    private final SizeSupport sizeSupport = new SizeSupport(this);
    private final AvatarView avatarView;
    private final Label nameLabel;
    private final HBox badgeBox;
    private final Label descriptionLabel;

    public record LinkedObjectBadge(String name, Ikon ikon, int count) {
    }

    public MobilePersonDetailView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        backButton.setGraphic(new FontIcon(MaterialDesignA.ARROW_LEFT));
        MobileLinkUtil.setGoToBackLink(backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);

        avatarView = new AvatarView();
        StackPane topPane = new StackPane(backButton, avatarView);
        topPane.getStyleClass().add("top-pane");

        nameLabel = new Label();
        nameLabel.getStyleClass().add("name");

        badgeBox = new HBox();
        badgeBox.getStyleClass().add("badges");

        descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setWrapText(true);

        LinkedObjectsBox<Person> linkedObjectsBox = new LinkedObjectsBox<>();
        linkedObjectsBox.sizeProperty().bind(sizeProperty());
        linkedObjectsBox.dataProperty().bind(personProperty());

        VBox personContentBox = new VBox(nameLabel, badgeBox, descriptionLabel, linkedObjectsBox);
        personContentBox.getStyleClass().add("person-content-box");

        getChildren().addAll(topPane, personContentBox);

        personProperty().addListener(it -> updateView());
        updateView();
    }

    private void updateView() {
        if (avatarView.imageProperty().isBound()) {
            avatarView.imageProperty().unbind();
        }

        Person person = getPerson();
        if (person == null) {
            nameLabel.setText("");
            descriptionLabel.setText("");
            badgeBox.getChildren().clear();
            return;
        }

        // reset the avatar
        avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));

        // reset the text
        nameLabel.setText(person.getName());
        descriptionLabel.setText(DataRepository.getInstance().getPersonReadMe(person));

        // reset the boxes
        updateBadge(person);
    }

    private void updateBadge(Person person) {
        if (person.isChampion()) {
            Label championBadge = new Label("Champion", new FontIcon(IkonUtil.champion));
            championBadge.getStyleClass().addAll("badge-item", "champion");
            championBadge.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getChildren().add(championBadge);
        }

        if (person.isRockstar()) {
            Label rockstarBadge = new Label("Rockstar", new FontIcon(IkonUtil.rockstar));
            rockstarBadge.getStyleClass().addAll("badge-item", "rockstar");
            rockstarBadge.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getChildren().add(rockstarBadge);
        }
    }

    // Size

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    // person

    private final ObjectProperty<Person> person = new SimpleObjectProperty<>(this, "person");

    public final ObjectProperty<Person> personProperty() {
        return person;
    }

    public final Person getPerson() {
        return personProperty().get();
    }

    public final void setPerson(Person person) {
        personProperty().set(person);
    }

    // extra node

    private final ObjectProperty<Node> extraNode = new SimpleObjectProperty<>(this, "extraNode");

    public final ObjectProperty<Node> extraNodeProperty() {
        return extraNode;
    }

    public final Node getExtraNode() {
        return extraNode.get();
    }

    public final void setExtraNode(Node extraNode) {
        extraNodeProperty().set(extraNode);
    }
}
