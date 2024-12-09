package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tip;

public class TipOverviewBox extends OverviewBox<Tip> {

    public TipOverviewBox(Tip tip) {
        super(tip);
        getStyleClass().add("tip-overview-box");
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "tips/" + getModel().getId());
        setMarkdown(DataRepository.getInstance().getTipReadMe(tip));
    }
}
