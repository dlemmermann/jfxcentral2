package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.jpro.webapi.WebAPI;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.text.MessageFormat;

public class WelcomeView extends PaneBase {

    private final VBox labelBox;
    private final FlowPane flowPane;
    private final Button openJFXProjectButton;
    private final Node clientWebSwitchButton;
    private final Button jfxcentralDataButton;
    private final Button jfxCentralButton;
    private double xOffset;
    private double yOffset;

    public WelcomeView(boolean mobile) {
        getStyleClass().add("welcome-view");

        labelBox = new VBox();
        labelBox.getStyleClass().add("label-box");

        Label label1 = new Label("Home to anything");
        label1.getStyleClass().add("home-label");

        CustomImageView fxLogo = new CustomImageView();
        fxLogo.getStyleClass().add("fx-logo");

        Label label2 = new Label("related", fxLogo);
        label2.getStyleClass().add("related-label");

        String fxDesc = "JavaFX is an advanced GUI toolkit accessible from any JVM language, which runs on desktop, mobile and the web." + (WebAPI.isBrowser() ? " In fact, this website was written in JavaFX!" : "");
        Label jfxDescLabel = new Label(fxDesc);
        jfxDescLabel.getStyleClass().add("fx-description-label");
        jfxDescLabel.setWrapText(true);
        jfxDescLabel.setMinHeight(Region.USE_PREF_SIZE);
        jfxDescLabel.managedProperty().bind(jfxDescLabel.visibleProperty());

//        Label jfxCentralDescLabel = new Label("JFX Central is an open source project that you can find on GitHub, " +
//                "with its content in a separate data repository. Feel free to add your project, documentation, book, " +
//                "etc. via a simple pull-request!");
//        jfxCentralDescLabel.getStyleClass().add("description-label");
//        jfxCentralDescLabel.setWrapText(true);
//        jfxCentralDescLabel.setMinHeight(Region.USE_PREF_SIZE);

        String javaVersion = System.getProperty("java.version");
        String fxVersion = System.getProperty("javafx.runtime.version");

        Label versionLabel = new Label(MessageFormat.format("This {0} runs on Java " + javaVersion + " with JavaFX " + fxVersion + ".", WebAPI.isBrowser() ? "site" : OSUtil.isAndroidOrIOS() ? "app" : "application"));
        versionLabel.getStyleClass().add("version-label");
        versionLabel.setWrapText(true);

        labelBox.getChildren().addAll(label1, label2, jfxDescLabel, versionLabel);
        labelBox.setMinHeight(Region.USE_PREF_SIZE);

        flowPane = new FlowPane();
        flowPane.getStyleClass().add("flow-pane");

        jfxCentralButton = new Button("jfxcentral");
        jfxCentralButton.setFocusTraversable(false);
        jfxCentralButton.setGraphic(new FontIcon(IkonUtil.github));
        jfxCentralButton.getStyleClass().addAll("transparent-button", "jfxcentral-button");
        ExternalLinkUtil.setExternalLink(jfxCentralButton, "https://github.com/dlsc-software-consulting-gmbh/jfxcentral2");

        jfxcentralDataButton = new Button("jfxcentral-data", new FontIcon(IkonUtil.github));
        jfxcentralDataButton.getStyleClass().addAll("transparent-button", "jfxcentral-data-button");
        jfxcentralDataButton.setFocusTraversable(false);
        ExternalLinkUtil.setExternalLink(jfxcentralDataButton, "https://github.com/dlsc-software-consulting-gmbh/jfxcentral-data");

        Region graphicRegion = new Region();
        if (true) {
            clientWebSwitchButton = new MenuButton();

            graphicRegion.getStyleClass().add("download-region");
            HBox graphic = new HBox(new Label("Install locally"), graphicRegion);
            graphic.getStyleClass().add("install-graphic");
            ((MenuButton) clientWebSwitchButton).setGraphic(graphic);

            MenuItem downloadMenuItem = createMenuItem("Desktop", "https://downloads.hydraulic.dev/jfxcentral2/download.html", MaterialDesign.MDI_DESKTOP_MAC);

            MenuItem downloadMobileMenuItem = createMenuItem("Apple Store", "https://apps.apple.com/jp/app/jfxcentral/id1613971561", MaterialDesign.MDI_APPLE);

            // TODO: Add Google Play link
            MenuItem downloadGoogleMenuItem = createMenuItem("Google Play", "https://play.google.com/store/apps", MaterialDesign.MDI_GOOGLE_PLAY);

            ((MenuButton) clientWebSwitchButton).getItems().addAll(downloadMenuItem, downloadMobileMenuItem, downloadGoogleMenuItem);

        } else {
            clientWebSwitchButton = new Button("Online Version", graphicRegion);
            graphicRegion.getStyleClass().add("openjfx-region");
            ExternalLinkUtil.setExternalLink(clientWebSwitchButton, "https://www.jfx-central.com/");
        }
        clientWebSwitchButton.getStyleClass().addAll("fill-button", "install-button");
        clientWebSwitchButton.setFocusTraversable(false);
        clientWebSwitchButton.setVisible(!mobile);
        clientWebSwitchButton.setManaged(!mobile);

        Region openjfxRegion = new Region();
        openjfxRegion.getStyleClass().add("openjfx-region");
        openJFXProjectButton = new Button("OpenJFX Project", openjfxRegion);
        openJFXProjectButton.getStyleClass().addAll("fill-button", "openjfx-button");
        openJFXProjectButton.setFocusTraversable(false);
        LinkUtil.setLink(openJFXProjectButton, PagePath.OPENJFX);
        setMinHeight(Region.USE_PREF_SIZE);

        if (!WebAPI.isBrowser()) {
            setOnMousePressed(event -> {
                xOffset = getScene().getWindow().getX() - event.getScreenX();
                yOffset = getScene().getWindow().getY() - event.getScreenY();
            });

            setOnMouseDragged(event -> {
                getScene().getWindow().setX(event.getScreenX() + xOffset);
                getScene().getWindow().setY(event.getScreenY() + yOffset);
            });
        }

        layoutBySize();
    }

    protected void layoutBySize() {
        if (isMedium()) {
            flowPane.getChildren().setAll(jfxCentralButton, clientWebSwitchButton, jfxcentralDataButton, openJFXProjectButton);
        } else {
            flowPane.getChildren().setAll(jfxCentralButton, jfxcentralDataButton, clientWebSwitchButton, openJFXProjectButton);
        }
        Pane content = isLarge() ? new HBox() : new VBox();
        content.getStyleClass().add("content");
        content.getChildren().setAll(labelBox, flowPane);
        getChildren().setAll(content);
    }

    private MenuItem createMenuItem(String text, String url, Ikon ikon) {
        Label label = new Label(text, ikon != null ? new FontIcon(ikon) : null);
        StackPane wrapper = new StackPane(label);
        LinkUtil.setExternalLink(label, url);
        return new CustomMenuItem(wrapper);
    }
}
