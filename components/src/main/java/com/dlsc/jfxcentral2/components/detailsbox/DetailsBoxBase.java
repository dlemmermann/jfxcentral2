package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.components.Header;
import com.dlsc.jfxcentral2.components.PaginationControl2;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.PageUtil;
import com.dlsc.jfxcentral2.utils.PlatformLinkUtil;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.Objects;

public abstract class DetailsBoxBase<T extends ModelObject> extends PaneBase {
    private final Header header;
    private PaginationControl2 pagination;
    private VBox pageBox;

    public DetailsBoxBase() {
        getStyleClass().add("details-box");

        header = new Header();
        header.titleProperty().bind(titleProperty());
        header.iconProperty().bind(ikonProperty());

        itemsProperty().addListener((ob, ov, nv) -> layoutBySize());

        selectedItemProperty().addListener((ob, ov, item) -> {
            int index = getItems().indexOf(item);
            if (index >= 0) {
                int oldPageIndex = pagination.getCurrentPageIndex();
                int newPageIndex = index / getMaxItemsPerPage();
                //If not on the same page; jump to a new page; automatically display the selected item
                if (oldPageIndex != newPageIndex) {
                    pagination.setCurrentPageIndex(newPageIndex);
                } else { //If on the same page; update the items
                    pageBox.getChildren().forEach(node -> {
                        if (node instanceof DetailsBoxBase<?>.DetailsCell detailsCell) {
                            detailsCell.updateExtras();
                        }
                    });
                }
            }
        });

        setDetailUrlProvider(PageUtil::getLink);
    }

    private final ObjectProperty<T> selectedItem = new SimpleObjectProperty<>(this, "selectedItem");

    public T getSelectedItem() {
        return selectedItem.get();
    }

    public ObjectProperty<T> selectedItemProperty() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem.set(selectedItem);
    }

    private final ObjectProperty<Callback<T, Node>> extrasProvider = new SimpleObjectProperty<>(this, "extras");

    public Callback<T, Node> getExtrasProvider() {
        return extrasProvider.get();
    }

    public ObjectProperty<Callback<T, Node>> extrasProviderProperty() {
        return extrasProvider;
    }

    public void setExtrasProvider(Callback<T, Node> extrasProvider) {
        this.extrasProvider.set(extrasProvider);
    }

    @Override
    protected void layoutBySize() {
        pagination = new PaginationControl2();
        pagination.getStyleClass().add("pagination2");
        //pagination.setMaxPageIndicatorCount(3);
        int pageCount = itemsProperty().size() / getMaxItemsPerPage();
        if (itemsProperty().size() % getMaxItemsPerPage() != 0) {
            pageCount++;
        }
        StackPane page = new StackPane();
        page.getStyleClass().add("page");
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(pageIndex -> {
            VBox pageBox = createPageBox(pageIndex);
            page.getChildren().setAll(pageBox);
            return null;
        });
        pagination.managedProperty().bind(pagination.visibleProperty());
        pagination.visibleProperty().bind(pagination.pageCountProperty().greaterThan(1));

        VBox contentBox = new VBox(header, page, pagination);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    private VBox createPageBox(Integer pageIndex) {
        int fromIndex = pageIndex * getMaxItemsPerPage();
        int toIndex = Math.min(fromIndex + getMaxItemsPerPage(), getItems().size());
        pageBox = new VBox();
        pageBox.getStyleClass().add("page-box");
        for (int i = fromIndex; i < toIndex; i++) {
            T model = getItems().get(i);
            DetailsCell cell = new DetailsCell(model);
            if (i == 0) {
                cell.getStyleClass().add("first-cell");
            }
            if (i == toIndex - 1) {
                cell.getStyleClass().add("last-cell");
            }
            pageBox.getChildren().add(cell);
        }
        pageBox.setMinHeight(Region.USE_PREF_SIZE);
        return pageBox;
    }

    protected String getDescription(T model) {
        // description
        String description = model.getDescription();
        if (StringUtils.isBlank(description)) {
            description = model.getSummary();
        }

        if (StringUtils.isNotBlank(description)) {
            return description;
        }

        // some model objects have a more suitable description (currently) in their readme files
        if (model instanceof Download download) {
            return DataRepository.getInstance().getDownloadReadMe(download);
        } else if (model instanceof Person person) {
            return DataRepository.getInstance().getPersonReadMe(person);
        }

        return "(Missing description)";
    }

    private class DetailsCell extends VBox {

        private final StackPane extrasWrapper;
        private T model;

        public DetailsCell(T model) {
            this.model = model;
            VBox cellRight = new VBox();
            cellRight.getStyleClass().add("cell-right");
            HBox.setHgrow(cellRight, Priority.ALWAYS);

            // title box (title and action buttons)
            HBox titleBox = new HBox();
            titleBox.getStyleClass().add("title-box");

            Label titleLabel = new Label(model.getName());
            titleLabel.setWrapText(true);

            //library may have a logo
            if (model instanceof Library library) {
                CustomImageView graphic = new CustomImageView();
                graphic.imageProperty().bind(ImageManager.getInstance().libraryImageProperty(library));
                graphic.managedProperty().bind(graphic.visibleProperty());
                graphic.visibleProperty().bind(graphic.imageProperty().isNotNull());
                titleLabel.setGraphic(graphic);
            }

            titleLabel.getStyleClass().add("title-label");

            titleBox.getChildren().addAll(titleLabel, new Spacer());

            List<Node> actionButtons = createActionButtons(model);
            if (actionButtons != null && !actionButtons.isEmpty()) {
                Pane actionButtonsPane = isSmall() ? new FlowPane() : new HBox();
                actionButtonsPane.getStyleClass().add("action-buttons-pane");
                for (int i = 0; i < actionButtons.size(); i++) {
                    Node actionButton = actionButtons.get(i);
                    if (actionButton instanceof Region region) {
                        region.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    }
                    actionButton.getStyleClass().addAll("action-button", "action-button-" + i);
                    actionButton.setFocusTraversable(false);
                    actionButtonsPane.getChildren().add(actionButton);
                }
                if (isSmall()) {
                    cellRight.getChildren().add(actionButtonsPane);
                } else {
                    titleBox.getChildren().add(actionButtonsPane);
                }
            }

            cellRight.getChildren().addAll(titleBox);

            // description
            CustomMarkdownView descMD = new CustomMarkdownView(getDescription(model));
            descMD.getStyleClass().add("description-markdown");
            cellRight.getChildren().add(descMD);

            // previews
            if (model instanceof Library library) {
                Node previewsBox = createPreviewsBox(library);
                if (previewsBox != null) {
                    cellRight.getChildren().add(previewsBox);
                }
            }

            // save and like
            HBox cellBottom = new HBox();
            cellBottom.getStyleClass().addAll("save-like-box", "cell-bottom");
            SaveAndLikeButton saveButton = new SaveAndLikeButton();
            saveButton.setFocusTraversable(false);
            saveButton.setSaveButtonSelected(SaveAndLikeUtil.isSaved(model));
            saveButton.setLikeButtonSelected(SaveAndLikeUtil.isLiked(model));
            cellBottom.getChildren().add(saveButton);

            HBox cellCenter = new HBox();
            cellCenter.getStyleClass().add("cell-center");

            Node cellLeft = createMainPreView(model);
            if (cellLeft != null) {
                cellLeft.getStyleClass().add("cell-left");
                if (!isSmall()) {
                    cellCenter.getChildren().add(cellLeft);
                } else {
                    cellRight.getChildren().add(0, cellLeft);
                }
            }

            cellCenter.getChildren().add(cellRight);

            getStyleClass().addAll("cell-content", "details-cell");

            extrasWrapper = new StackPane();
            getChildren().addAll(cellCenter, cellBottom, extrasWrapper);
            updateExtras();
        }

        public void updateExtras() {
            Callback<T, Node> extrasProvider = getExtrasProvider();
            if (extrasProvider != null && Objects.equals(model, getSelectedItem())) {
                Node extras = extrasProvider.call(model);
                if (extras != null) {
                    extrasWrapper.getChildren().setAll(extras);
                } else {
                    extrasWrapper.getChildren().clear();
                }
            } else {
                extrasWrapper.getChildren().clear();
            }
        }
    }

    protected Node createPreviewsBox(Library library) {
        return null;
    }

    protected List<Node> createActionButtons(T model) {
        return null;
    }

    protected Node createMainPreView(T model) {
        ObjectProperty<Image> imageProperty;
        String mins = null;

        if (model instanceof Video video) {
            mins = video.getMinutes() + " mins";
        }

        imageProperty = ModelObjectTool.getModelPreviewImageProperty(model, false);

        if (imageProperty != null) {
            CustomImageView imageView = new CustomImageView();
            if (isSmall()) {
                StackPane.setAlignment(imageView, Pos.CENTER_LEFT);
            }
            imageView.imageProperty().bind(imageProperty);
            StackPane.setAlignment(imageView, Pos.TOP_LEFT);

            StackPane mainPreviewPane = new StackPane(imageView);
            mainPreviewPane.getStyleClass().addAll("image-wrapper", "main-preview-wrapper");
            mainPreviewPane.managedProperty().bind(mainPreviewPane.visibleProperty());
            mainPreviewPane.visibleProperty().bind(imageProperty.isNotNull());

            if (mins != null) {
                Label mainPreviewDesc = new Label(mins, new FontIcon());
                mainPreviewDesc.getStyleClass().add("main-preview-desc");
                mainPreviewPane.getChildren().add(mainPreviewDesc);
                StackPane.setAlignment(mainPreviewDesc, Pos.TOP_RIGHT);
            }
            return mainPreviewPane;
        }
        return null;
    }

    protected Button createDetailsButton(T model) {
        Button detailsButton = new Button("DETAILS", new FontIcon(IkonUtil.link));
        detailsButton.setFocusTraversable(false);
        detailsButton.getStyleClass().add("details-button");
        detailsButton.setMinWidth(Region.USE_PREF_SIZE);
        setLinkOnDetailsButton(model, detailsButton);
        detailUrlProviderProperty().addListener(it -> setLinkOnDetailsButton(model, detailsButton));
        return detailsButton;
    }

    private void setLinkOnDetailsButton(T model, Button detailsButton) {
        Callback<T, String> provider = getDetailUrlProvider();
        detailsButton.setVisible(false);
        detailsButton.setManaged(false);
        if (provider != null) {
            String url = provider.call(model);
            if (StringUtils.isNotBlank(url)) {
                detailsButton.setVisible(true);
                detailsButton.setManaged(true);
                PlatformLinkUtil.setLink(detailsButton, url);
            }
        }
    }

    protected Button createHomepageButton(T model) {
        Button homepageButton = new Button("HOMEPAGE", new FontIcon(IkonUtil.link));
        homepageButton.setFocusTraversable(false);
        homepageButton.setMinWidth(Region.USE_PREF_SIZE);
        setLinkOnHomepageButton(model, homepageButton);
        homepageUrlProviderProperty().addListener(it -> setLinkOnHomepageButton(model, homepageButton));
        return homepageButton;
    }

    private void setLinkOnHomepageButton(T model, Button homepageButton) {
        Callback<T, String> provider = getHomepageUrlProvider();
        homepageButton.setVisible(false);
        homepageButton.setManaged(false);
        if (provider != null) {
            String url = provider.call(model);
            if (StringUtils.isNotBlank(url)) {
                homepageButton.setVisible(true);
                homepageButton.setManaged(true);
                ExternalLinkUtil.setExternalLink(homepageButton, homepageUrlProvider.get().call(model));
            }
        }
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "Header");

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    private final ObjectProperty<Ikon> ikon = new SimpleObjectProperty<>(this, "ikon");

    public Ikon getIkon() {
        return ikon.get();
    }

    public ObjectProperty<Ikon> ikonProperty() {
        return ikon;
    }

    public void setIkon(Ikon ikon) {
        this.ikon.set(ikon);
    }

    private final IntegerProperty maxItemsPerPage = new SimpleIntegerProperty(this, "maxItemsPerPage", 3);

    public int getMaxItemsPerPage() {
        return maxItemsPerPage.get();
    }

    public IntegerProperty maxItemsPerPageProperty() {
        return maxItemsPerPage;
    }

    public void setMaxItemsPerPage(int maxItemsPerPage) {
        this.maxItemsPerPage.set(maxItemsPerPage);
    }

    private final ListProperty<T> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ObservableList<T> getItems() {
        return items.get();
    }

    public ListProperty<T> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<T> items) {
        this.items.set(items);
    }

    private final ObjectProperty<Callback<T, String>> detailUrlProvider = new SimpleObjectProperty<>(this, "detailUrlProvider");

    public Callback<T, String> getDetailUrlProvider() {
        return detailUrlProvider.get();
    }

    public ObjectProperty<Callback<T, String>> detailUrlProviderProperty() {
        return detailUrlProvider;
    }

    public void setDetailUrlProvider(Callback<T, String> detailUrlProvider) {
        this.detailUrlProvider.set(detailUrlProvider);
    }

    private final ObjectProperty<Callback<T, String>> homepageUrlProvider = new SimpleObjectProperty<>(this, "homepageUrlProvider");

    public Callback<T, String> getHomepageUrlProvider() {
        return homepageUrlProvider.get();
    }

    public ObjectProperty<Callback<T, String>> homepageUrlProviderProperty() {
        return homepageUrlProvider;
    }

    public void setHomepageUrlProvider(Callback<T, String> homepageUrlProvider) {
        this.homepageUrlProvider.set(homepageUrlProvider);
    }
}
