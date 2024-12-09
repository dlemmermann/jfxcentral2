package com.dlsc.jfxcentral2.mobile.pages.details;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral2.components.MobilePageBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.List;

public abstract class MobileDetailsPageBase<T extends ModelObject> extends MobilePageBase {

    private T item;
    private final Class<? extends T> clazz;

    public MobileDetailsPageBase(ObjectProperty<Size> size, Class<? extends T> clazz, String itemId) {
        this.clazz = clazz;

        sizeProperty().bind(size);
        setItem(DataRepository.getInstance().getByID(clazz, itemId));
        getChildren().addAll(content());
        getStyleClass().add("mobile-details-page");
    }

    public Class<? extends T> getModelClazz() {
        return clazz;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public abstract List<Node> content();

}