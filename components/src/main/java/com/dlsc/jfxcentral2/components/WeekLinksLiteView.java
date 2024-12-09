package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class WeekLinksLiteView extends PaneBase {

    private final VBox contentBox;
    private final CustomMarkdownView markdownView;
    private final Label title;
    private final Label subtitle;
    private final Button viewAllButton;
    private Pane topBox;

    public WeekLinksLiteView() {
        getStyleClass().add("week-links-lite-view");
        title = new Label("Links of the week");
        title.getStyleClass().add("title");

        subtitle = new Label("Miscellaneous stuff found on the web");
        subtitle.setWrapText(true);
        subtitle.getStyleClass().add("subtitle");

        viewAllButton = new Button("VIEW ALL LINKS OF THE WEEK");
        viewAllButton.getStyleClass().add("view-all-button");
        viewAllButton.setFocusTraversable(false);
        LinkUtil.setLink(viewAllButton, "/links");

        markdownView = new CustomMarkdownView();
        markdownView.getStyleClass().add("md-view");
        markdownView.mdStringProperty().bind(Bindings.createStringBinding(() -> {
            LinksOfTheWeek linksOfTheWeek = getLinksOfTheWeek();
            if (linksOfTheWeek == null) {
                return "Error loading links of the week.";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
            String date = String.format("## Posted on %s%n%n", formatter.format(linksOfTheWeek.getCreatedOn()));
            String mdStr = DataRepository.getInstance().getLinksOfTheWeekReadMe(linksOfTheWeek);
            return date + mdStr;
        }, linksOfTheWeekProperty()));

        contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");

        updateUI();
    }

    @Override
    protected void layoutBySize() {
        if (isLgToMdOrMdToLg()) {
            return;
        }
        updateUI();
    }

    private void updateUI() {
        contentBox.getChildren().clear();
        if (topBox != null) {
            contentBox.getChildren().clear();
        }
        if (isSmall()) {
            topBox = new VBox(title, subtitle, viewAllButton);
            ((VBox)topBox).setAlignment(Pos.CENTER);
        } else {
            VBox titleBox = new VBox(title, subtitle);
            titleBox.getStyleClass().add("title-box");
            titleBox.setAlignment(Pos.CENTER_LEFT);
            topBox = new HBox(titleBox,new Spacer(), viewAllButton);
            ((HBox)topBox).setAlignment(Pos.CENTER);
        }
        topBox.getStyleClass().add("top-box");

        contentBox.getChildren().setAll(topBox, markdownView);
        getChildren().setAll(contentBox);
    }

    private final ObjectProperty<LinksOfTheWeek> linksOfTheWeek = new SimpleObjectProperty<>(this, "linksOfTheWeek");

    public LinksOfTheWeek getLinksOfTheWeek() {
        return linksOfTheWeek.get();
    }

    public ObjectProperty<LinksOfTheWeek> linksOfTheWeekProperty() {
        return linksOfTheWeek;
    }

    public void setLinksOfTheWeek(LinksOfTheWeek linksOfTheWeek) {
        this.linksOfTheWeek.set(linksOfTheWeek);
    }
}
