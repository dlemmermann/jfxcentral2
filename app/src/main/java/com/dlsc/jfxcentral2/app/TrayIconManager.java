package com.dlsc.jfxcentral2.app;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.utils.LOGGER;
import com.dlsc.jfxcentral2.utils.OSUtil;
import com.dlsc.jfxcentral2.utils.PageUtil;
import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import one.jpro.platform.routing.sessionmanager.SessionManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;

public class TrayIconManager {

    private final SessionManager sessionManager;
    private final Stage stage;
    private final FXTrayIcon trayIcon;

    public TrayIconManager(Stage stage, SessionManager sessionManager) {
        this.stage = stage;
        this.sessionManager = sessionManager;

        if (OSUtil.isWindows()) {
            Dimension trayIconSize = SystemTray.getSystemTray().getTrayIconSize();

            java.awt.Image image = null;
            URL url = null;
            try {
                url = getClass().getResource("tray-icon2-windows.png");
                assert url != null;
                image = ImageIO.read(url);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            assert image != null;

            trayIcon = new FXTrayIcon(stage);
            trayIcon.setGraphic(image.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH));
        } else {
            trayIcon = new FXTrayIcon(stage, Objects.requireNonNull(JFXCentral2App.class.getResource("tray-icon2-dark.png")), 350, 210);
        }
        refresh();
        trayIcon.show();
    }

    public void hide() {
        trayIcon.hide();
    }

    public void refresh() {
        trayIcon.clear();

        Menu libraries = new Menu("Libraries");
        Menu people = new Menu("People");
        Menu tools = new Menu("Tools");
        Menu blogs = new Menu("Blogs");
        Menu videos = new Menu("Videos");
        Menu tutorials = new Menu("Tutorials");
        Menu downloads = new Menu("Downloads");
        Menu companies = new Menu("Companies");
        Menu books = new Menu("Books");
        Menu realWorld = new Menu("Real World Apps");
        Menu tips = new Menu("Tips & Tricks");
        Menu icons = new Menu("Icons");
        Menu utilities = new Menu("Utilities");
        Menu documentation = new Menu("Documentation");

        DataRepository repository = DataRepository.getInstance();

        repository.getTools().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(tools, mo));
        repository.getPeople().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(people, mo));
        repository.getTutorials().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(tutorials, mo));
        repository.getCompanies().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(companies, mo));
        repository.getDownloads().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(downloads, mo));
        repository.getBlogs().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(blogs, mo));
        repository.getLibraries().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(libraries, mo));
        repository.getBooks().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(books, mo));
        repository.getRealWorldApps().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(realWorld, mo));
        repository.getTips().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(tips, mo));
        repository.getIkonliPacks().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(icons, mo));
        repository.getUtilities().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(utilities, mo));
        repository.getDocumentation().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(documentation, mo, modelObject -> {
            String docUrl = null;
            try {
                if (modelObject instanceof Documentation doc) {
                    docUrl = doc.getUrl();
                    Desktop.getDesktop().browse(URI.create(docUrl));
                }
            } catch (IOException e) {
                LOGGER.error("Failed to browse the documentation URL: {}", docUrl, e);
            }
        }));
        repository.getVideos().stream().sorted(Comparator.comparing(ModelObject::getName)).forEach(mo -> createMenuItem(videos, mo, modelObject -> {
            String videoUrl = null;
            try {
                videoUrl = "https://www.youtube.com/watch?v=" + modelObject.getId();
                Desktop.getDesktop().browse(URI.create(videoUrl));
            } catch (IOException e) {
                LOGGER.error("Failed to browse the YouTube video URL: {}", videoUrl , e);
            }
        }));

        MenuItem showApp = new MenuItem("Main Window");
        showApp.setOnAction(evt -> {
            stage.show();
            stage.setIconified(false);
            stage.toFront();
        });
        trayIcon.addMenuItem(showApp);

        MenuItem openjfx = new MenuItem("Visit openjfx.io");
        openjfx.setOnAction(evt -> showURL("https://openjfx.io"));
        trayIcon.addMenuItem(openjfx);

        trayIcon.addSeparator();

        MenuItem addInfoToJfxCentral = new MenuItem("Add Info to JFX-Central");
        addInfoToJfxCentral.setOnAction(evt -> showURL("https://github.com/dlsc-software-consulting-gmbh/jfxcentral-data"));
        trayIcon.addMenuItem(addInfoToJfxCentral);

        MenuItem reportIssue = new MenuItem("Report an Issue");
        reportIssue.setOnAction(evt -> showURL("https://github.com/dlsc-software-consulting-gmbh/jfxcentral2/issues"));
        trayIcon.addMenuItem(reportIssue);

        trayIcon.addSeparator();
        trayIcon.addMenuItem(utilities);
        trayIcon.addSeparator();

        trayIcon.addMenuItem(documentation);
        trayIcon.addMenuItem(libraries);
        trayIcon.addMenuItem(tools);
        trayIcon.addMenuItem(books);
        trayIcon.addMenuItem(people);
        trayIcon.addMenuItem(blogs);
        trayIcon.addMenuItem(videos);
        trayIcon.addMenuItem(tutorials);
        trayIcon.addMenuItem(companies);
        trayIcon.addMenuItem(downloads);
        trayIcon.addMenuItem(realWorld);
        trayIcon.addMenuItem(tips);
        trayIcon.addMenuItem(icons);

        trayIcon.addExitItem(true);
    }

    private void showURL(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            LOGGER.error("Failed to open the URL: {}", url, e);
        }
    }

    private void createMenuItem(Menu menu, ModelObject mo) {
        createMenuItem(menu, mo, this::showModelObject);
    }

    private void createMenuItem(Menu menu, ModelObject mo, Consumer<ModelObject> consumer) {
        MenuItem item = new MenuItem(mo.getName());
        item.setOnAction(evt -> consumer.accept(mo));
        menu.getItems().add(item);
    }

    private void showModelObject(ModelObject mo) {
        sessionManager.gotoURL(PageUtil.getLink(mo));
        stage.show();
        stage.toFront();
    }
}
