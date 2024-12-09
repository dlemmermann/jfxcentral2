package com.dlsc.jfxcentral2.iconfont;

import org.kordamp.ikonli.Ikon;

public enum JFXCentralIcon implements Ikon {

    APP("jfx-icon-app", '\ue900'),
    ARROW_LEFT_RIGHT("jfx-icon-arrow-left-right", '\ue901'),
    BLOGS("jfx-icon-blogs", '\ue902'),
    BOOKS("jfx-icon-books", '\ue903'),
    BULLET_POINT("jfx-icon-bullet-point", '\ue904'),
    CALENDAR("jfx-icon-calendar", '\ue905'),
    CAMERA("jfx-icon-camera", '\ue906'),
    CHAMPION("jfx-icon-champion", '\ue907'),
    CHEVRON_BOTTOM("jfx-icon-chevron-bottom", '\ue908'),
    CHEVRON_MENU_BOTTOM("jfx-icon-chevron-menu-bottom", '\ue909'),
    CHEVRON_MENU_TOP("jfx-icon-chevron-menu-top", '\ue90a'),
    CHEVRON_TOP("jfx-icon-chevron-top", '\ue90b'),
    COMMENTS("jfx-icon-comments", '\ue90c'),
    COMPANIES("jfx-icon-companies", '\ue90d'),
    COORDINATES("jfx-icon-coordinates", '\ue90e'),
    COSS("jfx-icon-coss", '\ue90f'),
    DELETE("jfx-icon-delete", '\ue910'),
    DOWNLOAD("jfx-icon-download", '\ue911'),
    EDIT("jfx-icon-edit", '\ue912'),
    EMAIL("jfx-icon-email", '\ue913'),
    EYE_CLOSED("jfx-icon-eye-closed", '\ue914'),
    EYE_OPEN("jfx-icon-eye-open", '\ue915'),
    FACEBOOK("jfx-icon-facebook", '\ue916'),
    FLOPPY("jfx-icon-floppy", '\ue917'),
    GITHUB("jfx-icon-github", '\ue918'),
    HEART("jfx-icon-heart", '\ue919'),
    IMAGE("jfx-icon-image", '\ue91a'),
    INSTALL_LOCALLY("jfx-icon-install-locally", '\ue91b'),
    LIBRARIES("jfx-icon-libraries", '\ue91c'),
    LINKEDIN("jfx-icon-linkedin", '\ue91d'),
    LINKS_OF_THE_WEEK("jfx-icon-links-of-the-week", '\ue91e'),
    LINKS("jfx-icon-links", '\ue91f'),
    LOG_IN("jfx-icon-log-in", '\ue920'),
    LOG_OUT("jfx-icon-log-out", '\ue921'),
    MENU("jfx-icon-menu", '\ue922'),
    OPEN_LINK("jfx-icon-open-link", '\ue923'),
    OVERVIEW("jfx-icon-overview", '\ue924'),
    PEOPLE("jfx-icon-people", '\ue925'),
    PLAY("jfx-icon-play", '\ue926'),
    ROCKSTAR("jfx-icon-rockstar", '\ue927'),
    SEARCH("jfx-icon-search", '\ue928'),
    SHARE("jfx-icon-share", '\ue929'),
    SHOWCASES("jfx-icon-showcases", '\ue92a'),
    THUMB_UP_FILLED("jfx-icon-thumb-up-filled", '\ue92b'),
    THUMB_UP("jfx-icon-thumb-up", '\ue92c'),
    TIMER("jfx-icon-timer", '\ue92d'),
    TIPS_TRICKS("jfx-icon-tips-tricks", '\ue92e'),
    TOOLS("jfx-icon-tools", '\ue92f'),
    TUTORIALS("jfx-icon-tutorials", '\ue930'),
    TWITTER("jfx-icon-twitter", '\ue931'),
    VIDEOS("jfx-icon-videos", '\ue932'),
    WWW("jfx-icon-www", '\ue933'),
    YOUTUBE("jfx-icon-youtube", '\ue934'),
    TOP_CONTENT("jfx-icon-top-content", '\ue935'),
    HANDSHAKE("jfx-icon-handshake", '\ue936'),
    TEAM("jfx-icon-team", '\ue937'),
    BLUESKY("jfx-icon-bluesky", '\ue938');

    private final String description;
    private final int icon;

    JFXCentralIcon(String description, int icon) {
        this.description = description;
        this.icon = icon;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return icon;
    }

    public static JFXCentralIcon findByDescription(String description) {
        for (JFXCentralIcon icon : values()) {
            if (icon.description.equals(description)) {
                return icon;
            }
        }
        throw new IllegalArgumentException("Icon not supported: " + description);
    }

}
