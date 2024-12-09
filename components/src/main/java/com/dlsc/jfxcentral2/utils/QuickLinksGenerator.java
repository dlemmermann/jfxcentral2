package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.NormalQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.model.SenaptQuickLink;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuickLinksGenerator {

    private QuickLinksGenerator() {
    }

    public static List<QuickLink> generateQuickLinks(Size size) {
        if (size != Size.SMALL) {
            return getLargeOrMediumQuickLinks();
        } else {
            return getSmallQuickLinks();
        }
    }

    private static List<QuickLink> createQuickLinks(int count) {
        List<QuickLink> list = new ArrayList<>();
        List<ModelObject> dataList = createShuffledSublist(count);
        for (int i = 0; i < dataList.size(); i++) {
            ModelObject mo = dataList.get(i);
            list.add(new NormalQuickLink(getCategoryName(mo), mo.getName(), IkonUtil.getModelIkon(mo), PageUtil.getLink(mo)));
        }
        return list;
    }

    private static List<QuickLink> getSmallQuickLinks() {
        List<QuickLink> list = createQuickLinks(3);
        list.add(new SenaptQuickLink());
        Collections.shuffle(list);
        return list;
    }

    private static List<QuickLink> getLargeOrMediumQuickLinks() {
        // Randomly generate 4 to 5 QuickLinks
        int count = new Random().nextInt(2) + 4;
        List<QuickLink> list = createQuickLinks(count);

        //add main sponsor
        list.add(new SenaptQuickLink());

        //Randomly generate 1~3 QuickLinks for image placeholders
        int count2 = new Random().nextInt(3) + 1;
        List<String> imageName = new ArrayList<>();

        //currently there are only 3 images for testing
        for (int i = 0; i < 3; i++) {
            imageName.add("quick-link-image" + (i + 1) + ".jpg");
        }

        Collections.shuffle(imageName);
        imageName.subList(0, count2).forEach(url -> list.add(new ImageQuickLink(Objects.requireNonNull(QuickLinksGenerator.class.getResource(url)).toExternalForm())));

        //If the above QuickLinks are less than 9 (must add main sponsor), fill null
        for (int i = 0; i < 9 - count - count2- 1 ; i++) {
            list.add(null);
        }
        // shuffle the links
        do {
            Collections.shuffle(list);
        } while (!checkArrayList(list));
        return list;
    }

    private static String getCategoryName(ModelObject mo) {
        Objects.requireNonNull(mo, "model object can not be null");

        if (mo instanceof Video video) {
            if (video.getMinutes() > 0) {
                return video.getMinutes() + " min";
            }
            return "Video";
        } else if (mo instanceof RealWorldApp) {
            return "Showcase";
        } else if (mo instanceof Library) {
            return "Library";
        } else if (mo instanceof Tool) {
            return "Tool";
        } else if (mo instanceof Book) {
            return "Book";
        } else if (mo instanceof Tip) {
            return "Tip";
        } else if (mo instanceof Tutorial) {
            return "Tutorial";
        } else if (mo instanceof Person) {
            return "Person";
        } else if (mo instanceof Download) {
            return "Download";
        }

        return null;
    }

    public static boolean checkArrayList(List<QuickLink> list) {
        int rows = 3;
        int cols = 3;
        // Cannot have 3 null values or 3 QuickLinks without images in the same row
        for (int i = 0; i < rows; i++) {
            int normalView = 0;
            int imageView = 0;
            int nullCount = 0;
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (list.get(index) == null) {
                    nullCount++;
                } else if (list.get(index) instanceof NormalQuickLink) {
                    normalView++;
                } else {
                    imageView++;
                }
            }
            if (normalView == cols || nullCount == cols || imageView == cols) {
                return false;
            }
        }

        // Cannot have 3 null values or 3 QuickLinks without images in the same row
        for (int j = 0; j < cols; j++) {
            int nullCount = 0;
            int imageView = 0;
            int normalView = 0;
            for (int i = 0; i < rows; i++) {
                int index = i * cols + j;
                if (list.get(index) == null) {
                    nullCount++;
                } else if (list.get(index) instanceof NormalQuickLink) {
                    normalView++;
                } else {
                    imageView++;
                }
            }
            if (nullCount == rows || normalView == rows || imageView == rows) {
                return false;
            }
        }
        return true;
    }

    private static List<ModelObject> createShuffledSublist(int size) {
        DataRepository repository = DataRepository.getInstance();

        List<ModelObject> allModelObjects = new ArrayList<>();
        allModelObjects.addAll(repository.getTips());
        allModelObjects.addAll(repository.getLibraries());
        allModelObjects.addAll(repository.getRealWorldApps());
        allModelObjects.addAll(repository.getTools());
        allModelObjects.addAll(repository.getBooks());

        Collections.shuffle(allModelObjects);

        return allModelObjects.subList(0, Math.min(allModelObjects.size(), size));
    }

    public static List<QuickLink> generateWebsiteChangesQuickLinks(ObjectProperty<Size> sizeProperty) {
        // new list, we do not want to shuffle the original list
        List<ModelObject> recentItems = findRecentItems();
        if (recentItems.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.shuffle(recentItems);

        List<QuickLink> quickLinks = new ArrayList<>();
        if (sizeProperty.get() != Size.SMALL) {
            Random random = new Random();
            int imageQuickLinkCount = random.nextInt(2) + 1;
            int dateQuickLinkCount = Math.min(5, recentItems.size());
            int nullCount = 7 - imageQuickLinkCount - dateQuickLinkCount;

            // normal QuickLinks
            for (int i = 0; i < dateQuickLinkCount; i++) {
                ModelObject changedModelObject = recentItems.get(i);
                quickLinks.add(createDateQuickLink(changedModelObject));
            }

            // image QuickLinks
            for (int i = 0; i < imageQuickLinkCount; i++) {
                String imgUrl = "quick-link-image" + (i + 1) + ".jpg";
                quickLinks.add(new ImageQuickLink(Objects.requireNonNull(QuickLinksGenerator.class.getResource(imgUrl)).toExternalForm()));
            }

            // empty QuickLinks
            for (int i = 0; i < nullCount; i++) {
                quickLinks.add(null);
            }
            Collections.shuffle(quickLinks);
        } else { // small size

            // normal QuickLinks
            for (ModelObject changedModelObject : recentItems) {
                quickLinks.add(createDateQuickLink(changedModelObject));
            }
        }

        return quickLinks;
    }

    private static List<ModelObject> findRecentItems() {
        DataRepository repository = DataRepository.getInstance();

        List<ModelObject> result = new ArrayList<>();

        result.addAll(findRecentItems(repository.getPeople()));
        result.addAll(findRecentItems(repository.getBooks()));
        result.addAll(findRecentItems(repository.getLibraries()));
        result.addAll(findRecentItems(repository.getVideos()));
        result.addAll(findRecentItems(repository.getBlogs()));
        result.addAll(findRecentItems(repository.getCompanies()));
        result.addAll(findRecentItems(repository.getTools()));
        result.addAll(findRecentItems(repository.getTutorials()));
        result.addAll(findRecentItems(repository.getRealWorldApps()));
        result.addAll(findRecentItems(repository.getDownloads()));
        result.addAll(findRecentItems(repository.getTips()));
        result.addAll(findRecentItems(repository.getIkonliPacks()));

        // newest ones on top
        result.sort(Comparator.comparing(ModelObject::getCreationOrUpdateDate).reversed());

        return result;
    }

    private static List<ModelObject> findRecentItems(List<? extends ModelObject> items) {
        List<ModelObject> result = new ArrayList<>();

        LocalDate today = LocalDate.now();

        items.forEach(item -> {
            LocalDate date = item.getModifiedOn();
            if (date == null) {
                date = item.getCreatedOn();
            }
            if (date != null) {
                if (date.isAfter(today.minusWeeks(8))) {
                    result.add(item);
                }
            }
        });

        return result;
    }

    private static DateQuickLink createDateQuickLink(ModelObject changedModelObject) {
        return new DateQuickLink(changedModelObject.getName(), getCategoryName(changedModelObject), null, PageUtil.getLink(changedModelObject), changedModelObject.getCreationOrUpdateDate());
    }
}
