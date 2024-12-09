package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral2.mobile.components.ModelListCell;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileBlogsCategoryPage extends MobileCategoryPageBase<Blog> {

    public MobileBlogsCategoryPage(ObjectProperty<Size> size) {
        super(size);
        getStyleClass().add("blogs-category-page");
    }

    @Override
    protected Callback<ListView<Blog>, ListCell<Blog>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Blog item, ObjectProperty<Image> imageProperty) {
                imageProperty.bind(ImageManager.getInstance().blogIconImageProperty(item));
            }

            @Override
            protected String getSummary(Blog item) {
                return item.getSummary();
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Blogs";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Blog.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a blog";
    }

    @Override
    protected ObservableList<Blog> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getBlogs());
    }
}
