package com.dlsc.jfxcentral2.mobile.pages.category;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.mobile.components.ModelListCell;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.images.CentralImageManager;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.kordamp.ikonli.Ikon;

public class MobileBooksCategoryPage extends MobileCategoryPageBase<Book> {

    public MobileBooksCategoryPage(ObjectProperty<Size> size) {
        super(size);
    }

    @Override
    protected Callback<ListView<Book>, ListCell<Book>> cellFactory() {
        return param -> new ModelListCell<>() {
            @Override
            protected void handleImage(Book item, ObjectProperty<Image> imageProperty) {
                imageProperty.set(CentralImageManager.getBookCoverImage1(item));
            }
        };
    }

    @Override
    protected String getCategoryTitle() {
        return "Books";
    }

    @Override
    protected Ikon getCategoryIkon() {
        return IkonUtil.getModelIkon(Book.class);
    }

    @Override
    protected String getSearchPromptText() {
        return "Search for a book";
    }

    @Override
    protected ObservableList<Book> getCategoryItems() {
        return FXCollections.observableArrayList(DataRepository.getInstance().getBooks());
    }
}
