package com.dlsc.jfxcentral2.demo.mobile;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.mobile.home.CategoryPreviewView;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.PagePath;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloCategoryPreviewView extends JFXCentralSampleBase {

    private final Random random = new Random();
    private CategoryPreviewView view0;
    private CategoryPreviewView view1;
    private CategoryPreviewView view2;

    @Override
    protected Region createControl() {
        view0 = createShowCasePreviewView();
        view1 = createTipsPreviewView();
        view2 = createPeoplePreviewView();

        VBox vBox = new VBox(35, view0, view1, view2);
        vBox.setMaxHeight(Region.USE_PREF_SIZE);
        vBox.setStyle("-fx-padding: 20px;-fx-border-color: -grey-10;-fx-alignment: center;");
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private CategoryPreviewView createShowCasePreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Real World Apps");
        view.setShowAllUrl(PagePath.SHOWCASES);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<RealWorldApp> apps = DataRepository.getInstance().getRealWorldApps();
        for (int i = 0; i < 2; i++) {
            RealWorldApp app = apps.get(random.nextInt(apps.size()));
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    app.getName(),
                    app.getSummary(),
                    null,
                    ImageManager.getInstance().realWorldAppBannerImageProperty(app),
                    ModelObjectTool.getModelLink(app),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createPeoplePreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("People");
        view.setShowAllUrl(PagePath.PEOPLE);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Person> people = DataRepository.getInstance().getPeople();

        for (int i = 0; i < 2; i++) {
            Person person = people.get(random.nextInt(people.size()));
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    person.getName(),
                    DataRepository.getInstance().getPersonReadMe(person),
                    null,
                    ImageManager.getInstance().personImageProperty(person),
                    ModelObjectTool.getModelLink(person),
                    399,
                    258,
                    3096

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    private CategoryPreviewView createTipsPreviewView() {
        CategoryPreviewView view = new CategoryPreviewView();
        view.setTitle("Tips");
        view.setShowAllUrl(PagePath.TIPS);
        List<CategoryPreviewView.CategoryItem> items = new ArrayList<>();
        List<Tip> tips = DataRepository.getInstance().getTips();
        for (int i = 0; i < 2; i++) {
            Tip tip = tips.get(random.nextInt(tips.size()));
            CategoryPreviewView.CategoryItem item = new CategoryPreviewView.CategoryItem(
                    tip.getName(),
                    tip.getSummary(),
                    null,
                    ImageManager.getInstance().tipBannerImageProperty(tip),
                    ModelObjectTool.getModelLink(tip),
                    123,
                    57,
                    2048

            );
            items.add(item);
        }

        view.getItems().setAll(items);
        return view;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        view0.sizeProperty().bind(sizeComboBox.sizeProperty());
        view1.sizeProperty().bind(sizeComboBox.sizeProperty());
        view2.sizeProperty().bind(sizeComboBox.sizeProperty());

        return sizeComboBox;
    }

    @Override
    public String getSampleName() {
        return "CategoryPreviewView";
    }

}
