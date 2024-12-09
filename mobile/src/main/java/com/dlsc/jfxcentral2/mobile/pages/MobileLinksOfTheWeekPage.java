package com.dlsc.jfxcentral2.mobile.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.MobilePageBase;
import com.dlsc.jfxcentral2.mobile.components.LotwPagination;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;

public class MobileLinksOfTheWeekPage extends MobilePageBase {

    private static final String DEFAULT_STYLE_CLASS = "lotw-page-view";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    public MobileLinksOfTheWeekPage(ObjectProperty<Size> size) {
        this();
        sizeProperty().bind(size);
    }

    public MobileLinksOfTheWeekPage() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        // top header
        MobilePageHeader header = new MobilePageHeader();
        header.setIcon(IkonUtil.getModelIkon(LinksOfTheWeek.class));

        // sort by date
        ArrayList<LinksOfTheWeek> sortedLinks = new ArrayList<>(DataRepository.getInstance().getLinksOfTheWeek());
        sortedLinks.sort(Comparator.comparing(LinksOfTheWeek::getCreatedOn).reversed());

        LotwPagination pagination = new LotwPagination();
        pagination.getItems().setAll(sortedLinks);
        pagination.setIndex(0);
        VBox.setVgrow(pagination, Priority.ALWAYS);

        // bind header title to item name
        header.titleProperty().bind(pagination.itemProperty().map(item -> {
            if (item == null) {
                return "Links of the Week";
            } else {
                return "Links of the Week" + " - " + DATE_FORMATTER.format(item.getCreatedOn());
            }
        }));

        getChildren().addAll(header, pagination);
    }

}
