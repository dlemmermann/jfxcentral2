package com.dlsc.jfxcentral2.components.topcontent;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.PaneBase;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.VBox;

import java.util.List;

public class TopContentContainerView extends PaneBase {
    public TopContentContainerView() {
        getStyleClass().add("top-content-container-view");

        TopContentView<Tool> toolsTop = new TopContentView<>();
        getStyleClass().add("tools-top-view");
        toolsTop.sizeProperty().bind(sizeProperty());
        toolsTop.setTitle("Tools");
        toolsTop.setIcon(IkonUtil.tool);

        TopContentView<Library> librariesTop = new TopContentView<>();
        getStyleClass().add("libraries-top");
        librariesTop.sizeProperty().bind(sizeProperty());
        librariesTop.setTitle("Libraries");
        librariesTop.setIcon(IkonUtil.library);

        TopContentView<Blog> blogsTop = new TopContentView<>();
        getStyleClass().add("blogs-top");
        blogsTop.sizeProperty().bind(sizeProperty());
        blogsTop.setTitle("Blogs");
        blogsTop.setIcon(IkonUtil.blog);

        TopContentView<Video> videosTop = new TopContentView<>();
        getStyleClass().add("videos-top");
        videosTop.sizeProperty().bind(sizeProperty());
        videosTop.setTitle("Videos");
        videosTop.setIcon(IkonUtil.video);

        TopContentView<Download> downloadTop = new TopContentView<>();
        getStyleClass().add("downloads-top");
        downloadTop.sizeProperty().bind(sizeProperty());
        downloadTop.setTitle("Downloads");
        downloadTop.setIcon(IkonUtil.download);

        TopContentView<Book> booksTop = new TopContentView<>();
        getStyleClass().add("books-top");
        booksTop.sizeProperty().bind(sizeProperty());
        booksTop.setTitle("Books");
        booksTop.setIcon(IkonUtil.book);

        TopContentView<Tip> tipsTop = new TopContentView<>();
        getStyleClass().add("tips-top");
        tipsTop.sizeProperty().bind(sizeProperty());
        tipsTop.setTitle("Tips");
        tipsTop.setIcon(IkonUtil.tip);

        TopContentView<Company> companiesTop = new TopContentView<>();
        getStyleClass().add("companies-top");
        companiesTop.sizeProperty().bind(sizeProperty());
        companiesTop.setTitle("Companies");
        companiesTop.setIcon(IkonUtil.company);

        VBox containerBox = new VBox(toolsTop, librariesTop, blogsTop, videosTop, downloadTop, booksTop, tipsTop, companiesTop);
        containerBox.getStyleClass().add("container-box");
        getChildren().setAll(containerBox);

        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        List<Tool> tools = DataRepository.getInstance().getTools();
                        List<Library> libraries = DataRepository.getInstance().getLibraries();
                        List<Blog> blogs = DataRepository.getInstance().getBlogs();
                        List<Video> videos = DataRepository.getInstance().getVideos();
                        List<Download> downloads = DataRepository.getInstance().getDownloads();
                        List<Book> books = DataRepository.getInstance().getBooks();
                        List<Company> companies = DataRepository.getInstance().getCompanies();
                        List<Tip> tips = DataRepository.getInstance().getTips();
                        //Display the interface, Pause for 0.5 seconds, then fill the data
                        Thread.sleep(500);
                        Platform.runLater(() -> {
                            booksTop.getItems().setAll(books);
                            toolsTop.getItems().setAll(tools);
                            blogsTop.getItems().setAll(blogs);
                            librariesTop.getItems().setAll(libraries);
                            downloadTop.getItems().setAll(downloads);
                            videosTop.getItems().setAll(videos);
                            tipsTop.getItems().setAll(tips);
                            companiesTop.getItems().setAll(companies);
                        });

                        return null;
                    }
                };
            }
        };
        service.start();

    }

}
