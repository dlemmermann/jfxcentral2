package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Download;

/*
 * We do not display an image in the overview box for downloads. Not needed.
 */
public class DownloadOverviewBox extends SimpleOverviewBox<Download> {

    public DownloadOverviewBox(Download download) {
        super(download);
        getStyleClass().add("download-overview-box");
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "downloads/" + getModel().getId());
        setMarkdown(DataRepository.getInstance().getDownloadReadMe(download));
    }
}
