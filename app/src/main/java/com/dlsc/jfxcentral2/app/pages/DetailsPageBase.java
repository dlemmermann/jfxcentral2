package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.components.DetailsContentPane;
import com.dlsc.jfxcentral2.components.Mode;
import com.dlsc.jfxcentral2.components.detailsbox.AppsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.BlogsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.BooksDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.CompaniesDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.DetailsBoxBase;
import com.dlsc.jfxcentral2.components.detailsbox.DownloadsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.LearnDetailBox;
import com.dlsc.jfxcentral2.components.detailsbox.LibrariesDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.PersonsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.TipsAndTricksDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.ToolsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.TutorialsDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.VideosDetailsBox;
import com.dlsc.jfxcentral2.components.detailsbox.DocumentationDetailsBox;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.OSUtil;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.eclipse.jgit.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class DetailsPageBase<T extends ModelObject> extends PageBase {

    private T item;

    public DetailsPageBase(ObjectProperty<Size> size, Class<? extends T> clazz, String itemId) {
        super(size, Mode.DARK);
        setItem(DataRepository.getInstance().getByID(clazz, itemId));
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public String title() {
        return "JFXCentral - " + getItem().getName();
    }

    @Override
    public String description() {
        String text = getItem().getDescription();

        if (!StringUtils.isEmptyOrNull(text)) {
            return text;
        }

        return "";
    }

    @Override
    public Node content() {
        return wrapContent(new Label(getItem().getName()));
    }

    protected DetailsContentPane createContentPane() {
        DetailsContentPane detailsContentPane = new DetailsContentPane();
        detailsContentPane.sizeProperty().bind(sizeProperty());
        return detailsContentPane;
    }

    protected List<DetailsBoxBase<?>> createDetailBoxes() {
        ModelObject modelObject = getItem();
        List<DetailsBoxBase<?>> boxes = new ArrayList<>();

        maybeAddBox(modelObject, Library.class, LibrariesDetailsBox::new, boxes);
        maybeAddBox(modelObject, Tool.class, ToolsDetailsBox::new, boxes);
        maybeAddBox(modelObject, Book.class, BooksDetailsBox::new, boxes);
        maybeAddBox(modelObject, Video.class, VideosDetailsBox::new, boxes);
        maybeAddBox(modelObject, Blog.class, BlogsDetailsBox::new, boxes);
        maybeAddBox(modelObject, Tutorial.class, TutorialsDetailsBox::new, boxes);
        maybeAddBox(modelObject, Learn.class, LearnDetailBox::new, boxes);

        if (!isMobile() && !OSUtil.isAndroidOrIOS()) {
            maybeAddBox(modelObject, Download.class, DownloadsDetailsBox::new, boxes);
        }

        maybeAddBox(modelObject, Tip.class, TipsAndTricksDetailsBox::new, boxes);
        maybeAddBox(modelObject, RealWorldApp.class, AppsDetailsBox::new, boxes);
        maybeAddBox(modelObject, Person.class, PersonsDetailsBox::new, boxes);
        maybeAddBox(modelObject, Company.class, CompaniesDetailsBox::new, boxes);
        maybeAddBox(modelObject, Documentation.class, DocumentationDetailsBox::new, boxes);

        return boxes;
    }

    private <MO extends ModelObject> void maybeAddBox(ModelObject modelObject, Class<MO> clazz, Supplier<DetailsBoxBase<MO>> boxSupplier, List<DetailsBoxBase<?>> boxList) {
        if (clazz.equals(Learn.class)) {
            addLearnBox(modelObject, boxList);
            return;
        }

        List<MO> linkedObjects = DataRepository.getInstance().getLinkedObjects(modelObject, clazz);
        if (!linkedObjects.isEmpty()) {
            DetailsBoxBase<MO> box = boxSupplier.get();
            box.getItems().setAll(linkedObjects);
            box.sizeProperty().bind(sizeProperty());
            boxList.add(box);
        }
    }

    private void addLearnBox(ModelObject modelObject, List<DetailsBoxBase<?>> boxList) {
        List<LearnJavaFX> fxList = DataRepository.getInstance().getLinkedObjects(modelObject, LearnJavaFX.class);
        List<LearnMobile> mobileList = DataRepository.getInstance().getLinkedObjects(modelObject, LearnMobile.class);
        List<LearnRaspberryPi> learnRaspberryPiList = DataRepository.getInstance().getLinkedObjects(modelObject, LearnRaspberryPi.class);
        if (!fxList.isEmpty() || !mobileList.isEmpty() || !learnRaspberryPiList.isEmpty()) {
            LearnDetailBox box = new LearnDetailBox();
            box.getItems().setAll(fxList);
            box.getItems().addAll(mobileList);
            box.getItems().addAll(learnRaspberryPiList);
            box.sizeProperty().bind(sizeProperty());
            boxList.add(box);
        }
    }
}
