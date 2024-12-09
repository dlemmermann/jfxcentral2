package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Utility;
import com.dlsc.jfxcentral2.components.CustomMarkdownView;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import com.dlsc.jfxcentral2.utilities.cssplayground.CssPlaygroundView;
import com.dlsc.jfxcentral2.utilities.effectdesigner.EffectDesignerView;
import com.dlsc.jfxcentral2.utilities.paintpicker.GradientDesignerView;
import com.dlsc.jfxcentral2.utilities.pathextractor.SVGPathExtractorView;
import com.dlsc.jfxcentral2.utilities.pxemconverter.Px2EmView;
import com.dlsc.jfxcentral2.utils.ExternalLinkUtil;
import com.dlsc.jfxcentral2.utils.ModelObjectTool;
import com.jpro.webapi.WebAPI;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.fileicons.FileIcons;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;
import org.kordamp.ikonli.simplelineicons.SimpleLineIcons;

public class UtilityOverviewBox extends OverviewBox<Utility> {

    private Node toolPane;
    private Node readmeView;

    public UtilityOverviewBox(Utility model) {
        super(model);
        getStyleClass().add("utility-overview-box");
    }

    @Override
    protected Node createTopNode() {
        if (toolPane == null) {
            toolPane = createToolPane(getModel());
        }
        return toolPane;
    }

    @Override
    protected Node createBottomNode() {
        if (readmeView == null) {
            readmeView = readmeView(getModel());
        }
        return readmeView;
    }

    private Node createToolPane(Utility model) {
        if (StringUtils.equalsIgnoreCase(model.getId(), "pathextractor")) {
            setTitle("SVG Path Extractor");
            setIcon(MaterialDesignS.SHAPE_OUTLINE);
            if (!model.isOnlineSupported() && WebAPI.isBrowser()) {
                return createOnlyOnClientNode();
            }
            SVGPathExtractorView view = new SVGPathExtractorView(model);
            view.sizeProperty().bind(sizeProperty());
            return view;
        } else if (StringUtils.equalsIgnoreCase(model.getId(), "cssplayground")) {
            setTitle("Css Playground");
            setIcon(Elusive.CSS);
            if (!model.isOnlineSupported() && WebAPI.isBrowser()) {
                return createOnlyOnClientNode();
            }
            CssPlaygroundView cssPlaygroundView = new CssPlaygroundView();
            cssPlaygroundView.sizeProperty().bind(sizeProperty());
            return cssPlaygroundView;
        } else if (StringUtils.equalsIgnoreCase(model.getId(), "gradientdesigner")) {
            setTitle("Gradient Designer");
            setIcon(Material2OutlinedAL.COLOR_LENS);
            getStyleClass().add("gradient-designer-overview-box");
            if (!model.isOnlineSupported() && WebAPI.isBrowser()) {
                return createOnlyOnClientNode();
            }
            return new GradientDesignerView(sizeProperty());
        } else if (StringUtils.equalsIgnoreCase(model.getId(), "effectdesigner")) {
            setTitle("Effect Designer");
            setIcon(SimpleLineIcons.MAGIC_WAND);
            getStyleClass().add("effect-designer-overview-box");
            if (!model.isOnlineSupported() && WebAPI.isBrowser()) {
                return createOnlyOnClientNode();
            }
            EffectDesignerView effectDesignerView = new EffectDesignerView();
            effectDesignerView.sizeProperty().bind(sizeProperty());
            return effectDesignerView;
        } else if (StringUtils.equalsIgnoreCase(model.getId(), "pxtoemconverter")) {
            setTitle("Pixel-to-EM Converter");
            setIcon(CarbonIcons.CD_CREATE_EXCHANGE);
            getStyleClass().add("px-2-em-overview-box");
            if (!model.isOnlineSupported() && WebAPI.isBrowser()) {
                return createOnlyOnClientNode();
            }
            Px2EmView view = new Px2EmView();
            view.sizeProperty().bind(sizeProperty());
            return view;
        }
        return createComingSoonNode();
    }

    private Node readmeView(Utility model) {
        String readme = DataRepository.getInstance().getUtilityReadMe(model);
        CustomMarkdownView markdownView = new CustomMarkdownView(readme);
        markdownView.setDisable(!ModelObjectTool.isUtilityCanBeUsed(model));
        return markdownView;
    }

    private Node createComingSoonNode() {
        Label label = new Label("Coming soon. Stay tuned!", new FontIcon(FileIcons.CAFFE2));
        label.getStyleClass().add("coming-soon-label");
        return label;
    }

    private Node createOnlyOnClientNode() {
        Label label = new Label("This tool is not supported when run inside a browser.",
                new FontIcon(FluentUiRegularMZ.PHONE_LAPTOP_20));
        label.setWrapText(true);
        label.getStyleClass().add("client-only-tips");

        Button downloadBtn = new Button("Install Locally", new FontIcon(JFXCentralIcon.INSTALL_LOCALLY));
        downloadBtn.getStyleClass().addAll("fill-button", "download-button");
        downloadBtn.setFocusTraversable(false);
        ExternalLinkUtil.setExternalLink(downloadBtn, "https://downloads.hydraulic.dev/jfxcentral2/download.html");

        VBox box = new VBox(label, downloadBtn);
        box.getStyleClass().add("client-only-box");
        return box;
    }
}
