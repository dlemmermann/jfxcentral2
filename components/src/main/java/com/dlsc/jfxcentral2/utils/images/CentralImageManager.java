package com.dlsc.jfxcentral2.utils.images;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.utils.OSUtil;
import javafx.scene.image.Image;
import one.jpro.platform.image.manager.ImageDefinition;
import one.jpro.platform.image.manager.ImageManager;
import one.jpro.platform.image.manager.encoder.ImageEncoder;
import one.jpro.platform.image.manager.encoder.ImageEncoderJPG;
import one.jpro.platform.image.manager.source.ImageSource;
import one.jpro.platform.image.manager.source.ImageSourceFile;
import one.jpro.platform.image.manager.transformer.ImageTransformer;
import one.jpro.platform.image.manager.transformer.ImageTransformerFitHeight;
import one.jpro.platform.image.manager.transformer.ImageTransformerScaleToArea;
import one.jpro.platform.image.manager.transformer.ImageTransformerWH;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CentralImageManager {

    private static final URL MISSING_IMAGE = com.dlsc.jfxcentral.data.ImageManager.class.getResource("missing-image.jpg");

    private static final ImageManager manager = ImageManager.getInstance();

    static int HEIGHT_PREVIEW_LARGE = 147;
    static int HEIGHT_PREVIEW_SMALL = 71;

    public static Image loadImage(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            return new Image(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getPreviewImage(File file, boolean large) {
        if (!OSUtil.isAWTSupported()) {
            return loadImage(file);
        }

        ImageEncoder encoding = new ImageEncoderJPG(0.85);
        int height = large ? HEIGHT_PREVIEW_LARGE : HEIGHT_PREVIEW_SMALL;
        ImageTransformer transformer = new ImageTransformerFitHeight(height, 2);
        ImageSource source = new ImageSourceFile(file);
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static Image getRealWorldAppBannerImage2(RealWorldApp app) {
        if (!OSUtil.isAWTSupported()) {
            return loadImage(realWorldAppBannerImageFile(app));
        }

        ImageEncoder encoding = new ImageEncoderJPG(0.85);
        ImageTransformer transformer = new ImageTransformerWH(413, 233, 2);
        ImageSource source = new ImageSourceFile(realWorldAppBannerImageFile(app));
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static File realWorldAppBannerImageFile(RealWorldApp app) {
        return com.dlsc.jfxcentral.data.ImageManager.getInstance().realWorldAppBannerFile(app);
    }

    public static Image getDownloadImage(Download download) {
        if (!OSUtil.isAWTSupported()) {
            return loadImage(com.dlsc.jfxcentral.data.ImageManager.getInstance().downloadBannerFile(download));
        }

        ImageEncoder encoding = new ImageEncoderJPG(0.85);
        ImageTransformer transformer = new ImageTransformerWH(413, 233, 2);
        File file = com.dlsc.jfxcentral.data.ImageManager.getInstance().downloadBannerFile(download);
        ImageSource source = new ImageSourceFile(file);
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static Image getBookCoverImage1(Book book) {
        if (!OSUtil.isAWTSupported()) {
            return loadImage(bookCoverImageFile(book));
        }

        ImageEncoder encoding = new ImageEncoderJPG(0.8);
        ImageTransformer transformer = new ImageTransformerScaleToArea(180, 250);
        ImageSource source = new ImageSourceFile(bookCoverImageFile(book));
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static Image getBookCoverImage2(Book book) {
        if (!OSUtil.isAWTSupported()) {
            return loadImage(bookCoverImageFile(book));
        }

        ImageEncoder encoding = new ImageEncoderJPG(0.8);
        ImageTransformer transformer = new ImageTransformerScaleToArea(500, 500);
        ImageSource source = new ImageSourceFile(bookCoverImageFile(book));
        return manager.loadImage(new ImageDefinition(source, transformer, encoding)).toFXImage();
    }

    public static File bookCoverImageFile(Book book) {
        File f = new File(DataRepository.getRepositoryDirectory(), "books/" + book.getId() + "/" + "cover.jpg");
        if (f.exists()) {
            return f;
        } else {
            return new File(Objects.requireNonNull(MISSING_IMAGE).getFile());
        }
    }
}
