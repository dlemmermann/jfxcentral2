package com.dlsc.jfxcentral2.demo;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.mobile.components.MobileCategoryHeader;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.layout.Region;

public class HelloMobileCategoryHeader extends JFXCentralSampleBase {

    @Override
    protected Region createControl() {
        MobileCategoryHeader header = new MobileCategoryHeader();
        header.setTitle("Books");
        header.setIcon(IkonUtil.getModelIkon(Book.class));
        return header;
    }

    @Override
    public String getSampleName() {
        return "MobileCategoryHeader";
    }
}
