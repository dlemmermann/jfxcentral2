package com.dlsc.jfxcentral2.app;

import com.dlsc.gemsfx.util.StageManager;
import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.app.filters.FooterFilter;
import com.dlsc.jfxcentral2.app.pages.CreditsPage;
import com.dlsc.jfxcentral2.app.pages.ErrorPage;
import com.dlsc.jfxcentral2.app.pages.LegalPage;
import com.dlsc.jfxcentral2.app.pages.LinksOfTheWeekPage;
import com.dlsc.jfxcentral2.app.pages.LoginPage;
import com.dlsc.jfxcentral2.app.pages.OpenJFXPage;
import com.dlsc.jfxcentral2.app.pages.RefreshPage;
import com.dlsc.jfxcentral2.app.pages.SingleIconPage;
import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.app.pages.TeamPage;
import com.dlsc.jfxcentral2.app.pages.TopContentPage;
import com.dlsc.jfxcentral2.app.pages.UserProfilePage;
import com.dlsc.jfxcentral2.app.pages.category.BlogsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.BooksCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.CompaniesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.DocumentationCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.DownloadsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.IconsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.LearnJavaFXCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.LearnMobileCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.LearnRaspberryPiCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.LibrariesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.PeopleCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.ShowcasesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.TipCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.ToolsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.TutorialsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.UtilitiesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.VideosCategoryPage;
import com.dlsc.jfxcentral2.app.pages.details.BlogDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.BookDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.CompanyDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.DownloadDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.IconPackDetailPage;
import com.dlsc.jfxcentral2.app.pages.details.LearnDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.LibraryDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.PersonDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.ShowcaseDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.TipDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.ToolDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.TutorialDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.UtilityDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.VideoDetailsPage;
import com.dlsc.jfxcentral2.app.stage.CustomStage;
import com.dlsc.jfxcentral2.app.utils.VideoPane;
import com.dlsc.jfxcentral2.components.PrettyScrollPane;
import com.dlsc.jfxcentral2.model.IconInfo;
import com.dlsc.jfxcentral2.model.IconInfoBuilder;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.PagePath;
import com.dlsc.jfxcentral2.utils.SocialUtil;
import com.gluonhq.attach.display.DisplayService;
import com.jpro.webapi.WebAPI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import one.jpro.platform.routing.LinkUtil;
import one.jpro.platform.routing.Request;
import one.jpro.platform.routing.Response;
import one.jpro.platform.routing.Route;
import one.jpro.platform.routing.RouteNode;
import one.jpro.platform.routing.View;
import one.jpro.platform.routing.dev.DevFilter;
import one.jpro.platform.routing.dev.StatisticsFilter;
import one.jpro.platform.routing.sessionmanager.SessionManager;
import org.apache.commons.lang3.StringUtils;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;

public class JFXCentral2App extends Application {

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    private TrayIconManager trayIconManager;

    static {
        if (WebAPI.isBrowser() || !RepositoryManager.isCountryEqualToChina()) {
            Locale.setDefault(Locale.US);
        }
    }

//    private static boolean freezeDetectorStarted = false;

    @Override
    public void start(Stage stage) {
        if (!OSUtil.isNative()) {
            // This is a workaround to prevent a deadlock between the TrayIcon and the JPro ImageManager
            BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            bi.createGraphics();
        }

        if (!WebAPI.isBrowser()) {
            System.setProperty("routing.scrollpane", PrettyScrollPane.class.getName());
            System.setProperty("prism.lcdtext", "false");

            if (OSUtil.isAWTSupported()) {
                if (Taskbar.isTaskbarSupported()) {
                    Taskbar taskbar = Taskbar.getTaskbar();
                    if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                        Image dockIcon = defaultToolkit.getImage(JFXCentral2App.class.getResource("app-icon.png"));
                        taskbar.setIconImage(dockIcon);
                    }
                }
            }
        }

        // set jpro.imagemanager.cache to ~/.jfxcentral/imagecache
        System.setProperty("jpro.imagemanager.cache", new File(new File(System.getProperty("user.home")), ".jfxcentral/imagecache").getAbsolutePath());
        System.out.println("jpro.imagemanager.cache: " + System.getProperty("jpro.imagemanager.cache"));

        stage.initStyle(StageStyle.UNDECORATED);

        // route node
        Route route = createRoute();
        RouteNode routeNode = new RouteNode(stage, route);

        // session manager
        SessionManager sessionManager = SessionManager.getDefault(routeNode, stage);
        routeNode.start(sessionManager);

        // tray icon
        if (!WebAPI.isBrowser() && OSUtil.isAWTSupported() && SystemTray.isSupported()) {
            RepositoryManager.repositoryUpdatedProperty().addListener(it -> {
                if (trayIconManager == null) {
                    trayIconManager = new TrayIconManager(stage, sessionManager);
                } else {
                    trayIconManager.refresh();
                }
            });
        }

        Parent parent = routeNode;

        if (OSUtil.isAndroidOrIOS()) {
            StackPane notchPane = new StackPane();
            notchPane.getStyleClass().add("notch-pane");
            VBox.setVgrow(routeNode, Priority.ALWAYS);
            parent = new VBox(notchPane, routeNode);

            DisplayService.create().ifPresent(service -> {
                if (service.hasNotch()) {
                    notchPane.getStyleClass().add("notch");
                }
            });
        }

        VideoPane videoPane = new VideoPane(size);
        videoPane.onCloseProperty().bind(onCloseVideoPaneProperty());
        videoPane.visibleProperty().bind(onCloseVideoPaneProperty().isNotNull());
        setOnCloseVideoPane(() -> {
        });

        StackPane wrapper = new StackPane(parent, videoPane);

        // customs stage for decorations / the chrome
        CustomStage customStage = new CustomStage(stage, parent, sessionManager, size);
        customStage.setCloseHandler(() -> {
            if (!OSUtil.isNative()) {
                if (SystemTray.isSupported() && trayIconManager != null) {
                    trayIconManager.hide();
                }
            }
            stage.close();
        });

        parent.setOnMousePressed(evt -> routeNode.requestFocus());

        // scene
        Scene scene = new Scene(customStage, 1400, 800);
        scene.setFill(Color.web("#070B32"));
        scene.widthProperty().addListener((it -> updateSizeProperty(scene)));
        scene.getStylesheets().add(Objects.requireNonNull(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css")).toExternalForm());
        // scene.focusOwnerProperty().addListener(it -> System.out.println("focus owner: " + scene.getFocusOwner()));
        updateSizeProperty(scene);

        stage.setScene(scene);
        stage.setFullScreenExitHint("");

        // do not store stage width, height, location when we are running in a browser
        if (!WebAPI.isBrowser()) {
            StageManager.install(stage, "com/dlsc/jfxcentral2", 500, 800);
            // Mike Hearn explicitly requested to use this approach to exit the app
            stage.setOnCloseRequest(evt -> System.exit(0));
        }

        // add client URL handler
        if (OSUtil.isAWTSupported()) {
            addClientUrlHandler(scene, sessionManager);
        }

        stage.show();
    }

    private final ObjectProperty<Runnable> onCloseVideoPane = new SimpleObjectProperty<>(this, "onCloseVideoPane");

    public Runnable getOnCloseVideoPane() {
        return onCloseVideoPane.get();
    }

    public ObjectProperty<Runnable> onCloseVideoPaneProperty() {
        return onCloseVideoPane;
    }

    public void setOnCloseVideoPane(Runnable onClose) {
        this.onCloseVideoPane.set(onClose);
    }

    public Route createRoute() {
        Route route = Route.empty()
                .and(Route.get("/", r -> {
                    if (RepositoryManager.isRepositoryUpdated()) {
                        return Response.view(new StartPage(size));
                    } else {
                        return Response.redirect("/refresh");
                    }
                }))
                .and(Route.redirect("/home", "/"))
                .and(Route.redirect("/index", "/"))
                .and(createCategoryOrDetailRoute(PagePath.BLOGS, Blog.class, () -> new BlogsCategoryPage(size), id -> new BlogDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.BOOKS, Book.class, () -> new BooksCategoryPage(size), id -> new BookDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.COMPANIES, Company.class, () -> new CompaniesCategoryPage(size), id -> new CompanyDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.DOWNLOADS, Download.class, () -> new DownloadsCategoryPage(size), id -> new DownloadDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.LIBRARIES, Library.class, () -> new LibrariesCategoryPage(size), id -> new LibraryDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.PEOPLE, Person.class, () -> new PeopleCategoryPage(size), id -> new PersonDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.REAL_WORLD, RealWorldApp.class, () -> new ShowcasesCategoryPage(size), id -> new ShowcaseDetailsPage(size, id))) // legacy routing for real world apps / showcases
                .and(createCategoryOrDetailRoute(PagePath.SHOWCASES, RealWorldApp.class, () -> new ShowcasesCategoryPage(size), id -> new ShowcaseDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.TIPS, Tip.class, () -> new TipCategoryPage(size), id -> new TipDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.TOOLS, Tool.class, () -> new ToolsCategoryPage(size), id -> new ToolDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.TUTORIALS, Tutorial.class, () -> new TutorialsCategoryPage(size), id -> new TutorialDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute(PagePath.VIDEOS, Video.class, () -> new VideosCategoryPage(size), id -> new VideoDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.UTILITIES, Utility.class, () -> new UtilitiesCategoryPage(size), id -> new UtilityDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute(PagePath.LEARN_JAVAFX, LearnJavaFX.class, () -> new LearnJavaFXCategoryPage(size), id -> new LearnDetailsPage(size, LearnJavaFX.class, id)))
                .and(createCategoryOrDetailRoute(PagePath.LEARN_MOBILE, LearnMobile.class, () -> new LearnMobileCategoryPage(size), id -> new LearnDetailsPage(size, LearnMobile.class, id)))
                .and(createCategoryOrDetailRoute(PagePath.LEARN_RASPBERRYPI, LearnRaspberryPi.class, () -> new LearnRaspberryPiCategoryPage(size), id -> new LearnDetailsPage(size, LearnRaspberryPi.class, id)))
                .and(createIconPackOrDetailRoute(PagePath.ICONS))
                .and(Route.get(PagePath.CREDITS, r -> Response.view(new CreditsPage(size))))
                .and(Route.get(PagePath.LEGAL, r -> Response.view(new LegalPage(size, LegalPage.Section.TERMS))))
                .and(Route.get(PagePath.LEGAL_TERMS, r -> Response.view(new LegalPage(size, LegalPage.Section.TERMS))))
                .and(Route.get(PagePath.LEGAL_COOKIES, r -> Response.view(new LegalPage(size, LegalPage.Section.COOKIES))))
                .and(Route.get(PagePath.LEGAL_PRIVACY, r -> Response.view(new LegalPage(size, LegalPage.Section.PRIVACY))))
                .and(Route.get(PagePath.LINKS, r -> Response.view(new LinksOfTheWeekPage(size))))
                .and(Route.get(PagePath.TEAM, r -> Response.view(new TeamPage(size))))
                .and(Route.get(PagePath.OPENJFX, r -> Response.view(new OpenJFXPage(size))))
                .and(Route.get(PagePath.DOCUMENTATION, r -> Response.view(new DocumentationCategoryPage(size))))
                .filter(FooterFilter.create(size))
                .and(Route.get(PagePath.REFRESH, r -> {
                    RepositoryManager.prepareForRefresh();
                    return Response.view(new RefreshPage(size));
                }));

        // the following routes are only needed when we support user login

        if (SocialUtil.isSocialFeaturesEnabled()) {
            route = route.and(Route.get(PagePath.LOGIN, r -> Response.view(new LoginPage(size))))
                    .and(Route.get(PagePath.TOP, r -> Response.view(new TopContentPage(size))))
                    .and(Route.get(PagePath.PROFILE, r -> Response.view(new UserProfilePage(size))));
        }

        route = route.and(r -> Response.view(new ErrorPage(size, r)));

        if (Boolean.getBoolean("develop")) {
            route = route.filter(DevFilter.create());
            route = route.filter(StatisticsFilter.create());
        }
        // if (!freezeDetectorStarted) {
        //    new FreezeDetector(Duration.ofMillis(100));
        //    freezeDetectorStarted = true;
        //}

        return route;
    }

    private Route createCategoryOrDetailRoute(String path, Class<? extends ModelObject> clazz, Supplier<View> masterResponse, Callback<String, View> detailedResponse) {
        return r -> {
            if (r.getPath().startsWith(path)) {
                return createResponse(r, clazz, masterResponse, detailedResponse);
            }
            return Response.empty();
        };
    }

    private Response createResponse(Request request, Class<? extends ModelObject> clazz, Supplier<View> categoryResponse, Callback<String, View> detailedResponse) {
        int index = request.getPath().lastIndexOf("/");
        if (index > 0) {
            String id = request.getPath().substring(index + 1).trim();
            if (!DataRepository.getInstance().isValidId(clazz, id)) {
                return Response.view(new ErrorPage(size, request));
            }

            return Response.view(detailedResponse.call(id));
        }

        return Response.view(categoryResponse.get());
    }

    /**
     * Creates a route that handles the icon pack and icon detail pages.
     * /icons -> IconsCategoryPage
     * /icons/{ikonPackId} -> IconPackDetailPage
     * /icons/{ikonliPackId}/{iconDescription} -> SingleIconPage
     */
    private Route createIconPackOrDetailRoute(String path) {
        return r -> {
            String requestPath = r.getPath();
            if (!requestPath.startsWith(path)) {
                return Response.empty();
            }
            String[] parts = StringUtils.split(requestPath, '/');
            // Route to IconsCategoryPage or IconPackDetailPage: /icons or /icons/{ikonPackId}
            if (parts.length < 3) {
                return createResponse(r, IkonliPack.class, () -> new IconsCategoryPage(size), id -> new IconPackDetailPage(size, id));
            }

            //  Route to SingleIconPage: /icons/{ikonliPackId}/{iconDescription}
            String ikonliPackId = parts[1];
            String iconDescription = parts[2];

            return DataRepository.getInstance().getIkonliPackById(ikonliPackId)
                    .flatMap(ikonliPack -> IkonliPackUtil.getInstance().getIkon(ikonliPack, iconDescription)
                            .map(ikon -> {
                                IconInfo iconInfo = new IconInfoBuilder(ikon, ikonliPack.getName(), ikonliPackId).build();
                                return Response.view(new SingleIconPage(size, iconInfo, true));
                            }))
                    .orElseGet(() -> Response.view(new ErrorPage(size, r)));
        };
    }

    private void updateSizeProperty(Scene scene) {
        double sceneWidth = scene.getWidth();
        if (sceneWidth < 760) {
            size.set(Size.SMALL);
        } else if (sceneWidth <= 1320) {
            size.set(Size.MEDIUM);
        } else {
            size.set(Size.LARGE);
        }
    }


    /**
     * Adds a handler for processing client URLs.
     */
    public void addClientUrlHandler(Scene scene, SessionManager sessionManager) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.APP_OPEN_URI)) {
            desktop.setOpenURIHandler(e -> Platform.runLater(() -> {
                ((Stage) scene.getWindow()).toFront();
                String uri = e.getURI().toString();
                String url = StringUtils.substringAfter(uri, "/");
                LinkUtil.gotoPage(sessionManager, url);
            }));
        } else {
            System.out.println("Does not support opening the custom schema URIs");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
