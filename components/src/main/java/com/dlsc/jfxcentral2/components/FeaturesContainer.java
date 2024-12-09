package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.model.Feature;
import com.dlsc.jfxcentral2.model.Feature.Type;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.dlsc.jfxcentral2.utils.PageUtil;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.css.converter.EnumConverter;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FeaturesContainer extends PaneBase {

    private static final PseudoClass VERTICAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("vertical");

    private static final PseudoClass HORIZONTAL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("horizontal");

    private static final Orientation DEFAULT_ORIENTATION = Orientation.HORIZONTAL;

    public FeaturesContainer() {
        this(DEFAULT_ORIENTATION);
    }

    public FeaturesContainer(Orientation orientation) {
        getStyleClass().add("features-container");
        setOrientation(orientation);
        activateOrientationPseudoClass();
        orientationProperty().addListener((ob, ov, nv) -> {
            activateOrientationPseudoClass();
            layoutBySize();
        });
        featuresProperty().addListener((ob, ov, nv) -> layoutBySize());

        List<ModelObject> allModelObjects = new ArrayList<>();
        allModelObjects.addAll(DataRepository.getInstance().getTips());
        allModelObjects.addAll(DataRepository.getInstance().getLibraries());
        allModelObjects.addAll(DataRepository.getInstance().getRealWorldApps());
        allModelObjects.addAll(DataRepository.getInstance().getTools());
        allModelObjects.addAll(DataRepository.getInstance().getBooks());
        allModelObjects.addAll(DataRepository.getInstance().getBlogs());
        Collections.shuffle(allModelObjects);

        /*
         * For now we do not feature videos, tutorials, people, or blogs.
         */
//        allModelObjects.addAll(DataRepository.getInstance().getVideos());
//        allModelObjects.addAll(DataRepository.getInstance().getTutorials());
//        allModelObjects.addAll(DataRepository.getInstance().getPeople());
//        allModelObjects.addAll(DataRepository.getInstance().getBlogs());


        allModelObjects
                .subList(0, Math.min(3, allModelObjects.size()))
                .forEach(mo -> getFeatures().add(new Feature(mo.getName(), mo.getSummary(), "Featured", getRemark(mo), IkonUtil.getModelIkon(mo), getType(mo), getImageProperty(mo), PageUtil.getLink(mo))));
    }

    private String getRemark(ModelObject mo) {
        Objects.requireNonNull(mo, "model object can not be null");

        if (mo instanceof Video video) {
            if (video.getMinutes() > 0) {
                return video.getMinutes() + " min";
            }
            return "Videos";
        } else if (mo instanceof RealWorldApp) {
            return "Showcases";
        } else if (mo instanceof Library) {
            return "Libraries";
        } else if (mo instanceof Tool) {
            return "Tools";
        } else if (mo instanceof Book) {
            return "Books";
        } else if (mo instanceof Tip) {
            return "Tips & Tricks";
        } else if (mo instanceof Blog){
            return "Blogs";
        }

        return null;
    }

    private ObjectProperty<Image> getImageProperty(ModelObject mo) {
        Objects.requireNonNull(mo, "model object can not be null");
        // HERE

        return ModelObjectTool.getModelPreviewImageProperty(mo, true);
    }

    private Type getType(ModelObject mo) {
        if (mo instanceof Video) {
            return Type.VIDEO;
        } else if (mo instanceof Tip) {
            return Type.TIP;
        } else if (mo instanceof Tutorial) {
            return Type.TUTORIAL;
        } else if (mo instanceof Library) {
            return Type.LIBRARY;
        } else if (mo instanceof RealWorldApp) {
            return Type.SHOWCASE;
        } else if (mo instanceof Person) {
            return Type.PERSON;
        } else if (mo instanceof Blog) {
            return Type.BLOG;
        } else if (mo instanceof Tool) {
            return Type.TOOL;
        } else if (mo instanceof Book) {
            return Type.BOOK;
        } else {
            String errorMessage = "model object of type " + mo.getClass().getSimpleName() + " is not supported";
            System.err.println(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Override
    protected void layoutBySize() {
        getChildren().clear();
        if (getFeatures() == null || getFeatures().isEmpty()) {
            return;
        }
        int size = getFeatures().size();
        boolean isVer = getOrientation() == Orientation.VERTICAL;
        if (isVer || !isSmall()) {
            Pane contentBox = isVer ? new VBox() : new HBox();
            for (int i = 0; i < size; i++) {
                Feature item = getFeatures().get(i);
                FeatureView featureView = new FeatureView(item);
                featureView.getStyleClass().addAll("feature-view-" + i, i % 2 == 0 ? "even" : "odd");
                featureView.sizeProperty().bind(sizeProperty());
                contentBox.getChildren().add(featureView);
            }
            getChildren().setAll(contentBox);
        } else {
            PaginationControl pagination = new PaginationControl();
            pagination.setPageCount(size);
            pagination.setPageFactory(index -> {
                FeatureView featureView = new FeatureView(getFeatures().get(index));
                featureView.sizeProperty().bind(sizeProperty());
                return featureView;
            });
            getChildren().setAll(pagination);
        }
    }

    private void activateOrientationPseudoClass() {
        Orientation tempOrientation = getOrientation();
        pseudoClassStateChanged(HORIZONTAL_PSEUDOCLASS_STATE, tempOrientation == Orientation.HORIZONTAL);
        pseudoClassStateChanged(VERTICAL_PSEUDOCLASS_STATE, tempOrientation == Orientation.VERTICAL);
    }

    private final ListProperty<Feature> features = new SimpleListProperty<>(this, "features", FXCollections.observableArrayList());

    public ObservableList<Feature> getFeatures() {
        return features.get();
    }

    public ListProperty<Feature> featuresProperty() {
        return features;
    }

    public void setFeatures(ObservableList<Feature> features) {
        this.features.set(features);
    }

    private final ObjectProperty<Orientation> orientation = new StyleableObjectProperty<>(DEFAULT_ORIENTATION) {

        @Override
        public Object getBean() {
            return FeaturesContainer.this;
        }

        @Override
        public String getName() {
            return "orientation";
        }

        @Override
        public CssMetaData<FeaturesContainer, Orientation> getCssMetaData() {
            return FeaturesContainer.StyleableProperties.ORIENTATION;
        }
    };

    public final void setOrientation(Orientation value) {
        orientation.set(value);
    }

    public final Orientation getOrientation() {
        return orientation.get();
    }

    public final ObjectProperty<Orientation> orientationProperty() {
        return orientation;
    }

    private static class StyleableProperties {

        private static final CssMetaData<FeaturesContainer, Orientation> ORIENTATION = new CssMetaData<>("-fx-orientation", new EnumConverter<>(Orientation.class), DEFAULT_ORIENTATION) {

            @Override
            public boolean isSettable(FeaturesContainer n) {
                return !n.orientation.isBound();
            }

            @Override
            public StyleableProperty<Orientation> getStyleableProperty(FeaturesContainer n) {
                return (StyleableProperty<Orientation>) n.orientationProperty();
            }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(PaneBase.getClassCssMetaData());
            styleables.add(ORIENTATION);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return FeaturesContainer.StyleableProperties.STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
}
