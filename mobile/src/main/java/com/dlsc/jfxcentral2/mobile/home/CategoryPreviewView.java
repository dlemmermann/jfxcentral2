package com.dlsc.jfxcentral2.mobile.home;

import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.AvatarView;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.MobileLinkUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.SocialUtil;
import javafx.beans.binding.Bindings;
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
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

public class CategoryPreviewView extends VBox {

    private static final String DEFAULT_STYLE_CLASS = "category-preview-view";
    private final Button moreButton;

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public CategoryPreviewView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);

        Label titleLabel = new Label();
        titleLabel.textProperty().bind(titleProperty());
        titleLabel.getStyleClass().add("title");

        moreButton = new Button("More", new FontIcon("mdi-chevron-right"));
        moreButton.getStyleClass().add("more-button");
        moreButton.managedProperty().bind(moreButton.visibleProperty());
        moreButton.visibleProperty().bind(showAllUrlProperty().isNotEmpty());

        HBox header = new HBox(titleLabel, new Spacer(), moreButton);
        header.getStyleClass().add("header");

        VBox content = new VBox();
        content.getStyleClass().add("content");

        itemsProperty().addListener((observable, oldValue, newValue) -> {
            content.getChildren().clear();

            int size = newValue.size();
            for (int i = 0; i < size; i++) {
                CategoryItem item = newValue.get(i);
                CategoryCell cell = new CategoryCell();
                if (size == 1) {
                    cell.getStyleClass().add("single-cell");
                } else {
                    if (i == 0) {
                        cell.getStyleClass().add("first-cell");
                    } else if (i == size - 1) {
                        cell.getStyleClass().add("last-cell");
                    } else {
                        cell.getStyleClass().add("middle-cell");
                    }
                }

                cell.setTitle(item.title());
                cell.setTitleGraphic(item.titleGraphic());
                cell.setDescription(item.description());

                // must enable social features
                cell.setLikesNumber(item.likesNumber());
                cell.setSaveNumber(item.saveNumber());
                cell.setViewsNumber(item.viewsNumber());

                if (item.imageProperty() != null) {
                    cell.imageProperty().bind(item.imageProperty());
                }

                MobileLinkUtil.setLink(cell, item.url());
                content.getChildren().add(cell);
            }
        });

        getChildren().addAll(header, content);
    }

    /**
     * Represents a single item in the category preview.
     *
     * @param likesNumber : must enable social features
     * @param viewsNumber : must enable social features
     */
    public record CategoryItem(String title, String description, Node titleGraphic, ObjectProperty<Image> imageProperty,
                               String url,
                               int likesNumber,
                               int saveNumber,
                               int viewsNumber) {
        public CategoryItem(String title, String description, ObjectProperty<Image> imageProperty, String url) {
            this(title, description, null, imageProperty, url, 0, 0, 0);
        }

        public CategoryItem(String title, String description, Node titleGraphic, ObjectProperty<Image> imageProperty, String url) {
            this(title, description, titleGraphic, imageProperty, url, 0, 0, 0);
        }
    }

    // size

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    // title

    private final StringProperty title = new SimpleStringProperty(this, "title");

    public final StringProperty titleProperty() {
        return title;
    }

    public final String getTitle() {
        return titleProperty().get();
    }

    public final void setTitle(String title) {
        titleProperty().set(title);
    }

    // show all url

    private final StringProperty showAllUrl = new SimpleStringProperty(this, "showAllUrl") {
        @Override
        protected void invalidated() {
            MobileLinkUtil.setLink(moreButton, getShowAllUrl());
        }
    };

    public StringProperty showAllUrlProperty() {
        return showAllUrl;
    }

    public String getShowAllUrl() {
        return showAllUrlProperty().get();
    }

    public void setShowAllUrl(String showAllUrl) {
        showAllUrlProperty().set(showAllUrl);
    }

    // items

    private final ListProperty<CategoryItem> items = new SimpleListProperty<>(this, "items", FXCollections.observableArrayList());

    public ListProperty<CategoryItem> itemsProperty() {
        return items;
    }

    public ObservableList<CategoryItem> getItems() {
        return itemsProperty().get();
    }

    public void setItems(ObservableList<CategoryItem> items) {
        itemsProperty().set(items);
    }

    // CategoryCell

    private static class CategoryCell extends HBox {

        private static final String DEFAULT_STYLE_CLASS = "category-cell";
        private static final PseudoClass SOCIAL_FEATURES_ENABLED = PseudoClass.getPseudoClass("social-enabled");

        public CategoryCell() {
            getStyleClass().add(DEFAULT_STYLE_CLASS);

            // left side
            AvatarView avatarView = new AvatarView();
            avatarView.imageProperty().bind(imageProperty());
            avatarView.setType(AvatarView.Type.SQUARE);
            avatarView.setMouseTransparent(true);
            avatarView.managedProperty().bind(avatarView.visibleProperty());
            avatarView.visibleProperty().bind(imageProperty().isNotNull());

            // right side
            Label titleLabel = new Label();
            titleLabel.getStyleClass().add("title");
            titleLabel.textProperty().bind(titleProperty());
            titleLabel.graphicProperty().bind(titleGraphicProperty());

            Label descriptionLabel = new Label();
            descriptionLabel.getStyleClass().add("description");
            descriptionLabel.setWrapText(true);
            descriptionLabel.setMaxHeight(Double.MAX_VALUE);
            descriptionLabel.textProperty().bind(descriptionProperty());
            descriptionLabel.managedProperty().bind(descriptionLabel.visibleProperty());
            descriptionLabel.visibleProperty().bind(descriptionProperty().isNotEmpty());
            VBox.setVgrow(descriptionLabel, Priority.ALWAYS);

            Label likesLabel = new Label();
            likesLabel.getStyleClass().add("likes-label");
            likesLabel.setGraphic(new FontIcon("mdi-heart"));
            likesLabel.textProperty().bind(likesNumberProperty().asString());

            Label savesLabel = new Label();
            savesLabel.getStyleClass().add("saves-label");
            savesLabel.setGraphic(new FontIcon("mdi-bookmark"));
            savesLabel.textProperty().bind(likesNumberProperty().asString());

            Label viewsLabel = new Label();
            viewsLabel.getStyleClass().add("views-label");
            viewsLabel.setGraphic(new FontIcon("mdi-eye"));
            viewsLabel.textProperty().bind(viewsNumberProperty().asString());

            HBox footer = new HBox(likesLabel, savesLabel, viewsLabel);
            footer.getStyleClass().add("footer");
            footer.managedProperty().bind(footer.visibleProperty());
            footer.setVisible(SocialUtil.isSocialFeaturesEnabled());
            pseudoClassStateChanged(SOCIAL_FEATURES_ENABLED, footer.isVisible());
            footer.visibleProperty().addListener(it -> pseudoClassStateChanged(SOCIAL_FEATURES_ENABLED, footer.isVisible()));

            VBox cellContent = new VBox(titleLabel, descriptionLabel, footer);
            cellContent.getStyleClass().add("cell-content");
            HBox.setHgrow(cellContent, Priority.ALWAYS);

            this.maxHeightProperty().bind(Bindings.createObjectBinding(() -> avatarView.getAvatarSize() + this.getInsets().getTop() + this.getInsets().getBottom(), avatarView.avatarSizeProperty(), this.insetsProperty()));
            getChildren().addAll(avatarView, cellContent);
        }

        // title

        private final StringProperty title = new SimpleStringProperty(this, "title");

        public final StringProperty titleProperty() {
            return title;
        }

        public final String getTitle() {
            return titleProperty().get();
        }

        public final void setTitle(String title) {
            titleProperty().set(title);
        }

        // titleGraphic

        private final ObjectProperty<Node> titleGraphic = new SimpleObjectProperty<>(this, "titleGraphic");

        public final ObjectProperty<Node> titleGraphicProperty() {
            return titleGraphic;
        }

        public final Node getTitleGraphic() {
            return titleGraphicProperty().get();
        }

        public final void setTitleGraphic(Node titleGraphic) {
            titleGraphicProperty().set(titleGraphic);
        }

        // description

        private final StringProperty description = new SimpleStringProperty(this, "description");

        public final StringProperty descriptionProperty() {
            return description;
        }

        public final String getDescription() {
            return descriptionProperty().get();
        }

        public final void setDescription(String description) {
            descriptionProperty().set(description);
        }

        // image

        private final ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

        public final ObjectProperty<Image> imageProperty() {
            return image;
        }

        public final Image getImage() {
            return imageProperty().get();
        }

        public final void setImage(Image image) {
            imageProperty().set(image);
        }

        // likesNumber must enable social features

        private IntegerProperty likesNumber;

        public final IntegerProperty likesNumberProperty() {
            if (likesNumber == null) {
                likesNumber = new SimpleIntegerProperty(this, "likesNumber");
            }
            return likesNumber;
        }

        public final int getLikesNumber() {
            return likesNumber == null ? 0 : likesNumber.get();
        }

        public final void setLikesNumber(int likesNumber) {
            likesNumberProperty().set(likesNumber);
        }

        // saveNumber must enable social features

        private IntegerProperty saveNumber;

        public final IntegerProperty saveNumberProperty() {
            if (saveNumber == null) {
                saveNumber = new SimpleIntegerProperty(this, "saveNumber");
            }
            return saveNumber;
        }

        public final int getSaveNumber() {
            return saveNumber == null ? 0 : saveNumber.get();
        }

        public final void setSaveNumber(int saveNumber) {
            saveNumberProperty().set(saveNumber);
        }

        // viewsNumber must enable social features

        private IntegerProperty viewsNumber;

        public final IntegerProperty viewsNumberProperty() {
            if (viewsNumber == null) {
                viewsNumber = new SimpleIntegerProperty(this, "viewsNumber");
            }
            return viewsNumber;
        }

        public final int getViewsNumber() {
            return viewsNumber == null ? 0 : viewsNumber.get();
        }

        public final void setViewsNumber(int viewsNumber) {
            viewsNumberProperty().set(viewsNumber);
        }
    }

    public static CategoryPreviewView createBlogPreviewView(List<Blog> blogs, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Blog");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Blog blog : blogs) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    blog.getName(),
                    blog.getSummary(),
                    ImageManager.getInstance().blogIconImageProperty(blog),
                    ModelObjectTool.getModelLink(blog)
            );
            items.add(item);
        }
        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createBlogPreviewView(List<Blog> blogs) {
        return createBlogPreviewView(blogs, null);
    }

    public static CategoryPreviewView createShowCasePreviewView(List<RealWorldApp> apps, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Real World Apps");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (RealWorldApp app : apps) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    app.getName(),
                    app.getSummary(),
                    ImageManager.getInstance().realWorldAppBannerImageProperty(app),
                    ModelObjectTool.getModelLink(app)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createShowCasePreviewView(List<RealWorldApp> apps) {
        return createShowCasePreviewView(apps, null);
    }

    public static CategoryPreviewView createTipsPreviewView(List<Tip> tips, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Tips");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Tip tip : tips) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    tip.getName(),
                    tip.getSummary(),
                    ImageManager.getInstance().tipBannerImageProperty(tip),
                    ModelObjectTool.getModelLink(tip)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createTipsPreviewView(List<Tip> tips) {
        return createTipsPreviewView(tips, null);
    }

    public static CategoryPreviewView createBooksPreviewView(List<Book> books, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Books");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();

        for (Book book : books) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    book.getName(),
                    book.getSummary(),
                    ImageManager.getInstance().bookCoverImageProperty(book),
                    ModelObjectTool.getModelLink(book)
            );
            items.add(item);
        }
        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createBooksPreviewView(List<Book> books) {
        return createBooksPreviewView(books, null);
    }

    public static CategoryPreviewView createLibraryPreviewView(List<Library> libraries, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Libraries");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Library library : libraries) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    library.getName(),
                    library.getSummary(),
                    ImageManager.getInstance().libraryFeaturedImageProperty(library),
                    ModelObjectTool.getModelLink(library)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createLibraryPreviewView(List<Library> libraries) {
        return createLibraryPreviewView(libraries, null);
    }

    public static CategoryPreviewView createPeoplePreviewView(List<Person> people, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("People");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Person person : people) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    person.getName(),
                    DataRepository.getInstance().getPersonReadMe(person),
                    ImageManager.getInstance().personImageProperty(person),
                    ModelObjectTool.getModelLink(person)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createPeoplePreviewView(List<Person> people) {
        return createPeoplePreviewView(people, null);
    }

    public static CategoryPreviewView createVideosPreviewView(List<Video> videos, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Videos");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Video video : videos) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    video.getName(),
                    video.getDescription(),
                    ImageManager.getInstance().youTubeImageProperty(video),
                    ModelObjectTool.getModelLink(video)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createVideosPreviewView(List<Video> videos) {
        return createVideosPreviewView(videos, null);
    }

    public static CategoryPreviewView createLearnPreviewView(List<Learn> learns) {
        return createLearnPreviewView(learns, null);
    }

    public static CategoryPreviewView createLearnPreviewView(List<Learn> learns, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Learn");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }
        view.getStyleClass().add("learn-category-preview-view");
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Learn learn : learns) {
            // create title graphic
            Ikon icon = ModelObjectTool.getModelIcon(learn);
            FontIcon fontIcon = new FontIcon(icon);
            StackPane iconWrapper = new StackPane(fontIcon);
            iconWrapper.getStyleClass().add("icon-wrapper");
            if (learn instanceof LearnJavaFX) {
                iconWrapper.getStyleClass().add("javafx");
            } else if (learn instanceof LearnMobile) {
                iconWrapper.getStyleClass().add("mobile");
            } else if (learn instanceof LearnRaspberryPi) {
                iconWrapper.getStyleClass().add("raspberry-pi");
            }

            // create item
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    learn.getName(),
                    null,
                    iconWrapper,
                    null,
                    ModelObjectTool.getModelLink(learn)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    public static CategoryPreviewView createCompanyPreviewView(List<Company> linkedCompanies) {
        return createCompanyPreviewView(linkedCompanies, null);
    }

    public static CategoryPreviewView createCompanyPreviewView(List<Company> linkedCompanies, String showAllUrl) {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Companies");
        if (showAllUrl != null) {
            view.setShowAllUrl(showAllUrl);
        }

        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        for (Company company : linkedCompanies) {
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    company.getName(),
                    company.getDescription(),
                    ImageManager.getInstance().companyImageProperty(company),
                    ModelObjectTool.getModelLink(company)
            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }
}
