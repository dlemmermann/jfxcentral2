package com.dlsc.jfxcentral2.utils;

import com.jpro.webapi.WebAPI;
import javafx.scene.Node;

public class WebAPIUtil {
    private WebAPIUtil() {
    }

    /**
     * Navigates to the given url.
     * If it is an external link, it will open in a new window;
     */
    public static void navigateToPage(Node node, String url) {
        System.out.println(url);
        if (url.startsWith("htttp")) {
            executeScript(node, "window.open('" + url + "', '_blank')");
        } else {
            executeScript(node, "window.location.assign('" + url + "')");
        }
    }

    public static void executeScript(Node node, String script) {
        WebAPI.getWebAPI(node.getScene()).executeScript(script);
    }

}
