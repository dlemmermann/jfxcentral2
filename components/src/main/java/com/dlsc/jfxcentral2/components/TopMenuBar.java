package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchField;
import com.dlsc.gemsfx.Spacer;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.dlsc.jfxcentral2.utils.SocialUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import one.jpro.platform.routing.LinkUtil;
import one.jpro.platform.routing.sessionmanager.SessionManager;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopMenuBar extends PaneBase {

    private static final Mode DEFAULT_MODE = Mode.DARK;

    private static final PseudoClass LIGHT_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("light");
    private static final PseudoClass DARK_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("dark");

    private final CustomImageView jfxCentralLogoView;
    private final boolean mobile;
    //    private final View view;
    private SearchField<ModelObject> searchField;
    private final HBox contentBox;
    private final StackPane logoWrapper;
    private Node searchTextField;
    private SessionManager sessionManager;

    public TopMenuBar(boolean mobile) { // iew view) {
//        this.view = view;
        this.mobile = mobile;

        getStyleClass().add("top-menu-bar");

        activateModePseudoClass();
        modeProperty().addListener(it -> activateModePseudoClass());

        contentBox = new HBox();
        contentBox.getStyleClass().add("content");
        getChildren().add(contentBox);

        jfxCentralLogoView = new CustomImageView();
        jfxCentralLogoView.setPreserveRatio(true);
        jfxCentralLogoView.getStyleClass().addAll("jfx-central-logo", "small");

        modeProperty().addListener(it -> {
            if (getMode().equals(Mode.LIGHT)) {
                jfxCentralLogoView.getStyleClass().add("black");
            }
        });

        // wrap logo to make it completely clickable
        logoWrapper = new StackPane(jfxCentralLogoView);
        LinkUtil.setLink(logoWrapper, "/", "Back to homepage");

        layoutBySize();

        sceneProperty().addListener(it -> {
            if (getScene() != null && getStyleClass().contains("start-page")) {
                jfxCentralLogoView.getStyleClass().add("color");
            }
        });
    }

    private SearchField<ModelObject> createSearchField() {
        SearchField<ModelObject> searchField = new SearchField<>();
        searchField.setOnCommit(selectedItem -> {
            if (selectedItem != null) {
                getSessionManager().gotoURL(ModelObjectTool.getModelLink(selectedItem));
            }
        });

        searchField.getPopup().setPrefWidth(600);
        searchField.getEditor().setFocusTraversable(OSUtil.isAndroidOrIOS());
        searchField.setPromptText("Search");
        searchField.setCellFactory(listView -> new SearchResultCell());
        searchField.setSuggestionProvider(request -> search(request.getUserText()));
        searchField.setMatcher((modelObject, text) -> modelObject.getName().startsWith(text));
        searchField.setConverter(new StringConverter<>() {
            @Override
            public String toString(ModelObject object) {
                if (object != null) {
                    return object.getName();
                }
                return "";
            }

            @Override
            public ModelObject fromString(String string) {
                return null;
            }
        });
        return searchField;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    private SessionManager getSessionManager() {
        return sessionManager;
    }

    public List<ModelObject> search(String pattern) {
        DataRepository repository = DataRepository.getInstance();

        List<ModelObject> results = new ArrayList<>();
        search(repository.getBooks(), pattern, results);
        search(repository.getBlogs(), pattern, results);
        search(repository.getCompanies(), pattern, results);
        search(repository.getPeople(), pattern, results);
        search(repository.getLibraries(), pattern, results);
        search(repository.getRealWorldApps(), pattern, results);
        search(repository.getTools(), pattern, results);
        search(repository.getVideos(), pattern, results);
        search(repository.getNews(), pattern, results);
        search(repository.getDownloads(), pattern, results);
        search(repository.getTutorials(), pattern, results);
        search(repository.getTips(), pattern, results);
        search(repository.getIkonliPacks(), pattern, results);

        if (!OSUtil.isNative()) {
            search(repository.getUtilities(), pattern, results);
        }

        search(repository.getLearnJavaFX(), pattern, results);
        search(repository.getLearnRaspberryPi(), pattern, results);
        search(repository.getLearnMobile(), pattern, results);

        return results;
    }

    private void search(List<? extends ModelObject> modelObjects, String pattern, List<ModelObject> result) {
        modelObjects.forEach(mo -> {
            if (mo.matches(pattern)) {
                result.add(mo);
            }
        });
    }

    private final BooleanProperty used = new SimpleBooleanProperty(this, "used");

    public boolean isUsed() {
        return used.get();
    }

    public BooleanProperty usedProperty() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used.set(used);
    }

    private void activateModePseudoClass() {
        Mode mode = getMode();
        pseudoClassStateChanged(LIGHT_PSEUDOCLASS_STATE, mode == Mode.LIGHT);
        pseudoClassStateChanged(DARK_PSEUDOCLASS_STATE, mode == Mode.DARK);
    }

    private BooleanBinding blocking;

    protected void layoutBySize() {
        searchField = createSearchField();

        if (isLarge()) {
            if (!getStyleClass().contains("normal-page")) {
                getStyleClass().add("normal-page");
            }
            MenuButton resourcesBtn = createMenuButton("Resources");
            resourcesBtn.getStyleClass().add("resources-button");

            MenuButton communityBtn = createMenuButton("Community");
            communityBtn.getStyleClass().add("community-button");

            fillResourcesMenu(resourcesBtn);
            fillCommunityMenu(communityBtn);

            Button showcasesBtn = new Button("Showcases");
            showcasesBtn.setFocusTraversable(false);
            showcasesBtn.setMinWidth(Region.USE_PREF_SIZE);
            showcasesBtn.getStyleClass().add("showcases-button");
            LinkUtil.setLink(showcasesBtn, PagePath.SHOWCASES);

            MenuButton learnBtn = createMenuButton("Learn");
            learnBtn.getStyleClass().add("learn-button");
            fillLearnMenu(learnBtn);

            Button utilitiesBtn = new Button("Utilities");
            utilitiesBtn.setFocusTraversable(false);
            utilitiesBtn.setMinWidth(Region.USE_PREF_SIZE);
            utilitiesBtn.getStyleClass().add("online-tools-button");
            LinkUtil.setLink(utilitiesBtn, PagePath.UTILITIES);

            Button loginBtn = new Button("Login", new FontIcon(JFXCentralIcon.LOG_IN));
            loginBtn.setFocusTraversable(false);
            loginBtn.setVisible(SocialUtil.isSocialFeaturesEnabled());
            loginBtn.setManaged(SocialUtil.isSocialFeaturesEnabled());
            loginBtn.setMinWidth(Region.USE_PREF_SIZE);
            loginBtn.getStyleClass().add("login-button");
            LinkUtil.setLink(loginBtn, PagePath.LOGIN);

            Region separatorRegion = createSeparatorRegion();
            separatorRegion.visibleProperty().bind(loginBtn.visibleProperty());
            separatorRegion.managedProperty().bind(loginBtn.managedProperty());

            searchField.setVisible(true);
            searchField.setMinWidth(Region.USE_PREF_SIZE);

            contentBox.getChildren().setAll(logoWrapper, new Spacer(), createHomeButton(), resourcesBtn, communityBtn, showcasesBtn, learnBtn);
            if (!OSUtil.isNative()) {
                contentBox.getChildren().add(utilitiesBtn);
            }

            contentBox.getChildren().addAll(separatorRegion, loginBtn, searchField);
        } else {
            getStyleClass().remove("normal-page");
            Region logoutRegion = new Region();
            logoutRegion.getStyleClass().add("logout-region");

            Button logOutBtn = new Button(null, logoutRegion);
            logOutBtn.setFocusTraversable(false);
            logOutBtn.getStyleClass().add("logout-button");
            logOutBtn.setVisible(SocialUtil.isSocialFeaturesEnabled());
            logOutBtn.setManaged(SocialUtil.isSocialFeaturesEnabled());

            Region searchRegion = new Region();
            searchRegion.getStyleClass().add("search-region");

            Button searchBtn = new Button(null, searchRegion);
            searchBtn.setFocusTraversable(false);

            StackPane stackPane = new StackPane(searchField, searchBtn);
            stackPane.getStyleClass().add("search-stack-pane");

            searchField.getEditor().setFocusTraversable(false);
            searchField.managedProperty().bind(searchField.visibleProperty());
            searchBtn.managedProperty().bind(searchBtn.visibleProperty());
            searchBtn.visibleProperty().bind(searchField.visibleProperty().not());
            searchField.setVisible(false);

            searchBtn.setOnAction(event -> {
                searchField.setVisible(true);
                if (!OSUtil.isAndroidOrIOS()) {
                    // on ios or android do not automatically request the focus,
                    // otherwise the keyboard will not show
                    Platform.runLater(() -> getSearchTextField().requestFocus());
                }
            });
            searchBtn.getStyleClass().add("search-button");

            MenuButton menuBtn = createMenuButton("Menu");
            menuBtn.setFocusTraversable(false);
            menuBtn.getStyleClass().add("top-menu-button");
            menuBtn.showingProperty().addListener(it -> {
                if (menuBtn.isShowing()) {
                    setShowHamburgerMenu(true);
                }
            });

            showHamburgerMenuProperty().addListener(it -> {
                if (!isShowHamburgerMenu()) {
                    menuBtn.hide();
                }
            });

            Region separatorRegion = createSeparatorRegion();
            separatorRegion.visibleProperty().bind(logOutBtn.visibleProperty());
            separatorRegion.managedProperty().bind(logOutBtn.managedProperty());

            Button homeButton = createHomeButton();

            if (OSUtil.isAndroidOrIOS()) {
                contentBox.getChildren().setAll(logoWrapper, new Spacer(), homeButton, createSeparatorRegion(), logOutBtn, separatorRegion, menuBtn);
            } else {
                homeButton.visibleProperty().bind(Bindings.createBooleanBinding(() -> {
                            if (isLarge() || isMedium()) {
                                return true;
                            } else {
                                return !searchField.isVisible();
                            }
                        }, sizeProperty(), searchField.visibleProperty())
                );

                Region separator2 = createSeparatorRegion();
                separator2.managedProperty().bind(homeButton.visibleProperty());
                separator2.visibleProperty().bind(homeButton.visibleProperty());

                contentBox.getChildren().setAll(logoWrapper, new Spacer(), logOutBtn, separatorRegion, homeButton, separator2, stackPane, createSeparatorRegion(), menuBtn);
            }
        }

        usedProperty().bind(blocking);
    }

    private Button createHomeButton() {
        Button homeBtn = new Button();
        homeBtn.setGraphic(new FontIcon(MaterialDesign.MDI_HOME));
        homeBtn.getStyleClass().add("home-button");
        homeBtn.managedProperty().bind(homeBtn.visibleProperty());
        homeBtn.setFocusTraversable(false);
        LinkUtil.setLink(homeBtn, PagePath.HOME);
        return homeBtn;
    }

    private void fillLearnMenu(MenuButton learnBtn) {
        MenuItem fxMenuItem = createMenuItem("Learn JavaFX", PagePath.LEARN_JAVAFX, IkonUtil.learnJavaFX);
        fxMenuItem.getStyleClass().add("learn-javafx-item");
        learnBtn.getItems().add(fxMenuItem);

        MenuItem mobileMenuItem = createMenuItem("Learn Mobile", PagePath.LEARN_MOBILE, IkonUtil.learnMobile);
        mobileMenuItem.getStyleClass().add("learn-mobile-item");
        learnBtn.getItems().add(mobileMenuItem);

        MenuItem raspberryPiMenuItem = createMenuItem("Learn Raspberry Pi", PagePath.LEARN_RASPBERRYPI, IkonUtil.learnRaspberryPi);
        raspberryPiMenuItem.getStyleClass().add("learn-raspberrypi-item");
        learnBtn.getItems().add(raspberryPiMenuItem);
    }

    private void fillResourcesMenu(MenuButton button) {
        button.getItems().add(createMenuItem("Libraries", PagePath.LIBRARIES, IkonUtil.getModelIkon(Library.class)));
        button.getItems().add(createMenuItem("Tools", PagePath.TOOLS, IkonUtil.getModelIkon(Tool.class)));
        button.getItems().add(createMenuItem("Videos", PagePath.VIDEOS, IkonUtil.getModelIkon(Video.class)));
        button.getItems().add(createMenuItem("Books", PagePath.BOOKS, IkonUtil.getModelIkon(Book.class)));
        button.getItems().add(createMenuItem("Blogs", PagePath.BLOGS, IkonUtil.getModelIkon(Blog.class)));
        button.getItems().add(createMenuItem("Tips", PagePath.TIPS, IkonUtil.getModelIkon(Tip.class)));
        button.getItems().add(createMenuItem("Tutorials", PagePath.TUTORIALS, IkonUtil.getModelIkon(Tutorial.class)));
        button.getItems().add(createMenuItem("Icons", PagePath.ICONS, IkonUtil.getModelIkon(IkonliPack.class)));
        button.getItems().add(createMenuItem("Documentation", PagePath.DOCUMENTATION, IkonUtil.getModelIkon(Documentation.class)));
        if (!mobile && !OSUtil.isAndroidOrIOS()) {
            button.getItems().add(createMenuItem("Downloads", PagePath.DOWNLOADS, IkonUtil.getModelIkon(Download.class)));
        }
    }

    private void fillCommunityMenu(MenuButton button) {
        button.getItems().add(createMenuItem("People", PagePath.PEOPLE, IkonUtil.getModelIkon(Person.class)));
        button.getItems().add(createMenuItem("Companies", PagePath.COMPANIES, IkonUtil.getModelIkon(Company.class)));
        button.getItems().add(createMenuItem("OpenJFX Project", PagePath.OPENJFX, MaterialDesign.MDI_GITHUB_BOX));
        button.getItems().add(createMenuItem("Links of the Week", PagePath.LINKS, IkonUtil.getModelIkon(LinksOfTheWeek.class)));
        button.getItems().add(createMenuItem("Meet the Team", PagePath.TEAM, JFXCentralIcon.TEAM));
        if (SocialUtil.isSocialFeaturesEnabled()) {
            button.getItems().add(createMenuItem("Top Content", PagePath.TOP, JFXCentralIcon.TOP_CONTENT));
        }
    }

    private MenuItem createMenuItem(String text, String url, Ikon ikon) {
        Label label = new Label(text, ikon != null ? new FontIcon(ikon) : null);
        // The StackPane is a workaround for the setLink.
        // setLink currently only works when the Parent is a Pane.
        StackPane wrapper = new StackPane(label);
        LinkUtil.setLink(label, url);
        return new CustomMenuItem(wrapper);
    }

    private MenuButton createMenuButton(String text) {
        MenuButton menuButton = new MenuButton(text);
        menuButton.setMinWidth(Region.USE_PREF_SIZE);
        menuButton.setFocusTraversable(false);
        if (blocking == null) {
            blocking = Bindings.createBooleanBinding(menuButton::isShowing, menuButton.showingProperty());
        } else {
            blocking = blocking.or(Bindings.createBooleanBinding(menuButton::isShowing, menuButton.showingProperty()));
        }
        return menuButton;
    }

    private final BooleanProperty showHamburgerMenu = new SimpleBooleanProperty(this, "showHamburgerMenu");

    public boolean isShowHamburgerMenu() {
        return showHamburgerMenu.get();
    }

    public BooleanProperty showHamburgerMenuProperty() {
        return showHamburgerMenu;
    }

    public void setShowHamburgerMenu(boolean showHamburgerMenu) {
        this.showHamburgerMenu.set(showHamburgerMenu);
    }

    private Node getSearchTextField() {
        if (searchTextField == null) {
            searchTextField = searchField.getEditor();
            searchTextField.focusedProperty().addListener((ob, ov, nv) -> {
                if (!nv) {
                    searchField.setVisible(false);
                }
            });
        }
        return searchTextField;
    }

    private Region createSeparatorRegion() {
        Region separator = new Region();
        separator.getStyleClass().add("separator-region");
        separator.setMinWidth(Region.USE_PREF_SIZE);
        return separator;
    }

    private final StyleableObjectProperty<Mode> mode = new StyleableObjectProperty<>(DEFAULT_MODE) {

        @Override
        public Object getBean() {
            return TopMenuBar.this;
        }

        @Override
        public String getName() {
            return "mode";
        }

        @Override
        public CssMetaData<? extends Styleable, Mode> getCssMetaData() {
            return StyleableProperties.MODE;
        }
    };

    public final StyleableObjectProperty<Mode> modeProperty() {
        return mode;
    }

    public final Mode getMode() {
        return mode.get();
    }

    public final void setMode(Mode mode) {
        modeProperty().set(mode);
    }

    private static class StyleableProperties {

        private static final CssMetaData<TopMenuBar, Mode> MODE = new CssMetaData<>("-fx-mode", new EnumConverter<>(Mode.class), DEFAULT_MODE) {

            @Override
            public boolean isSettable(TopMenuBar control) {
                return !control.mode.isBound();
            }

            @Override
            public StyleableProperty<Mode> getStyleableProperty(TopMenuBar control) {
                return control.modeProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(StackPane.getClassCssMetaData());
            styleables.add(MODE);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
}
