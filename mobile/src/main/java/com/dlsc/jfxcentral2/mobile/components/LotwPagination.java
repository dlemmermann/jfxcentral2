package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ScrollPane;

public class LotwPagination extends MobilePagination<LinksOfTheWeek> {

    private static final String DEFAULT_STYLE_CLASS = "lotw-pagination";
    private final ScrollPane scrollPane = new ScrollPane();

    public LotwPagination() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.mdStringProperty().bind(Bindings.createStringBinding(() -> {
            LinksOfTheWeek lotw = getItem();
            if (lotw == null) {
                return "";
            }
            return DataRepository.getInstance().getLinksOfTheWeekReadMe(lotw);
        }, itemProperty()));

        scrollPane.setContent(markdownView);
        scrollPane.getStyleClass().add("mobile");

        setCellFactory(index -> {
            scrollPane.setVvalue(0);
            return scrollPane;
        });
    }
}