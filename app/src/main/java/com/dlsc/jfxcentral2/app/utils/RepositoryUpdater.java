package com.dlsc.jfxcentral2.app.utils;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral2.app.RepositoryManager;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import org.eclipse.jgit.lib.ProgressMonitor;

public class RepositoryUpdater {

    /*
     * The "refresh" parameter states whether the update is called due to the first setup of the
     * data repository or because of a call to the refresh page.
     */
    public void performUpdate(boolean refresh) {
        Thread thread = new Thread(() -> RepositoryManager.updateRepository(new ProgressMonitor() {

            double total;
            double acc;

            @Override
            public void showDuration(boolean b) {
            }

            @Override
            public void start(int i) {
            }

            @Override
            public void beginTask(String taskName, int totalWork) {
                // clean up some messy message we get
                final String name = taskName.replace("remote: ", "");

                if (totalWork == -1) {
                    Platform.runLater(() -> {
                        loadMessage.set(name);
                        loadPercentage.set(-1);
                    });
                } else {
                    Platform.runLater(() -> {
                        loadMessage.set(name);
                        loadPercentage.set(0);
                    });
                    acc = 0;
                    total = totalWork;
                }
            }

            @Override
            public void update(int completed) {
                if (total != ProgressMonitor.UNKNOWN) {
                    acc += completed;
                    int p = (int) ((acc / total) * 100);
                    Platform.runLater(() -> loadPercentage.set(p));
                } else {
                    Platform.runLater(() -> loadPercentage.set(-1));
                }
            }

            @Override
            public void endTask() {
                if (refresh) {
                    DataRepository.getInstance().reload();
                }
            }

            @Override
            public boolean isCancelled() {
                return false;
            }
        }));

        thread.setName("Repository Thread");
        thread.setDaemon(true);
        thread.start();
    }

    // load percentage

    private final ReadOnlyIntegerWrapper loadPercentage = new ReadOnlyIntegerWrapper(this, "loadPercentage", 0);

    public ReadOnlyIntegerProperty loadPercentageProperty() {
        return loadPercentage.getReadOnlyProperty();
    }

    public int getLoadPercentage() {
        return loadPercentage.get();
    }

    // load message

    private final ReadOnlyStringWrapper loadMessage = new ReadOnlyStringWrapper(this, "loadMessage", "");

    public ReadOnlyStringProperty loadMessageProperty() {
        return loadMessage.getReadOnlyProperty();
    }

    public String getLoadMessage() {
        return loadMessage.get();
    }

}
