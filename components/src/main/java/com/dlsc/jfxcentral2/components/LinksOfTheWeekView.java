package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import com.dlsc.jfxcentral2.utils.WebAPIUtil;
import com.jpro.webapi.WebAPI;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;

public class LinksOfTheWeekView extends PaneBase {

    private final PaginationControl2 pagination;
    private final VBox linksBox;
    private Label dateLabel;
    private Node scrollAnchorNode;

    public LinksOfTheWeekView() {
        getStyleClass().add("links-of-the-week-view");

        linksBox = new VBox(createPaginationTitleBox());
        linksBox.getStyleClass().add("links-box");

        pagination = new PaginationControl2();
        pagination.getStyleClass().add("pagination");

        VBox contentBox = new VBox(linksBox, pagination);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

        createFactory();
        pagination.setPrefHeight(Region.USE_PREF_SIZE);
        selectedIndex.bind(pagination.currentPageIndexProperty());
        linksOfTheWeeksProperty().addListener((ob, ov, nv) -> createFactory());
        weekCountProperty().addListener((ob, ov, nv) -> createFactory());
        sizeProperty().addListener((ob, ov, nv) -> createFactory());
    }

    private HBox createPaginationTitleBox() {
        Button previousButton = new Button();
        previousButton.getStyleClass().add("prev-button");
        previousButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_LEFT_CIRCLE_FILL));
        previousButton.setMouseTransparent(false);
        previousButton.setOnAction(e -> goToPreviousPage());
        previousButton.disableProperty().bind(selectedIndexProperty().isEqualTo(0));
        previousButton.managedProperty().bind(previousButton.visibleProperty());
        previousButton.visibleProperty().bind(sizeProperty().map(s -> s != Size.LARGE));

        dateLabel = new Label();
        dateLabel.getStyleClass().add("date-label");
        dateLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(dateLabel, Priority.ALWAYS);

        Button nextButton = new Button();
        nextButton.getStyleClass().add("next-button");
        nextButton.setMouseTransparent(false);
        nextButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_RIGHT_CIRCLE_FILL));
        nextButton.setOnAction(e -> goToNextPage());
        nextButton.disableProperty().bind(Bindings.createBooleanBinding(() -> getSelectedIndex() == getLinksOfTheWeeks().size() - 1, selectedIndexProperty(), linksOfTheWeeks));

        nextButton.managedProperty().bind(nextButton.visibleProperty());
        nextButton.visibleProperty().bind(sizeProperty().map(s -> s != Size.LARGE));

        HBox paginationBox = new HBox(previousButton, dateLabel, nextButton);
        paginationBox.getStyleClass().add("pagination-title-box");
        paginationBox.managedProperty().bind(paginationBox.visibleProperty());

        return paginationBox;
    }

    private void createFactory() {
        pagination.setMaxPageIndicatorCount(isSmall() ? 2 : 3);
        List<LinksOfTheWeek> sortedList = getSortedList();
        pagination.setPageCount((int) Math.ceil((double) sortedList.size() / getWeekCount()));
        pagination.setPageFactory((pageIndex) -> {
            linksBox.getChildren().remove(1, linksBox.getChildren().size());
            int fromIndex = pageIndex * getWeekCount();
            int toIndex = Math.min(fromIndex + getWeekCount(), getLinksOfTheWeeks().size());
            List<LinksOfTheWeek> links = sortedList.subList(fromIndex, toIndex);
            links.forEach(week -> {
                dateLabel.setText(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(week.getCreatedOn()));

                CustomMarkdownView markdownView = new CustomMarkdownView();
                markdownView.setMdString(DataRepository.getInstance().getLinksOfTheWeekReadMe(week));
                markdownView.setPrefHeight(markdownView.getWidth());

                VBox weekBox = new VBox(markdownView);
                weekBox.getStyleClass().add("week-box");
                linksBox.getChildren().add(weekBox);
            });

            if (WebAPI.isBrowser()) {
                if (scrollAnchorNode != null) {
                    WebAPIUtil.scrollToTop(scrollAnchorNode, false);
                }
            } else {
                if (scrollAnchorNode == null) {
                    scrollAnchorNode = pagination.getScene().lookup(".links-of-the-week-header");
                }
                NodeUtil.scrollToNode(scrollAnchorNode, 80);
            }
            return null;
        });
        pagination.setCurrentPageIndex(0);
    }

    protected List<LinksOfTheWeek> getSortedList() {
        return DataRepository.getInstance().getLinksOfTheWeek()
                .stream()
                .sorted(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed())
                .toList();
    }

    private final IntegerProperty weekCount = new SimpleIntegerProperty(this, "weekCount", 1);

    public int getWeekCount() {
        return weekCount.get();
    }

    public IntegerProperty weekCountProperty() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount.set(weekCount);
    }

    private final ListProperty<LinksOfTheWeek> linksOfTheWeeks = new SimpleListProperty<>(this, "linksOfTheWeeks", FXCollections.observableArrayList());

    public ObservableList<LinksOfTheWeek> getLinksOfTheWeeks() {
        return linksOfTheWeeks.get();
    }

    public ListProperty<LinksOfTheWeek> linksOfTheWeeksProperty() {
        return linksOfTheWeeks;
    }

    public void setLinksOfTheWeeks(ObservableList<LinksOfTheWeek> linksOfTheWeeks) {
        this.linksOfTheWeeks.set(linksOfTheWeeks);
    }

    public void goToPage(int pageIndex) {
        pagination.setCurrentPageIndex(pageIndex);
    }

    public void goToNextPage() {
        int nextPageIndex = Math.min(pagination.getCurrentPageIndex() + 1, pagination.getPageCount() - 1);
        if (nextPageIndex != pagination.getCurrentPageIndex()) {
            pagination.setCurrentPageIndex(nextPageIndex);
        }
    }

    public void goToPreviousPage() {
        int previousPageIndex = Math.max(pagination.getCurrentPageIndex() - 1, 0);
        if (previousPageIndex != pagination.getCurrentPageIndex()) {
            pagination.setCurrentPageIndex(previousPageIndex);
        }
    }

    private final ReadOnlyIntegerWrapper selectedIndex = new ReadOnlyIntegerWrapper(this, "selectedIndex", 0);

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public ReadOnlyIntegerProperty selectedIndexProperty() {
        return selectedIndex.getReadOnlyProperty();
    }
}