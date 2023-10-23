package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.model.Target;
import com.dlsc.jfxcentral2.utils.OSUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.StackPane;

public class PaneBase extends StackPane {

    private static final PseudoClass SMALL_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("sm");
    private static final PseudoClass MEDIUM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("md");
    private static final PseudoClass LARGE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("lg");

    /**
     * sm-md = small or medium
     */
    private static final PseudoClass SMALL_OR_MEDIUM_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("sm-md");
    /**
     * md-lg = medium or large
     */
    private static final PseudoClass MEDIUM_OR_LARGE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("md-lg");

    private static final PseudoClass DESKTOP_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("desktop");
    private static final PseudoClass BROWSER_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("browser");
    private static final PseudoClass MOBILE_PSEUDOCLASS_STATE = PseudoClass.getPseudoClass("mobile");

    private Size oldSize;
    private Size newSize;

    public PaneBase() {
        // target styling
        activateTargetPseudoClass();
        targetProperty().addListener(it -> activateTargetPseudoClass());

        // size styling
        activateSizePseudoClass();
        newSize = getSize();
        sizeProperty().addListener(it -> {
            oldSize = newSize;
            newSize = getSize();
            activateSizePseudoClass();
            layoutBySize();
        });

        if (OSUtil.isAndroidOrIOS()) {
            getStyleClass().add("native");
        }
    }

    public Size getOldSize() {
        return oldSize;
    }

    public Size getNewSize() {
        return newSize;
    }

    public boolean isLgToMdOrMdToLg() {
        if (oldSize == null || newSize == null) {
            return false;
        }
        return (oldSize.isLarge() && newSize.isMedium()) || (oldSize.isMedium() && newSize.isLarge());
    }

    public boolean isSmToMdOrMdToSm() {
        if (oldSize == null || newSize == null) {
            return false;
        }
        return (oldSize.isSmall() && newSize.isMedium()) || (oldSize.isMedium() && newSize.isSmall());
    }

    private void activateTargetPseudoClass() {
        Target target = getTarget();
        pseudoClassStateChanged(DESKTOP_PSEUDOCLASS_STATE, target.isDesktop());
        pseudoClassStateChanged(BROWSER_PSEUDOCLASS_STATE, target.isBrowser());
        pseudoClassStateChanged(MOBILE_PSEUDOCLASS_STATE, target.isMobile());
    }

    private void activateSizePseudoClass() {
        Size size = getSize();
        pseudoClassStateChanged(LARGE_PSEUDOCLASS_STATE, size.isLarge());
        pseudoClassStateChanged(MEDIUM_PSEUDOCLASS_STATE, size.isMedium());
        pseudoClassStateChanged(SMALL_PSEUDOCLASS_STATE, size.isSmall());
        pseudoClassStateChanged(SMALL_OR_MEDIUM_PSEUDOCLASS_STATE, size.isSmall() || size.isMedium());
        pseudoClassStateChanged(MEDIUM_OR_LARGE_PSEUDOCLASS_STATE, size.isMedium() || size.isLarge());
    }

    protected void layoutBySize() {

    }

    private final ObjectProperty<Target> target = new SimpleObjectProperty<>(this, "target", Target.DESKTOP);

    public Target getTarget() {
        return target.get();
    }

    public ObjectProperty<Target> targetProperty() {
        return target;
    }

    public void setTarget(Target target) {
        this.target.set(target);
    }

    private final ObjectProperty<Size> size = new SimpleObjectProperty<>(this, "size", Size.LARGE);

    public Size getSize() {
        return size.get();
    }

    public ObjectProperty<Size> sizeProperty() {
        return size;
    }

    public void setSize(Size size) {
        this.size.set(size);
    }

    public boolean isSmall() {
        return getSize().isSmall();
    }

    public boolean isMedium() {
        return getSize().isMedium();
    }

    public boolean isLarge() {
        return getSize().isLarge();
    }

    public boolean isDesktop() {
        return getTarget().isDesktop();
    }

    public boolean isBrowser() {
        return getTarget().isBrowser();
    }

    public boolean isMobile() {
        return getTarget().isMobile();
    }

}
