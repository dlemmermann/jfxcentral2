package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tool;

public class ToolOverviewBox extends SimpleOverviewBox<Tool> {

    public ToolOverviewBox(Tool tool) {
        super(tool);
        getStyleClass().add("tool-overview-box");
        setBaseURL(DataRepository.getInstance().getRepositoryDirectoryURL() + "tools/" + getModel().getId());
        setMarkdown(DataRepository.getInstance().getToolReadMe(tool));
    }
}
