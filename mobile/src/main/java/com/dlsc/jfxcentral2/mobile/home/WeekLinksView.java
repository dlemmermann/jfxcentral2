package com.dlsc.jfxcentral2.mobile.home;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class WeekLinksView extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public WeekLinksView() {
        getStyleClass().add("week-links-view");

        // top part of the view
        Label title = new Label("Links of the week");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Miscellaneous stuff found on the web");
        subtitle.setWrapText(true);
        subtitle.getStyleClass().add("subtitle");

        VBox titleBox = new VBox(title, subtitle);
        titleBox.getStyleClass().add("title-box");
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Button viewAllButton = new Button();
        viewAllButton.getStyleClass().add("view-all-button");
        viewAllButton.setText("More");
        viewAllButton.setGraphic(new FontIcon("mdi-chevron-right"));
        viewAllButton.setFocusTraversable(false);
        MobileLinkUtil.setLink(viewAllButton, PagePath.LINKS);

        HBox topBox = new HBox(titleBox, new Spacer(), viewAllButton);
        topBox.setAlignment(Pos.CENTER);
        topBox.getStyleClass().add("top-box");

        // center part of the view
        CustomMarkdownView markdownView = new CustomMarkdownView();
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

        getChildren().setAll(topBox, markdownView);
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

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

}
