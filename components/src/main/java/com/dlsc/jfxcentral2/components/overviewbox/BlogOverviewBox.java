package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Post;
import com.dlsc.jfxcentral2.components.CustomImageView;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.StringUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

public class BlogOverviewBox extends OverviewBox<Blog> {

    public BlogOverviewBox(Blog blog) {
        super(blog);

        getStyleClass().add("blog-overview-box");

        setTitle("Recent posts found on " + blog.getName());
        setIcon(IkonUtil.getModelIkon(Blog.class));
    }

    @Override
    protected Node createTopNode() {
        Label loadingTipsLabel = new Label(StringUtil.LOADING_TIPS, new FontIcon(AntDesignIconsOutlined.CLOUD_DOWNLOAD));
        loadingTipsLabel.getStyleClass().add("loading-label");
        VBox box = new VBox(loadingTipsLabel);
        box.getStyleClass().add("posts-box");
        Service<Void> service = new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws InterruptedException {
                        List<Post> posts = DataRepository.getInstance().loadPosts(getModel());
                        Thread.sleep(500);
                        Platform.runLater(() -> {
                            box.getChildren().clear();
                            posts.forEach(post -> box.getChildren().add(new PostViewBuilder(post, getSize()).build()));
                        });
                        return null;
                    }
                };
            }
        };
        service.start();
        return box;
    }

    public static class PostViewBuilder {
        private final Pane postView;
        private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

        public PostViewBuilder(Post post, Size size) {
            postView = size == Size.SMALL ? new VBox() : new HBox();
            postView.getStyleClass().add("post-view");
            postView.setPrefWidth(0);

            Label titleLabel = new Label(post.getSyndEntry().getTitle());
            titleLabel.getStyleClass().add("title-label");
            titleLabel.setMaxWidth(Double.MAX_VALUE);
            titleLabel.setWrapText(true);
            titleLabel.setMinHeight(Region.USE_PREF_SIZE);

            CustomImageView imageView = new CustomImageView();
            imageView.imageProperty().bind(ImageManager.getInstance().blogIconImageProperty(post.getBlog()));

            Label ageLabel = new Label(getAge(post));
            ageLabel.getStyleClass().add("age-label");
            ageLabel.setMinWidth(Region.USE_PREF_SIZE);

            if (size == Size.SMALL) {
                titleLabel.setGraphic(imageView);
                HBox.setHgrow(titleLabel, Priority.ALWAYS);
                postView.getChildren().setAll(titleLabel, ageLabel);
            } else {
                postView.getChildren().setAll(imageView, titleLabel, ageLabel);
                HBox.setHgrow(titleLabel, Priority.ALWAYS);
            }

            ExternalLinkUtil.setExternalLink(postView, post.getSyndEntry().getLink());
        }

        private Node build() {
            return postView;
        }

        private String getAge(Post post) {
            SyndEntry syndEntry = post.getSyndEntry();
            Date date = syndEntry.getUpdatedDate();
            if (date == null) {
                date = syndEntry.getPublishedDate();
            }

            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            Duration between = Duration.between(zonedDateTime, ZonedDateTime.now());

            long days = between.toDays();
            if (days <= 0) {
                return between.toHours() + " hours";
            } else if (days < 100) {
                return days + (days > 1 ? " days" : " day");
            }

            return dateTimeFormatter.format(zonedDateTime);
        }
    }
}
