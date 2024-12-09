package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral2.components.HomePageTopView;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.VideoGalleryView;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.QuickLinksGenerator;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public class StartPage extends PageBase {

    public StartPage(ObjectProperty<Size> size) {
        super(size, Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral";
    }

    @Override
    public String description() {
        return "A central place for anything related to JavaFX.";
    }

    @Override
    public Node content() {
        // HomePage TopView
        HomePageTopView homePageTopView = new HomePageTopView();
        homePageTopView.sizeProperty().bind(sizeProperty());

        // links of the Week
        List<LinksOfTheWeek> linksOfTheWeek = DataRepository.getInstance().getLinksOfTheWeek();

        WeekLinksLiteView weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.sizeProperty().bind(sizeProperty());
        weekLinksLiteView.setLinksOfTheWeek(linksOfTheWeek.get(linksOfTheWeek.size() - 1));

        // website changes
        List<QuickLink> changes = QuickLinksGenerator.generateWebsiteChangesQuickLinks(sizeProperty());

        WebsiteChangesView websiteChangesView = new WebsiteChangesView();
        websiteChangesView.sizeProperty().bind(sizeProperty());
        websiteChangesView.getQuickLinks().setAll(changes);
        websiteChangesView.setVisible(!changes.isEmpty());
        websiteChangesView.setManaged(!changes.isEmpty());

        // video gallery
        VideoGalleryView videoGallery = new VideoGalleryView();
        videoGallery.sizeProperty().bind(sizeProperty());
        videoGallery.getVideos().setAll(randomSubList(DataRepository.getInstance().getVideos(), 12));
        videoGallery.blockingProperty().addListener(it -> setBlocking(videoGallery.isBlocking()));
        videoGallery.onCloseGlassPaneProperty().addListener(it -> setOnCloseGlassPane(videoGallery.getOnCloseGlassPane()));
        return wrapContent(homePageTopView, weekLinksLiteView, websiteChangesView, videoGallery);
    }
}
