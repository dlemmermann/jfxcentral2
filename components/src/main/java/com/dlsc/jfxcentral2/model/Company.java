package com.dlsc.jfxcentral2.model;

import javafx.scene.image.Image;

public class Company extends TileData {
    public Company() {
    }

    public Company(String title, String description, Image thumbnail, String url, boolean save, boolean like) {
        super(title, description, thumbnail, url, save, like);
    }
}
