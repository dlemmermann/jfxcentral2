package com.dlsc.jfxcentral2.app.pages;


import com.dlsc.jfxcentral2.components.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import one.jpro.routing.View;

public abstract class DefaultPage extends View {

    private final ObjectProperty<Size> size;

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public Size getSize() {
        return sizeProperty().get();
    }

    public DefaultPage(ObjectProperty<Size> size) {
        this.size = size;
    }

    public Node wrapContent(Node content) {
        var vbox = new VBox();

        // menubar
        TopMenuBar topMenuBar = new TopMenuBar();
        topMenuBar.sizeProperty().bind(sizeProperty());

        // footer
        FooterView footerView = new FooterView();
        footerView.sizeProperty().bind(sizeProperty());


        // sponsors
        SponsorsView sponsorsView = new SponsorsView();
        sponsorsView.sizeProperty().bind(sizeProperty());
        sponsorsView.showLogoCountProperty().bind(Bindings.createIntegerBinding(() -> switch (getSize()) {
            case SMALL -> 2;
            case MEDIUM -> 3;
            case LARGE -> 5;
        }, sizeProperty()));

        var topStackPane = new StackPane(content, topMenuBar);
        StackPane.setAlignment(topMenuBar, Pos.TOP_CENTER);

        vbox.getStyleClass().add("ui");
        StackPane.setAlignment(vbox, Pos.TOP_CENTER);

        vbox.getChildren().addAll(topStackPane, sponsorsView, footerView);

        StackPane root = new StackPane(vbox);
        root.getStyleClass().add("background");

        return root;
    }
}
