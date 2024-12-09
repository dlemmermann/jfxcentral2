package com.dlsc.jfxcentral2.components.headers;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.model.Badge;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class PersonDetailHeader extends DetailHeader<Person> {

    public PersonDetailHeader(Person person) {
        super(person);
        getStyleClass().add("person-detail-header");
        centerProperty().bind(Bindings.createObjectBinding(this::createCenterNode, sizeProperty()));

        setShareUrl("people/" + person.getId());
        setShareText("Found this person on @JFXCentral: " + person.getName());
        setShareTitle(person.getName());
        setBackText("ALL PEOPLE");
        setBackUrl(PagePath.PEOPLE);
    }

    private Pane createCenterNode() {
        Person person = getModel();

        AvatarView avatarImage = new AvatarView();
        avatarImage.setMouseTransparent(true);
        avatarImage.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
        FlowPane nameBadgePane = createNameBadgePane(person);

        Label descriptionLabel = new Label();
        descriptionLabel.setText(DataRepository.getInstance().getPersonReadMe(person));
        descriptionLabel.setWrapText(true);
        descriptionLabel.getStyleClass().add("description");

        FlowPane socialFlowPane = createSocialFlowPane(person);

        if (!isSmall()) {
            HBox contentBox = new HBox();
            contentBox.getStyleClass().add("content-box");
            VBox infoBox = new VBox(nameBadgePane, descriptionLabel, socialFlowPane);
            infoBox.getStyleClass().add("info-box");
            HBox.setHgrow(infoBox, Priority.ALWAYS);
            contentBox.getChildren().addAll(avatarImage, infoBox);
            contentBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            return contentBox;
        } else {
            VBox contentBox = new VBox();
            contentBox.getStyleClass().add("content-box");
            contentBox.getChildren().addAll(avatarImage, nameBadgePane, descriptionLabel, socialFlowPane);
            contentBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            return contentBox;
        }
    }

    private FlowPane createNameBadgePane(Person person) {
        FlowPane nameFlowPane = new FlowPane();
        nameFlowPane.getStyleClass().add("name-flow-pane");

        Label nameLabel = new Label(person.getName());
        nameLabel.getStyleClass().add("name");

        nameFlowPane.getChildren().add(nameLabel);
        List<Badge> badges = Badge.of(person);

        if (!badges.isEmpty()) {
            for (Badge badge : badges) {
                Label badgeLabel = new Label(badge.name(), new FontIcon(badge.icon()));
                badgeLabel.getStyleClass().add("badge");
                nameFlowPane.getChildren().add(badgeLabel);
            }
        }

        return nameFlowPane;
    }

    private FlowPane createSocialFlowPane(Person person) {
        FlowPane socialFlowPane = new FlowPane();
        socialFlowPane.getStyleClass().add("social-flow-pane");

        if (StringUtils.isNotBlank(person.getBluesky())) {
            Button blueskyLinkBtn = new Button("BLUESKY", new FontIcon(JFXCentralIcon.BLUESKY));
            blueskyLinkBtn.setFocusTraversable(false);
            blueskyLinkBtn.getStyleClass().addAll("bluesky-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(blueskyLinkBtn, "https://bsky.app/profile/" + person.getBluesky());
            socialFlowPane.getChildren().add(blueskyLinkBtn);
        }

        if (StringUtils.isNotBlank(person.getTwitter())) {
            Button twitterLinkBtn = new Button("TWITTER", new FontIcon(IkonUtil.twitter));
            twitterLinkBtn.setFocusTraversable(false);
            twitterLinkBtn.getStyleClass().addAll("twitter-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(twitterLinkBtn, "https://twitter.com/" + person.getTwitter());
            socialFlowPane.getChildren().add(twitterLinkBtn);
        }

        if (StringUtils.isNotBlank(person.getMastodon())) {
            Button mastodonLinkBtn = new Button("MASTODON", new FontIcon(IkonUtil.mastodon));
            mastodonLinkBtn.setFocusTraversable(false);
            mastodonLinkBtn.getStyleClass().addAll("mastodon-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(mastodonLinkBtn, person.getMastodon());
            socialFlowPane.getChildren().add(mastodonLinkBtn);
        }

        if (StringUtils.isNotBlank(person.getLinkedIn())) {
            Button linkedInLinkBtn = new Button("LINKEDIN", new FontIcon(IkonUtil.linkedin));
            linkedInLinkBtn.setFocusTraversable(false);
            linkedInLinkBtn.getStyleClass().addAll("linkedin-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(linkedInLinkBtn, "https://www.linkedin.com/in/" + person.getLinkedIn());
            socialFlowPane.getChildren().add(linkedInLinkBtn);
        }

        if (StringUtils.isNotBlank(person.getWebsite())) {
            Button websiteLinkBtn = new Button("WEBSITE", new FontIcon(IkonUtil.website));
            websiteLinkBtn.setFocusTraversable(false);
            websiteLinkBtn.getStyleClass().addAll("website-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(websiteLinkBtn, person.getWebsite());
            socialFlowPane.getChildren().add(websiteLinkBtn);
        }

        if (StringUtils.isNotBlank(person.getEmail())) {
            Button mailLinkBtn = new Button("MAIL", new FontIcon(IkonUtil.mail));
            mailLinkBtn.setFocusTraversable(false);
            mailLinkBtn.getStyleClass().addAll("mail-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(mailLinkBtn, "mailto:" + person.getEmail());
            socialFlowPane.getChildren().add(mailLinkBtn);
        }

        if (StringUtils.isNotBlank(person.getGitHub())) {
            Button githubLinkBtn = new Button("GITHUB", new FontIcon(IkonUtil.github));
            githubLinkBtn.setFocusTraversable(false);
            githubLinkBtn.getStyleClass().addAll("github-link-btn", "link-button");
            ExternalLinkUtil.setExternalLink(githubLinkBtn, "https://github.com/" + person.getGitHub());
            socialFlowPane.getChildren().add(githubLinkBtn);
        }

        return socialFlowPane;
    }
}
