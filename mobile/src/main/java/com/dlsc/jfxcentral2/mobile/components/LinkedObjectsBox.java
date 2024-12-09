package com.dlsc.jfxcentral2.mobile.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.SizeSupport;
import com.dlsc.jfxcentral2.mobile.home.CategoryPreviewView;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class LinkedObjectsBox<T extends ModelObject> extends VBox {

    private final SizeSupport sizeSupport = new SizeSupport(this);

    public LinkedObjectsBox() {
        getStyleClass().add("linked-objects-box");

        updateView();
        dataProperty().addListener((obs, oldDate, newDate) -> updateView());
    }

    public LinkedObjectsBox(T data) {
        this();
        setData(data);
    }

    private void updateView() {
        getChildren().clear();

        T data = getData();
        if (data == null) {
            return;
        }

        // apps
        List<RealWorldApp> linkedApps = getLinkedObjects(data, RealWorldApp.class);
        if (!linkedApps.isEmpty()) {
            CategoryPreviewView showCasePreviewView = CategoryPreviewView.createShowCasePreviewView(linkedApps);
            showCasePreviewView.sizeProperty().bind(sizeProperty());
            showCasePreviewView.setTitle("Apps");
            getChildren().add(showCasePreviewView);
        }

        // libraries
        List<Library> linkedLibraries = getLinkedObjects(data, Library.class);
        if (!linkedLibraries.isEmpty()) {
            CategoryPreviewView libraryPreviewView = CategoryPreviewView.createLibraryPreviewView(linkedLibraries);
            libraryPreviewView.sizeProperty().bind(sizeProperty());
            libraryPreviewView.setTitle("Libraries");
            getChildren().add(libraryPreviewView);
        }

        // books
        List<Book> linkedBooks = getLinkedObjects(data, Book.class);
        if (!linkedBooks.isEmpty()) {
            CategoryPreviewView booksPreviewView = CategoryPreviewView.createBooksPreviewView(linkedBooks);
            booksPreviewView.sizeProperty().bind(sizeProperty());
            booksPreviewView.setTitle("Books");
            getChildren().add(booksPreviewView);
        }

        // videos
        List<Video> linkedVideos = getLinkedObjects(data, Video.class);
        if (!linkedVideos.isEmpty()) {
            CategoryPreviewView videoPreviewView = CategoryPreviewView.createVideosPreviewView(linkedVideos);
            videoPreviewView.sizeProperty().bind(sizeProperty());
            videoPreviewView.setTitle("Videos");
            getChildren().add(videoPreviewView);
        }

        // tips
        List<Tip> linkedTips = getLinkedObjects(data, Tip.class);
        if (!linkedTips.isEmpty()) {
            CategoryPreviewView tipsPreviewView = CategoryPreviewView.createTipsPreviewView(linkedTips);
            tipsPreviewView.sizeProperty().bind(sizeProperty());
            tipsPreviewView.setTitle("Tips");
            getChildren().add(tipsPreviewView);
        }

        // blogs
        List<Blog> linkedBlogs = getLinkedObjects(data, Blog.class);
        if (!linkedBlogs.isEmpty()) {
            CategoryPreviewView blogPreviewView = CategoryPreviewView.createBlogPreviewView(linkedBlogs);
            blogPreviewView.sizeProperty().bind(sizeProperty());
            blogPreviewView.setTitle("Blogs");
            getChildren().add(blogPreviewView);
        }

        // learn
        List<Learn> linkedLearn = new ArrayList<>();
        linkedLearn.addAll(getLinkedObjects(data, LearnJavaFX.class));
        linkedLearn.addAll(getLinkedObjects(data, LearnMobile.class));
        linkedLearn.addAll(getLinkedObjects(data, LearnRaspberryPi.class));

        if (!linkedLearn.isEmpty()) {
            CategoryPreviewView learnPreviewView = CategoryPreviewView.createLearnPreviewView(linkedLearn);
            learnPreviewView.sizeProperty().bind(sizeProperty());
            learnPreviewView.setTitle("Learn");
            getChildren().add(learnPreviewView);
        }

        // person
        List<Person> linkedPersons = getLinkedObjects(data, Person.class);
        if (!linkedPersons.isEmpty()) {
            CategoryPreviewView personPreviewView = CategoryPreviewView.createPeoplePreviewView(linkedPersons);
            personPreviewView.sizeProperty().bind(sizeProperty());
            personPreviewView.setTitle("People");
            getChildren().add(personPreviewView);
        }

        // companies
        List<Company> linkedCompanies = getLinkedObjects(data, Company.class);
        if (!linkedCompanies.isEmpty()) {
            CategoryPreviewView companyPreviewView = CategoryPreviewView.createCompanyPreviewView(linkedCompanies);
            companyPreviewView.sizeProperty().bind(sizeProperty());
            companyPreviewView.setTitle("Companies");
            getChildren().add(companyPreviewView);
        }
    }

    private <M extends ModelObject> List<M> getLinkedObjects(T t, Class<M> type) {
        return DataRepository.getInstance().getLinkedObjects(t, type);
    }

    // size

    public final Size getSize() {
        return sizeSupport.getSize();
    }

    public final void setSize(Size size) {
        sizeSupport.setSize(size);
    }

    public final ObjectProperty<Size> sizeProperty() {
        return sizeSupport.sizeProperty();
    }

    // data

    private final ObjectProperty<T> data = new SimpleObjectProperty<>(this, "data");

    public final ObjectProperty<T> dataProperty() {
        return data;
    }

    public final T getData() {
        return data.get();
    }

    public final void setData(T data) {
        this.data.set(data);
    }

}
