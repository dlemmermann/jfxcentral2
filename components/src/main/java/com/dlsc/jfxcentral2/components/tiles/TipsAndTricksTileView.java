package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.platform.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TipsAndTricksTileView extends TileView<Tip> {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public TipsAndTricksTileView(Tip tip) {
        super(tip);

        getStyleClass().add("tips-tile-view");

        setButton1Text("READ NOW");
        setButton1Graphic(new FontIcon(IkonUtil.link));

        imageProperty().bind(ImageManager.getInstance().tipBannerImageProperty(tip));

        LocalDate date = tip.getCreatedOn();
        if (date != null) {
            String formattedDate = date.format(DEFAULT_DATE_FORMATTER);
            setRemark(formattedDate);
        } else {
            setRemark("Tips & Tricks");
        }

        LinkUtil.setLink(getButton1(), "/tips/" + tip.getId());
    }
}
