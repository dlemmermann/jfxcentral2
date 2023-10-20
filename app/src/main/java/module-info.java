open module com.dlsc.jfxcentral2.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires com.gluonhq.attach.browser;
    requires com.gluonhq.attach.statusbar;

    requires jfxcentral.data;
    requires org.eclipse.jgit;
    requires fr.brouillard.oss.cssfx;
    requires com.dlsc.jfxcentral2.components;
    requires one.jpro.platform.routing.core;
    requires one.jpro.platform.routing.dev;
    requires com.dlsc.gemsfx;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    // ikonli icon packs START
    requires org.kordamp.ikonli.antdesignicons;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.ikonli.boxicons;
    requires org.kordamp.ikonli.bpmn;
    requires org.kordamp.ikonli.captainicon;
    requires org.kordamp.ikonli.carbonicons;
    requires org.kordamp.ikonli.codicons;
    requires org.kordamp.ikonli.coreui;
    requires org.kordamp.ikonli.dashicons;
    requires org.kordamp.ikonli.devicons;
    requires org.kordamp.ikonli.elusive;
    requires org.kordamp.ikonli.entypo;
    requires org.kordamp.ikonli.evaicons;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.fileicons;
    requires org.kordamp.ikonli.fluentui;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.fontelico;
    requires org.kordamp.ikonli.foundation;
    requires org.kordamp.ikonli.hawcons;
    requires org.kordamp.ikonli.icomoon;
    requires org.kordamp.ikonli.ionicons;
    requires org.kordamp.ikonli.ionicons4;
    requires org.kordamp.ikonli.jam;
    requires org.kordamp.ikonli.ligaturesymbols;
    requires org.kordamp.ikonli.lineawesome;
    requires org.kordamp.ikonli.linecons;
    requires org.kordamp.ikonli.maki;
    requires org.kordamp.ikonli.maki2;
    requires org.kordamp.ikonli.mapicons;
    requires org.kordamp.ikonli.material;
    requires org.kordamp.ikonli.material2;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.medicons;
    requires org.kordamp.ikonli.metrizeicons;
    requires org.kordamp.ikonli.microns;
    requires org.kordamp.ikonli.ociicons;
    requires org.kordamp.ikonli.octicons;
    requires org.kordamp.ikonli.openiconic;
    requires org.kordamp.ikonli.paymentfont;
    requires org.kordamp.ikonli.prestashopicons;
    requires org.kordamp.ikonli.remixicon;
    requires org.kordamp.ikonli.runestroicons;
    requires org.kordamp.ikonli.simpleicons;
    requires org.kordamp.ikonli.simplelineicons;
    requires org.kordamp.ikonli.subway;
    requires org.kordamp.ikonli.swing;
    requires org.kordamp.ikonli.themify;
    requires org.kordamp.ikonli.typicons;
    requires org.kordamp.ikonli.unicons;
    requires org.kordamp.ikonli.weathericons;
    requires org.kordamp.ikonli.websymbols;
    requires org.kordamp.ikonli.whhg;
    requires org.kordamp.ikonli.win10;
    requires org.kordamp.ikonli.zondicons;
    requires com.dustinredmond.fxtrayicon;
    requires com.dlsc.jfxcentral2.iconfont;
    // ikonli icon packs END

    exports com.dlsc.jfxcentral2.app;
    exports com.dlsc.jfxcentral2.app.pages;
    exports com.dlsc.jfxcentral2.app.stage;
    exports com.dlsc.jfxcentral2.app.service;
}