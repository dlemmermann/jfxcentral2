package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tutorial;

public class TutorialOverviewBox extends SimpleOverviewBox<Tutorial> {

    public TutorialOverviewBox(Tutorial tutorial) {
        super(tutorial);
        getStyleClass().add("tutorial-overview-box");
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "tutorials/" + getModel().getId());
        setMarkdown(DataRepository.getInstance().getTutorialReadMe(tutorial));
        imageProperty().bind(ImageManager.getInstance().tutorialImageLargeProperty(tutorial));
    }
}
