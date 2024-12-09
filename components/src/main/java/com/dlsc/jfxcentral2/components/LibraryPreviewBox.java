package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Image;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LibraryInfo;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Objects;

public class LibraryPreviewBox extends PaneBase {

    private ToggleGroup group = new ToggleGroup();
    private CustomImageView largerImageView;
    private Library library;

    public LibraryPreviewBox(Library library) {
        this.library = Objects.requireNonNull(library);
        getStyleClass().add("library-preview-box");
        setLibraryInfo(DataRepository.getInstance().getLibraryInfo(library));
        layoutBySize();
    }

    @Override
    protected void layoutBySize() {
        getChildren().clear();
        group.getToggles().clear();

        LibraryInfo info = getLibraryInfo();

        if (info != null && library != null) {
            List<com.dlsc.jfxcentral.data.model.Image> images = info.getImages();
            if (images == null || images.isEmpty()) {
                return;
            }
            largerImageView = new CustomImageView();
            largerImageView.getStyleClass().add("larger-image");

            Button closeButton = new Button();
            closeButton.setGraphic(new FontIcon(IkonUtil.close));
            closeButton.getStyleClass().addAll("fill-button", "close-button");
            closeButton.setFocusTraversable(false);
            StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);
            closeButton.setOnAction(event -> {
                largerImageView.setImage(null);
                largerImageView.setUserData(null);
                group.selectToggle(null);
            });

            StackPane largerImageWrapper = new StackPane();
            largerImageWrapper.getStyleClass().add("larger-image-wrapper");
            largerImageWrapper.getChildren().addAll(largerImageView, closeButton);
            largerImageWrapper.managedProperty().bind(largerImageWrapper.visibleProperty());
            largerImageWrapper.visibleProperty().bind(largerImageView.imageProperty().isNotNull());

            if (!isSmall()) {
                layoutMediumAndLarge(images, largerImageWrapper);
            } else {
                layoutSmall(images, largerImageWrapper);
            }
        }
    }

    private void layoutSmall(List<Image> images, StackPane largerImageWrapper) {
        IntegerProperty pageIndexProperty = new SimpleIntegerProperty(-1);
        int maxCount = 3;

        HBox previewBox = new HBox();
        previewBox.getStyleClass().add("previews-box");
        HBox.setHgrow(previewBox, Priority.ALWAYS);

        Button prevButton = new Button();
        prevButton.getStyleClass().addAll("fill-button", "prev-button");
        prevButton.setFocusTraversable(false);
        prevButton.setGraphic(new FontIcon(JFXCentralIcon.BULLET_POINT));
        prevButton.setOnAction(event -> {
            pageIndexProperty.set(pageIndexProperty.get() - 1);
        });

        Button nextButton = new Button();
        nextButton.getStyleClass().addAll("fill-button", "next-button");
        nextButton.setFocusTraversable(false);
        nextButton.setGraphic(new FontIcon(JFXCentralIcon.BULLET_POINT));
        nextButton.setOnAction(event -> {
            pageIndexProperty.set(pageIndexProperty.get() + 1);
        });

        pageIndexProperty.addListener(it -> {
            int pageIndex = pageIndexProperty.get();
            prevButton.setDisable(pageIndex == 0);
            nextButton.setDisable(pageIndex == images.size() / maxCount);

            int startIndex = pageIndex * maxCount;
            int endIndex = Math.min((pageIndex + 1) * maxCount, images.size());
            List<Image> subList = images.subList(startIndex, endIndex);
            updateImageBox(previewBox, subList);
        });

        HBox navBox = new HBox(prevButton, previewBox, nextButton);
        navBox.getStyleClass().add("nav-box");

        VBox contentBox = new VBox(navBox, largerImageWrapper);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);

        pageIndexProperty.set(0);
    }

    private void updateImageBox(HBox imageBox, List<com.dlsc.jfxcentral.data.model.Image> images) {
        imageBox.getChildren().clear();
        images.forEach(item -> imageBox.getChildren().add(createPreviewCell(item)));
    }

    private void layoutMediumAndLarge(List<Image> images, StackPane largerImageWrapper) {
        FlowPane previewsPane = new FlowPane();
        previewsPane.getStyleClass().add("previews-pane");
        BooleanBinding isExpandBinding = Bindings.createBooleanBinding(() -> previewsPane.getChildren().size() >= images.size() + 1, previewsPane.getChildren());
        isExpandBinding.addListener(it -> {
            boolean isExpand = isExpandBinding.get();
            if (isExpand && !previewsPane.getStyleClass().contains("expanded")) {
                previewsPane.getStyleClass().add("expanded");
            } else if (!isExpand) {
                previewsPane.getStyleClass().remove("expanded");
            }
        });

        int max = isSmall() ? 2 : 7 - (isMedium() ? 1 : 0);
        int firstShownSize = Math.min(images.size(), max);
        for (int i = 0; i < firstShownSize; i++) {
            Node imageWrapper = createPreviewCell(images.get(i));
            previewsPane.getChildren().add(imageWrapper);
        }

        if (images.size() > max) {
            Label moreLabel = new Label("+" + (images.size() - max));
            moreLabel.setGraphic(new FontIcon(JFXCentralIcon.CHEVRON_MENU_TOP));
            moreLabel.getStyleClass().add("more-label");
            previewsPane.getChildren().addAll(moreLabel);
            moreLabel.contentDisplayProperty().bind(Bindings.when(isExpandBinding).then(ContentDisplay.GRAPHIC_ONLY).otherwise(ContentDisplay.TEXT_ONLY));
            moreLabel.managedProperty().bind(moreLabel.visibleProperty());

            moreLabel.setOnMousePressed(event -> {
                if (isExpandBinding.get()) {
                    previewsPane.getChildren().remove(firstShownSize, images.size());
                    group.getToggles().remove(firstShownSize, images.size());
                    return;
                }
                for (int i = firstShownSize; i < images.size(); i++) {
                    Node imageWrapper = createPreviewCell(images.get(i));
                    previewsPane.getChildren().add(previewsPane.getChildren().size() - 1, imageWrapper);
                }
            });
        }

        VBox contentBox = new VBox(previewsPane, largerImageWrapper);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    private Node createPreviewCell(Image modelImage) {
        ToggleButton previewButton = new ToggleButton();
        previewButton.getStyleClass().add("preview-button");
        previewButton.setFocusTraversable(false);
        previewButton.setToggleGroup(group);

        CustomImageView imageView = new CustomImageView();
        imageView.imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library, modelImage.getPath()));
        previewButton.setGraphic(imageView);

        previewButton.setSelected(largerImageView.getUserData() == modelImage);
        previewButton.setOnMousePressed(event -> {
            if (largerImageView.getUserData() == modelImage) {
                largerImageView.setImage(null);
                largerImageView.setUserData(null);
            } else {
                largerImageView.setUserData(modelImage);
                largerImageView.setImage(imageView.getImage());
            }
        });

        return previewButton;
    }

    private final ObjectProperty<LibraryInfo> libraryInfo = new SimpleObjectProperty<>(this, "libraryInfo");

    public LibraryInfo getLibraryInfo() {
        return libraryInfo.get();
    }

    public ObjectProperty<LibraryInfo> libraryInfoProperty() {
        return libraryInfo;
    }

    public void setLibraryInfo(LibraryInfo libraryInfo) {
        this.libraryInfo.set(libraryInfo);
    }
}
