package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;

import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LearnPagination<T extends Learn> extends MobilePagination<T> {

    private static final String DEFAULT_STYLE_CLASS = "learn-pagination";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

    private final ScrollPane scrollPane = new ScrollPane();

    public LearnPagination() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("date-label");
        dateLabel.textProperty().bind(itemProperty().map(item -> item == null ? "" : DATE_FORMATTER.format(item.getCreatedOn())));

        Spacer spacer = new Spacer();

        HBox authorBox = new HBox();
        authorBox.getStyleClass().add("author-box");
        itemProperty().addListener((obs, oldItem, newItem) -> {
            // update author box
            authorBox.getChildren().clear();
            newItem.getPersonIds().forEach(id -> DataRepository.getInstance().getPersonById(id)
                    .ifPresent(person -> {
                        AvatarView avatarView = new AvatarView();
                        avatarView.setTooltip(new Tooltip(person.getName()));
                        avatarView.imageProperty().bind(ImageManager.getInstance().personImageProperty(person));
                        MobileLinkUtil.setLink(avatarView, ModelObjectTool.getModelLink(person));
                        authorBox.getChildren().add(avatarView);
                    }));
            authorBox.getChildren().addAll(spacer, dateLabel);
        });

        CustomMarkdownView markdownView = new CustomMarkdownView();
        markdownView.baseURLProperty().bind(baseURLProperty());
        markdownView.mdStringProperty().bind(Bindings.createStringBinding(() -> {
            T currentItem = getItem();
            if (getBaseURL() == null || currentItem == null) {
                return "";
            }

            if (currentItem instanceof LearnJavaFX data) {
                return DataRepository.getInstance().getLearnJavaFXReadMe(data);
            } else if (currentItem instanceof LearnMobile data) {
                return DataRepository.getInstance().getLearnMobileReadMe(data);
            } else if (currentItem instanceof LearnRaspberryPi data) {
                return DataRepository.getInstance().getLearnRaspberryPiReadMe(data);
            }
            return "";
        }, itemProperty(), baseURLProperty()));

        VBox contentBox = new VBox(authorBox, markdownView);
        contentBox.getStyleClass().add("content-box");

        scrollPane.setContent(contentBox);
        scrollPane.getStyleClass().add("mobile");

        setCellFactory(index -> {
            scrollPane.setVvalue(0);
            return scrollPane;
        });
    }

    // base url

    private final StringProperty baseURL = new SimpleStringProperty(this, "baseURL");

    public String getBaseURL() {
        return baseURL.get();
    }

    public StringProperty baseURLProperty() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL.set(baseURL);
    }

}