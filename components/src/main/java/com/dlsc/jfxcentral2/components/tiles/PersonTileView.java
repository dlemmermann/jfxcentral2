package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.SocialLinksView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import com.jpro.webapi.WebAPI;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Region;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Collections;
import java.util.List;

public class PersonTileView extends SimpleTileView<Person> {

    private ContextMenu contextMenu;
    private MenuButton socialButton;

    public PersonTileView(Person person) {
        super(person);

        getStyleClass().add("person-tile-view");

        //add image for testing
        imageProperty().bind(ImageManager.getInstance().personImageProperty(person));

        setTitle(person.getName());
        setDescription(DataRepository.getInstance().getPersonReadMe(person));

        //add badges
        badgeBox.getChildren().clear();
        if (person.isChampion()) {
            Label championBadge = new Label("", new FontIcon(IkonUtil.champion));
            championBadge.getStyleClass().add("badge");
            championBadge.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getChildren().add(championBadge);
        }

        if (person.isRockstar()) {
            Label rockStartBadge = new Label("", new FontIcon(IkonUtil.rockstar));
            rockStartBadge.getStyleClass().add("badge");
            rockStartBadge.setMinWidth(Region.USE_PREF_SIZE);
            badgeBox.getChildren().add(rockStartBadge);
        }

        sizeUpdated();

        sizeProperty().addListener(it -> sizeUpdated());
    }

    @Override
    protected List<Node> createExtraNodes() {
        // We can not show the social button when running inside the browser, as the
        // click will go "through" the button and trigger the link on the tile, taking
        // the user to the details page of the person.
        if (WebAPI.isBrowser()) {
            return Collections.emptyList();
        }

        FontIcon graphic = new FontIcon(JFXCentralIcon.LINKS);
        graphic.getStyleClass().add("more-graphic");

        socialButton = new MenuButton();
        socialButton.setFocusTraversable(false);
        socialButton.getStyleClass().add("social-button");
        socialButton.setGraphic(graphic);

        int socialViewWidth = 302;

        Person person = getData();

        SocialLinksView socialLinksView = new SocialLinksView();
        socialLinksView.getStyleClass().add("person-social-links-view");
        socialLinksView.setPrefWidth(socialViewWidth);
        socialLinksView.setTwitterUrl(person.getTwitter());
        socialLinksView.setMastodonUrl(person.getMastodon());
        socialLinksView.setLinkedInUrl("https://www.linkedin.com/in/" + person.getLinkedIn());
        socialLinksView.setWebsiteUrl(person.getWebsite());
        socialLinksView.setGithubUrl(person.getGitHub());
        socialLinksView.setBlueskyUrl("https://bsky.app/profile/" + person.getBluesky());
        if (StringUtils.isNotBlank(person.getEmail().trim())) {
            socialLinksView.setMailUrl("mailto:" + person.getEmail());
        }

        contextMenu = new ContextMenu(new SeparatorMenuItem());
        contextMenu.getScene().setRoot(socialLinksView);
        contextMenu.showingProperty().addListener(it -> {
            if (contextMenu.isShowing()) {
                if (!socialButton.getStyleClass().contains("active")) {
                    socialButton.getStyleClass().add("active");
                }
            } else {
                socialButton.getStyleClass().remove("active");
            }
        });

        socialButton.setOnMousePressed(event -> {
            if (contextMenu.isShowing()) {
                contextMenu.hide();
            } else {
                contextMenu.show(socialButton, Side.BOTTOM, -socialViewWidth + socialButton.getWidth(), 10);
            }
        });

        return List.of(socialButton);
    }

    private void sizeUpdated() {
        if (socialButton != null) {
            socialButton.getStyleClass().remove("active");
            contextMenu.hide();
        }
        updateLinkedObjectBadges();
    }
}
