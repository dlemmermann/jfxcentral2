package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.LinksOfTheWeekView;
import com.dlsc.jfxcentral2.components.MenuView;
import com.dlsc.jfxcentral2.components.filters.SearchFilterView;
import com.dlsc.jfxcentral2.components.headers.LinksOfTheWeekHeader;
import com.dlsc.jfxcentral2.components.tiles.TileViewBase;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinksOfTheWeekPage extends CategoryPageBase<LinksOfTheWeek> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    public LinksOfTheWeekPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    public String title() {
        return "JFXCentral - Links of the Week";
    }

    @Override
    public String description() {
        return "Miscellaneous stuff found on the web that is related to JavaFX.";
    }

    @Override
    protected String getCategoryTitle() {
        return "Links of the Week";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(LinksOfTheWeek.class);
    }

    @Override
    public Node content() {

        // header
        LinksOfTheWeekHeader header = new LinksOfTheWeekHeader();
        header.sizeProperty().bind(sizeProperty());

        // links of the week view
        LinksOfTheWeekView linksOfTheWeekView = new LinksOfTheWeekView();
        linksOfTheWeekView.getLinksOfTheWeeks().setAll(DataRepository.getInstance().getLinksOfTheWeek());
        linksOfTheWeekView.sizeProperty().bind(sizeProperty());

        // this is a category page, but we still need to use the details content pane for layout purposes
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.getStyleClass().add("lotw-details-content-pane");
        detailsContentPane.sizeProperty().bind(sizeProperty());
        detailsContentPane.getCenterNodes().add(linksOfTheWeekView);

        detailsContentPane.setLeftTopExtraNode(createPaginationBox(linksOfTheWeekView));

        // this must be the last change to the details content pane, otherwise the menu shows wrong items
        detailsContentPane.getMenuView().getItems().setAll(createMenuItems(linksOfTheWeekView));

        linksOfTheWeekView.selectedIndexProperty().addListener((ob, ov, nv) -> {
            int size = detailsContentPane.getMenuView().getItems().size();
            if (nv.intValue() >= 0 && nv.intValue() < size) {
                detailsContentPane.getMenuView().setSelectedIndex(nv.intValue());
            } else {
                detailsContentPane.getMenuView().setSelectedIndex(-1);
            }
        });

        return wrapContent(header, detailsContentPane);
    }

    private HBox createPaginationBox(LinksOfTheWeekView linksOfTheWeekView) {
        Button previousButton = new Button();
        previousButton.getStyleClass().add("prev-button");
        previousButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_LEFT_CIRCLE_FILL));
        previousButton.setMouseTransparent(false);
        previousButton.setOnAction(e -> linksOfTheWeekView.goToPreviousPage());
        previousButton.disableProperty().bind(linksOfTheWeekView.selectedIndexProperty().isEqualTo(0));

        Label pageLabel = new Label();
        pageLabel.getStyleClass().add("page-label");
        pageLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            int index = linksOfTheWeekView.selectedIndexProperty().get();
            int size = linksOfTheWeekView.getLinksOfTheWeeks().size();
            return (index + 1) + " / " + size;
        }, linksOfTheWeekView.selectedIndexProperty(), linksOfTheWeekView.getLinksOfTheWeeks()));
        pageLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(pageLabel, Priority.ALWAYS);

        Button nextButton = new Button();
        nextButton.getStyleClass().add("next-button");
        nextButton.setMouseTransparent(false);
        nextButton.setGraphic(new FontIcon(BootstrapIcons.ARROW_RIGHT_CIRCLE_FILL));
        nextButton.setOnAction(e -> linksOfTheWeekView.goToNextPage());
        nextButton.disableProperty().bind(linksOfTheWeekView.selectedIndexProperty().isEqualTo(linksOfTheWeekView.getLinksOfTheWeeks().size() - 1));

        HBox paginationBox = new HBox(previousButton, pageLabel, nextButton);
        paginationBox.getStyleClass().add("pagination-box");
        paginationBox.managedProperty().bind(paginationBox.visibleProperty());
        paginationBox.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                linksOfTheWeekView.getLinksOfTheWeeks().size() > 1 && sizeProperty().get() == Size.LARGE,
                linksOfTheWeekView.getLinksOfTheWeeks(), sizeProperty())
        );
        return paginationBox;
    }

    @Override
    protected ObservableList<LinksOfTheWeek> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getLinksOfTheWeek());
    }

    @Override
    protected Callback<LinksOfTheWeek, TileViewBase<LinksOfTheWeek>> getTileViewProvider() {
        return null;
    }

    @Override
    protected SearchFilterView<LinksOfTheWeek> createSearchFilterView() {
        return null;
    }

    protected List<MenuView.Item> createMenuItems(LinksOfTheWeekView linksOfTheWeekView) {
        List<LinksOfTheWeek> linksOfTheWeek = DataRepository.getInstance().getLinksOfTheWeek();
        List<LinksOfTheWeek> weeks = new ArrayList<>(linksOfTheWeek);

        return weeks
                .stream()
                .sorted(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed())
                .limit(20)
                .map(links -> new MenuView.Item(DATE_FORMATTER.format(links.getCreatedOn()), null, null, () -> linksOfTheWeekView.goToPage(linksOfTheWeek.size() - linksOfTheWeek.indexOf(links) - 1)))
                .toList();
    }
}
