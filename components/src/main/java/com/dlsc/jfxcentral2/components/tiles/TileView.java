package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.FlipView;
import com.dlsc.jfxcentral2.components.SaveAndLikeButton;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.SaveAndLikeUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableBooleanProperty;
import javafx.css.Styleable;
import javafx.css.StyleableBooleanProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.BooleanConverter;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileView<T extends ModelObject> extends TileViewBase<T> {

    private final FlipView flipView = new FlipView();
    private final Button button1;
    private final Button button2;
    private Region mainImageRegion;

    public TileView(T item) {
        super(item);

        getStyleClass().add("tile-view");

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        //[Top] FlipView
        flipView.setFrontNode(createFront());
        flipView.setBackNode(createBack());
        VBox.setVgrow(flipView, Priority.ALWAYS);

        //[Bottom] nodes,save button,like button
        SaveAndLikeButton saveAndLikeButton = new SaveAndLikeButton();
        saveAndLikeButton.sizeProperty().bind(sizeProperty());
        saveSelectedProperty().addListener((ob, ov, nv) -> saveAndLikeButton.setSaveButtonSelected(nv));
        likeSelectedProperty().addListener((ob, ov, nv) -> saveAndLikeButton.setLikeButtonSelected(nv));

        saveAndLikeButton.saveButtonSelectedProperty().addListener((ob, ov, saved) -> {
            setSaveSelected(saved);
            if (getData() != null) {
                System.out.println((saved ? "SELECTED: " : "UNSELECTED: ") + getData().getName());
            }
        });

        saveAndLikeButton.likeButtonSelectedProperty().addListener((ob, ov, liked) -> {
            setLikeSelected(liked);
            if (getData() != null) {
                System.out.println((liked ? "LIKED: " : "UNLIKED: ") + getData().getName());
            }
        });

        button1 = new Button();
        button1.setFocusTraversable(false);
        button1.getStyleClass().addAll("bg-transparent-button", "button1");
        button1.textProperty().bind(button1TextProperty());
        button1.graphicProperty().bind(button1GraphicProperty());
        button1.managedProperty().bind(button1.visibleProperty());
        button1.visibleProperty().bind(button1TextProperty().isNotEmpty().or(button1GraphicProperty().isNotNull()));

        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.managedProperty().bind(button1.visibleProperty());
        separator1.visibleProperty().bind(button1.visibleProperty());

        button2 = new Button();
        button2.setFocusTraversable(false);
        button2.getStyleClass().addAll("bg-transparent-button", "button2");
        button2.textProperty().bind(button2TextProperty());
        button2.graphicProperty().bind(button2GraphicProperty());
        button2.managedProperty().bind(button2.visibleProperty());
        button2.visibleProperty().bind(button2TextProperty().isNotEmpty().or(button2GraphicProperty().isNotNull()));

        Separator separator2 = new Separator(Orientation.VERTICAL);
        separator2.managedProperty().bind(button2.visibleProperty());
        separator2.visibleProperty().bind(button2.visibleProperty());

        GridPane bottomPane = new GridPane();
        bottomPane.getStyleClass().add("bottom-pane");
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.CENTER);
        bottomPane.getRowConstraints().add(rowConstraints);
        bottomPane.add(saveAndLikeButton, 0, 0);

        button1.visibleProperty().addListener((ob, ov, nv) -> bottomPaneLayout(saveAndLikeButton, button1, separator1, button2, separator2, bottomPane));

        button2.visibleProperty().addListener((ob, ov, nv) -> bottomPaneLayout(saveAndLikeButton, button1, separator1, button2, separator2, bottomPane));

        button1VisibleProperty().addListener((ob, ov, nv) -> bottomPaneLayout(saveAndLikeButton, button1, separator1, button2, separator2, bottomPane));

        button2VisibleProperty().addListener((ob, ov, nv) -> bottomPaneLayout(saveAndLikeButton, button1, separator1, button2, separator2, bottomPane));

        contentBox.getChildren().setAll(flipView, bottomPane);
        getChildren().setAll(contentBox);

        if (mainImageRegion != null) {
            boolean isVideo = item instanceof Video;
            StackPane.setAlignment(mainImageRegion, isVideo ? Pos.TOP_LEFT : Pos.CENTER);
        }
        setTitle(item.getName());
        setSaveSelected(SaveAndLikeUtil.isSaved(item));
        setLikeSelected(SaveAndLikeUtil.isLiked(item));

        if (item instanceof RealWorldApp app) {
            imageProperty().bind(ImageManager.getInstance().realWorldAppBannerImageProperty(app));
        } else if (item instanceof Video video) {
            imageProperty().bind(ImageManager.getInstance().youTubeImageProperty(video));
        } else if (item instanceof Book book) {
            imageProperty().bind(ImageManager.getInstance().bookCoverImageProperty(book));
        } else if (item instanceof Download download) {
            imageProperty().bind(ImageManager.getInstance().downloadBannerImageProperty(download));
        } else if (item instanceof Company company) {
            imageProperty().bind(ImageManager.getInstance().companyImageProperty(company));
        } else if (item instanceof Tip) {
            imageProperty().bind(Bindings.createObjectBinding(() -> new Image(getClass().getResource("/com/dlsc/jfxcentral2/demoimages/default-tips-tricks-bg.png").toExternalForm())));
        }
    }

    public Button getButton1() {
        return button1;
    }

    public Button getButton2() {
        return button2;
    }

    private void bottomPaneLayout(SaveAndLikeButton saveAndLikeButton, Button button1, Separator separator1, Button button2, Separator separator2, GridPane bottomPane) {
        bottomPane.getChildren().clear();
        bottomPane.getColumnConstraints().clear();
        int column = 0;
        if (button1.isVisible() && getButton1Visible()) {
            bottomPane.add(button1, column++, 0);
            bottomPane.add(separator1, column++, 0);
            bottomPane.getColumnConstraints().addAll(getColumnConstraints(), getColumnConstraints());
        }
        if (button2.isVisible() && getButton2Visible()) {
            bottomPane.add(button2, column++, 0);
            bottomPane.add(separator2, column++, 0);
            bottomPane.getColumnConstraints().addAll(getColumnConstraints(), getColumnConstraints());
        }
        bottomPane.add(saveAndLikeButton, column, 0);
        bottomPane.getColumnConstraints().addAll(getColumnConstraints());
    }

    private ColumnConstraints getColumnConstraints() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setHalignment(HPos.CENTER);
        return columnConstraints;
    }

    private Node createFront() {
        //Center title and description
        Label titleLabel = new Label();
        titleLabel.getStyleClass().add("title");
        titleLabel.textProperty().bind(titleProperty());
        titleLabel.managedProperty().bind(titleProperty().isNotEmpty());
        titleLabel.visibleProperty().bind(titleProperty().isNotEmpty());
        titleLabel.setWrapText(true);
        titleLabel.minHeightProperty().bind(Bindings.createDoubleBinding(() -> {
            boolean isGalley = getStyleClass().contains("video-gallery-tile");
            double height = titleLabel.getFont().getSize() * 1.5 * (isGalley ? 2 : isSmall() ? 2 : 2.2);
            double prefH = titleLabel.prefHeight(titleLabel.getWidth());
            return Math.min(prefH, height);
        }, sizeProperty(), titleLabel.fontProperty(), titleLabel.widthProperty(), getStyleClass()));
        titleLabel.setOnMousePressed(event -> flipView.flipToBack());

        Label descriptionLabel = new Label();
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.managedProperty().bind(descriptionProperty().isNotEmpty());
        descriptionLabel.visibleProperty().bind(descriptionProperty().isNotEmpty());
        descriptionLabel.textProperty().bind(descriptionProperty());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setOnMousePressed(event -> flipView.flipToBack());

        VBox centerBox = new VBox(titleLabel, descriptionLabel);
        centerBox.getStyleClass().add("center-box");
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        VBox frontBox = new VBox(createFrontTop(), centerBox);
        frontBox.getStyleClass().add("front-box");
        frontBox.setAlignment(Pos.TOP_LEFT);
        return frontBox;
    }

    protected Node createFrontTop() {
        //Top image
        mainImageRegion = new Region();
        mainImageRegion.getStyleClass().add("main-image-region");
        imageProperty().addListener(it -> {
            Image image = getImage();
            mainImageRegion.setStyle("-fx-background-image: url('" + (image == null ? null : image.getUrl()) + "');");
        });

        //remarkLabel Used to display the remark information,
        // such as the duration of the video ,release date, etc.
        Label remarkLabel = new Label();
        remarkLabel.setGraphic(new FontIcon());
        remarkLabel.textProperty().bind(remarkProperty());
        remarkLabel.managedProperty().bind(remarkProperty().isNotEmpty());
        remarkLabel.visibleProperty().bind(remarkProperty().isNotEmpty());
        remarkLabel.getStyleClass().add("remark");
        StackPane.setAlignment(remarkLabel, Pos.TOP_RIGHT);

        StackPane imageContainer = new StackPane();
        imageContainer.getStyleClass().add("image-container");
        imageContainer.getChildren().setAll(mainImageRegion, remarkLabel);
        return imageContainer;
    }

    private Node createBack() {
        Button closeButton = new Button("CLOSE", new FontIcon(IkonUtil.close));
        closeButton.getStyleClass().addAll("close-button", "blue-button");
        closeButton.setContentDisplay(ContentDisplay.RIGHT);
        closeButton.setOnAction(event -> flipView.flipToFront());
        closeButton.setFocusTraversable(false);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setContextMenu(new ContextMenu());
        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);
        descriptionArea.textProperty().bind(descriptionProperty());
        VBox.setVgrow(descriptionArea, Priority.ALWAYS);

        VBox backBox = new VBox(closeButton, descriptionArea);
        backBox.setAlignment(Pos.CENTER);
        backBox.getStyleClass().add("back-box");
        return backBox;
    }

    private final StringProperty remark = new SimpleStringProperty(this, "remark");

    public String getRemark() {
        return remark.get();
    }

    public StringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    private final StringProperty button1Text = new SimpleStringProperty(this, "button1Text");

    public String getButton1Text() {
        return button1Text.get();
    }

    public StringProperty button1TextProperty() {
        return button1Text;
    }

    public void setButton1Text(String button1Text) {
        this.button1Text.set(button1Text);
    }

    private final ObjectProperty<Node> button1Graphic = new SimpleObjectProperty<>(this, "button1Graphic");

    public Node getButton1Graphic() {
        return button1Graphic.get();
    }

    public ObjectProperty<Node> button1GraphicProperty() {
        return button1Graphic;
    }

    public void setButton1Graphic(Node button1Graphic) {
        this.button1Graphic.set(button1Graphic);
    }

    private final StyleableBooleanProperty button1Visible = new SimpleStyleableBooleanProperty(StyleableProperties.BUTTON_1_VISIBLE, TileView.this, "button1Visible", true);

    public boolean getButton1Visible() {
        return button1Visible.get();
    }

    public StyleableBooleanProperty button1VisibleProperty() {
        return button1Visible;
    }

    public void setButton1Visible(boolean button1Visible) {
        this.button1Visible.set(button1Visible);
    }

    private final StringProperty button2Text = new SimpleStringProperty(this, "button2Text");

    public String getButton2Text() {
        return button2Text.get();
    }

    public StringProperty button2TextProperty() {
        return button2Text;
    }

    public void setButton2Text(String button2Text) {
        this.button2Text.set(button2Text);
    }

    private final ObjectProperty<Node> button2Graphic = new SimpleObjectProperty<>(this, "button2Graphic");

    public Node getButton2Graphic() {
        return button2Graphic.get();
    }

    public ObjectProperty<Node> button2GraphicProperty() {
        return button2Graphic;
    }

    public void setButton2Graphic(Node button2Graphic) {
        this.button2Graphic.set(button2Graphic);
    }

    private final StyleableBooleanProperty button2Visible = new SimpleStyleableBooleanProperty(StyleableProperties.BUTTON_2_VISIBLE, TileView.this,
            "button2Visible", true);

    public boolean getButton2Visible() {
        return button2Visible.get();
    }

    public StyleableBooleanProperty button2VisibleProperty() {
        return button2Visible;
    }

    public void setButton2Visible(boolean button2Visible) {
        this.button2Visible.set(button2Visible);
    }

    private static class StyleableProperties {

        private static final CssMetaData<TileView, Boolean> BUTTON_1_VISIBLE = new CssMetaData<>(
                "-fx-button1-visible", BooleanConverter.getInstance(), true) {

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(TileView control) {
                return control.button1VisibleProperty();
            }

            @Override
            public boolean isSettable(TileView control) {
                return !control.button1Visible.isBound();
            }
        };

        private static final CssMetaData<TileView, Boolean> BUTTON_2_VISIBLE = new CssMetaData<>(
                "-fx-button2-visible", BooleanConverter.getInstance(), true) {

            @Override
            public StyleableProperty<Boolean> getStyleableProperty(TileView control) {
                return control.button2VisibleProperty();
            }

            @Override
            public boolean isSettable(TileView control) {
                return !control.button2Visible.isBound();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(StackPane.getClassCssMetaData());
            Collections.addAll(styleables, BUTTON_1_VISIBLE, BUTTON_2_VISIBLE);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return TileView.StyleableProperties.STYLEABLES;
    }

}
