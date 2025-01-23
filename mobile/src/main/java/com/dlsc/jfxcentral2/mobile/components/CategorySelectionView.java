package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomToggleButton;
import com.dlsc.jfxcentral2.mobile.pages.MobileHomePage;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.MobileRouter;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Objects;

public class CategorySelectionView extends GridPane {

    private final ToggleGroup toggleGroup = new ToggleGroup();

    public CategorySelectionView(Runnable closeDrawer) {
        getStyleClass().add("categories-selection-view");

        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        CustomToggleButton homeButton = createButton("Home", MaterialDesign.MDI_HOME, PagePath.HOME);
        CustomToggleButton linksWeekButton = createButton("News", IkonUtil.getModelIkon(LinksOfTheWeek.class), PagePath.LINKS);
        CustomToggleButton showcasesButton = createButton("Apps", IkonUtil.getModelIkon(RealWorldApp.class), PagePath.SHOWCASES);
        CustomToggleButton libraryButton = createButton("Libs", IkonUtil.getModelIkon(Library.class), PagePath.LIBRARIES);
        CustomToggleButton peopleButton = createButton("People", IkonUtil.getModelIkon(Person.class), PagePath.PEOPLE);
        CustomToggleButton booksButton = createButton("Books", IkonUtil.getModelIkon(Book.class), PagePath.BOOKS);
        CustomToggleButton blogsButton = createButton("Blogs", IkonUtil.getModelIkon(Blog.class), PagePath.BLOGS);
        CustomToggleButton companiesButton = createButton("Companies", IkonUtil.getModelIkon(Company.class), PagePath.COMPANIES);
        CustomToggleButton docsButton = createButton("Docs", IkonUtil.getModelIkon(Documentation.class), PagePath.DOCUMENTATION);
        CustomToggleButton tutorialsButton = createButton("Tutorials", IkonUtil.getModelIkon(Tutorial.class), PagePath.TUTORIALS);
        CustomToggleButton toolsButton = createButton("Tools", IkonUtil.getModelIkon(Tool.class), PagePath.TOOLS);
        CustomToggleButton tipsButton = createButton("Tips", IkonUtil.getModelIkon(Tip.class), PagePath.TIPS);
        CustomToggleButton videosButton = createButton("Videos", IkonUtil.getModelIkon(Video.class), PagePath.VIDEOS);
        CustomToggleButton learnJavaFXButton = createButton("Learn JFX", IkonUtil.getModelIkon(LearnJavaFX.class), PagePath.LEARN_JAVAFX);
        CustomToggleButton learnMobileButton = createButton("Learn Mobile", IkonUtil.getModelIkon(LearnMobile.class), PagePath.LEARN_MOBILE);
        CustomToggleButton learnRaspberryPiButton = createButton("Learn Pi", IkonUtil.getModelIkon(LearnRaspberryPi.class), PagePath.LEARN_RASPBERRYPI);

        Node senaptNode = createNode("Sponsored by", "Senapt Ltd.", "senapt.png");
        Node gluonNode = createNode("Powered by", "Gluon", "gluon.png");

        senaptNode.setOnMousePressed(evt -> MobileLinkUtil.openWebLink("https://senapt.co.uk"));
        gluonNode.setOnMousePressed(evt -> MobileLinkUtil.openWebLink("https://gluonhq.com"));

        HBox logoBox = new HBox(10, senaptNode, gluonNode);
        logoBox.setMaxHeight(Region.USE_PREF_SIZE);
        logoBox.getStyleClass().add("logo-box");
        GridPane.setMargin(logoBox, new Insets(30, 0, 0, 0));

        for (int i = 0; i < 5; i++) {
            ColumnConstraints constraint = new ColumnConstraints();
            constraint.setPercentWidth(20);
            constraint.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(constraint);
        }

        GridPane.setColumnSpan(logoBox, 4);
        GridPane.setHalignment(logoBox, HPos.RIGHT);
        GridPane.setValignment(logoBox, VPos.CENTER);

        GridPane.setHgrow(videosButton, Priority.ALWAYS);
        GridPane.setHgrow(toolsButton, Priority.ALWAYS);
        GridPane.setHgrow(docsButton, Priority.ALWAYS);
        GridPane.setHgrow(tipsButton, Priority.ALWAYS);
        GridPane.setHgrow(tutorialsButton, Priority.ALWAYS);
        GridPane.setHgrow(booksButton, Priority.ALWAYS);
        GridPane.setHgrow(blogsButton, Priority.ALWAYS);
        GridPane.setHgrow(companiesButton, Priority.ALWAYS);
        GridPane.setHgrow(learnJavaFXButton, Priority.ALWAYS);
        GridPane.setHgrow(learnMobileButton, Priority.ALWAYS);
        GridPane.setHgrow(learnRaspberryPiButton, Priority.ALWAYS);

        GridPane.setVgrow(videosButton, Priority.ALWAYS);
        GridPane.setVgrow(toolsButton, Priority.ALWAYS);
        GridPane.setVgrow(docsButton, Priority.ALWAYS);
        GridPane.setVgrow(tipsButton, Priority.ALWAYS);
        GridPane.setVgrow(tutorialsButton, Priority.ALWAYS);
        GridPane.setVgrow(booksButton, Priority.ALWAYS);
        GridPane.setVgrow(blogsButton, Priority.ALWAYS);
        GridPane.setVgrow(companiesButton, Priority.ALWAYS);
        GridPane.setVgrow(learnJavaFXButton, Priority.ALWAYS);
        GridPane.setVgrow(learnMobileButton, Priority.ALWAYS);
        GridPane.setVgrow(learnRaspberryPiButton, Priority.ALWAYS);

        videosButton.setPrefWidth(0);
        toolsButton.setPrefWidth(0);
        docsButton.setPrefWidth(0);
        tipsButton.setPrefWidth(0);
        tutorialsButton.setPrefWidth(0);
        booksButton.setPrefWidth(0);
        blogsButton.setPrefWidth(0);
        companiesButton.setPrefWidth(0);
        learnJavaFXButton.setPrefWidth(0);
        learnMobileButton.setPrefWidth(0);
        learnRaspberryPiButton.setPrefWidth(0);

        add(homeButton, 0, 0);
        add(linksWeekButton, 1, 0);
        add(showcasesButton, 2, 0);
        add(libraryButton, 3, 0);
        add(peopleButton, 4, 0);

        add(videosButton, 0, 1);
        add(toolsButton, 1, 1);
        add(docsButton, 2, 1);
        add(tipsButton, 3, 1);
        add(tutorialsButton, 4, 1);

        add(learnJavaFXButton, 0, 2);
        add(learnRaspberryPiButton, 1, 2);
        add(learnMobileButton, 2, 2);
        add(booksButton, 3, 2);
        add(blogsButton, 4, 2);

        add(companiesButton, 0, 3);

        add(logoBox, 1, 3);

        toggleGroup.selectToggle(homeButton);
        toggleGroup.selectedToggleProperty().addListener(it -> closeDrawer.run());

        // listen to changes in the current path and update the selected button accordingly
        MobileRouter mobileRouter = MobileRouter.getInstance();
        mobileRouter.currentPathProperty().addListener(it ->
                toggleGroup.getToggles().forEach(btn -> {
                    CustomToggleButton button = (CustomToggleButton) btn;
                    if (StringUtils.equals(mobileRouter.getCurrentPath(), (String) button.getUserData())) {
                        if (!button.isSelected()) {
                            button.setSelected(true);
                        }
                    } else {
                        button.setSelected(false);
                    }
                })
        );
    }

    private Node createNode(String text1, String text2, String imageFileName) {
        ImageView logo = new ImageView(Objects.requireNonNull(CategorySelectionView.class.getResource(imageFileName)).toExternalForm());
        logo.setFitHeight(24);
        logo.setPreserveRatio(true);

        Label label1 = new Label(text1);
        Label label2 = new Label(text2);

        VBox vBox = new VBox(label1, label2);
        vBox.getStyleClass().add("vbox");

        HBox hBox = new HBox(logo, vBox);
        hBox.getStyleClass().add("hbox");

        return hBox;
    }

    private CustomToggleButton createButton(String text, Ikon icon, String path) {
        CustomToggleButton button = new CustomToggleButton();
        button.setPrefWidth(0);
        button.setText(text);
        button.setGraphic(new FontIcon(icon));
        button.setMaxWidth(Double.MAX_VALUE);
        button.setUserData(path);
        button.setOnMouseClicked(evt -> {
            if (evt.getButton().equals(MouseButton.PRIMARY) && evt.isStillSincePress()) {
                MobileHomePage.getInstance().setContentType(MobileHomePage.ContentType.NORMAL);
                MobileLinkUtil.getToPage(path);
            }
        });
        toggleGroup.getToggles().add(button);
        return button;
    }
}