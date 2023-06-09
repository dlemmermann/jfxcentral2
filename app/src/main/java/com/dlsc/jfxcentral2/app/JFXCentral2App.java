package com.dlsc.jfxcentral2.app;

import com.dlsc.gemsfx.util.StageManager;
import com.dlsc.jfxcentral2.app.pages.LegalPage;
import com.dlsc.jfxcentral2.app.pages.LinksOfTheWeekPage;
import com.dlsc.jfxcentral2.app.pages.LoginPage;
import com.dlsc.jfxcentral2.app.pages.OpenJFXPage;
import com.dlsc.jfxcentral2.app.pages.RefreshPage;
import com.dlsc.jfxcentral2.app.pages.StartPage;
import com.dlsc.jfxcentral2.app.pages.UserProfilePage;
import com.dlsc.jfxcentral2.app.pages.category.BlogsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.BooksCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.CompaniesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.DownloadsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.IconsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.LibrariesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.PeopleCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.ShowcasesCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.TipCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.ToolsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.TutorialsCategoryPage;
import com.dlsc.jfxcentral2.app.pages.category.VideosCategoryPage;
import com.dlsc.jfxcentral2.app.pages.details.BlogDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.BookDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.CompanyDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.DownloadDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.IconPackDetailPage;
import com.dlsc.jfxcentral2.app.pages.details.LibraryDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.PersonDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.ShowcaseDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.TipDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.ToolDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.TutorialDetailsPage;
import com.dlsc.jfxcentral2.app.pages.details.VideoDetailsPage;
import com.dlsc.jfxcentral2.app.stage.CustomStage;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.NodeUtil;
import com.jpro.webapi.WebAPI;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import one.jpro.routing.Request;
import one.jpro.routing.Response;
import one.jpro.routing.Route;
import one.jpro.routing.RouteNode;
import one.jpro.routing.RouteUtils;
import one.jpro.routing.dev.DevFilter;
import one.jpro.routing.sessionmanager.SessionManager;
import simplefx.experimental.parts.FXFuture;

import java.util.function.Supplier;

public class JFXCentral2App extends Application {

    static {
        LoadRepository.requestInitialUpdate();
    }

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(Size.LARGE);

    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);

        // route node
        Route route = createRoute();
        RouteNode routeNode = new RouteNode(stage, route);

        // session manager
        SessionManager sessionManager = SessionManager.getDefault(routeNode, stage);
        routeNode.start(sessionManager);

        // customs stage for decorations / the chrome
        CustomStage customStage = new CustomStage(stage, routeNode, sessionManager);
        customStage.setCloseHandler(stage::close);

        // scene
        Scene scene = new Scene(customStage, 1400, 800);
        scene.setFill(Color.web("070B32"));
        scene.widthProperty().addListener((it -> updateSizeProperty(scene)));
        scene.widthProperty().addListener(it -> System.out.println("width: " + scene.getWidth()));
        scene.getStylesheets().add(NodeUtil.class.getResource("/com/dlsc/jfxcentral2/theme.css").toExternalForm());
        scene.focusOwnerProperty().addListener(it -> System.out.println("new focus owner: " + scene.getFocusOwner()));

        updateSizeProperty(scene);

        stage.setScene(scene);

        // do not store stage width, height, location when we are running in a browser
        if (!WebAPI.isBrowser()) {
            StageManager.install(stage, "com/dlsc/jfxcentral2", 500, 800);
        }

        stage.show();
    }

    private Route createRoute() {
        Route route = Route.empty()
                .and(RouteUtils.get("/", r -> new StartPage(size)))
                .and(createCategoryOrDetailRoute("/blogs", () -> new BlogsCategoryPage(size), id -> new BlogDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/books", () -> new BooksCategoryPage(size), id -> new BookDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/companies", () -> new CompaniesCategoryPage(size), id -> new CompanyDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/downloads", () -> new DownloadsCategoryPage(size), id -> new DownloadDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/libraries", () -> new LibrariesCategoryPage(size), id -> new LibraryDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/people", () -> new PeopleCategoryPage(size), id -> new PersonDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/real_world", () -> new ShowcasesCategoryPage(size), id -> new ShowcaseDetailsPage(size, id))) // legacy routing for real world apps / showcases
                .and(createCategoryOrDetailRoute("/showcases", () -> new ShowcasesCategoryPage(size), id -> new ShowcaseDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/tips", () -> new TipCategoryPage(size), id -> new TipDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/tools", () -> new ToolsCategoryPage(size), id -> new ToolDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/tutorials", () -> new TutorialsCategoryPage(size), id -> new TutorialDetailsPage(size, id))) // new routing for showcases
                .and(createCategoryOrDetailRoute("/videos", () -> new VideosCategoryPage(size), id -> new VideoDetailsPage(size, id)))
                .and(createCategoryOrDetailRoute("/icons", () -> new IconsCategoryPage(size), id -> new IconPackDetailPage(size, id)))
                .and(RouteUtils.get("/legal", r -> new LegalPage(size, LegalPage.Section.TERMS)))
                .and(RouteUtils.get("/legal/terms", r -> new LegalPage(size, LegalPage.Section.TERMS)))
                .and(RouteUtils.get("/legal/cookies", r -> new LegalPage(size, LegalPage.Section.COOKIES)))
                .and(RouteUtils.get("/legal/privacy", r -> new LegalPage(size, LegalPage.Section.PRIVACY)))
                .and(RouteUtils.get("/links", r -> new LinksOfTheWeekPage(size)))
                .and(RouteUtils.get("/links/rss", r -> new LinksOfTheWeekPage(size))) // TODO: how to return raw data?
                .and(RouteUtils.get("/login", r -> new LoginPage(size)))
                .and(RouteUtils.get("/openjfx", r -> new OpenJFXPage(size)))
                .and(RouteUtils.get("/profile", r -> new UserProfilePage(size)))
                .and(RouteUtils.get("/refresh", r -> new RefreshPage(size)));

        if (Boolean.getBoolean("develop")) {
            route = route.filter(DevFilter.create());
        }

        return route;
    }

    private Route createCategoryOrDetailRoute(String path, Supplier<Response> masterResponse, Callback<String, Response> detailedResponse) {
        return r -> {
            if (r.path().startsWith(path)) {
                return FXFuture.apply(() -> createResponse(r, masterResponse, detailedResponse));
            }

            return null;
        };
    }

    private Response createResponse(Request request, Supplier<Response> categoryResponse, Callback<String, Response> detailedResponse) {
        int index = request.path().lastIndexOf("/");
        if (index > 0) {
            String id = request.path().substring(index + 1).trim();
            return detailedResponse.call(id);
        }

        return categoryResponse.get();
    }

    private void updateSizeProperty(Scene scene) {
        double sceneWidth = scene.getWidth();
        if (sceneWidth < 768) {
            size.set(Size.SMALL);
        } else if (sceneWidth < 1172) {
            size.set(Size.MEDIUM);
        } else {
            size.set(Size.LARGE);
        }
    }
}
