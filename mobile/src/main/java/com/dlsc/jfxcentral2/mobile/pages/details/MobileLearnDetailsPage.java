package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral2.mobile.components.LearnPagination;
import com.dlsc.jfxcentral2.mobile.components.MobilePageHeader;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MobileLearnDetailsPage extends MobileDetailsPageBase<Learn> {

    public MobileLearnDetailsPage(ObjectProperty<Size> size, Class<? extends Learn> clazz, String itemId) {
        super(size, clazz, itemId);
    }

    @Override
    public List<Node> content() {
        Learn learn = getItem();

        // header
        MobilePageHeader header = new MobilePageHeader();
        header.sizeProperty().bind(sizeProperty());

        // content
        LearnPagination<Learn> detailsView = new LearnPagination<>();
        if (learn instanceof LearnJavaFX) {
            detailsView.setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "learn/javafx/" + learn.getId());
            List<LearnJavaFX> learnJavaFX = DataRepository.getInstance().getLearnJavaFX();
            detailsView.getItems().setAll(learnJavaFX);
        } else if (learn instanceof LearnMobile) {
            detailsView.setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "learn/mobile/" + learn.getId());
            List<LearnMobile> learnMobile = DataRepository.getInstance().getLearnMobile();
            detailsView.getItems().setAll(learnMobile);
        } else if (learn instanceof LearnRaspberryPi) {
            List<LearnRaspberryPi> learnRaspberryPi = DataRepository.getInstance().getLearnRaspberryPi();
            detailsView.setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "learn/raspberrypi/" + learn.getId());
            detailsView.getItems().setAll(learnRaspberryPi);
        }
        detailsView.setSelectedItem(learn);

        // bind header title to item name
        header.titleProperty().bind(detailsView.itemProperty().map(item -> item == null ? "" : item.getName()));

        VBox.setVgrow(detailsView, Priority.ALWAYS);
        return List.of(header, detailsView);
    }

}
