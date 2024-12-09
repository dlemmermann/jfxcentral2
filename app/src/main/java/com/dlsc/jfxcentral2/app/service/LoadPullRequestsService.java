package com.dlsc.jfxcentral2.app.service;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.pull.PullRequest;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class LoadPullRequestsService extends Service<List<PullRequest>> {

    @Override
    protected Task<List<PullRequest>> createTask() {
        return new Task<>() {
            @Override
            protected List<PullRequest> call() {
                List<PullRequest> pullRequests = DataRepository.getInstance().loadPullRequests();
                updateProgress(1, 1);
                return pullRequests;
            }
        };
    }
}
