package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.model.Blog;
import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral.data.model.Company;
import com.dlsc.jfxcentral.data.model.Documentation;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Library;
import com.dlsc.jfxcentral.data.model.LinksOfTheWeek;
import com.dlsc.jfxcentral.data.model.ModelObject;
import com.dlsc.jfxcentral.data.model.News;
import com.dlsc.jfxcentral.data.model.OnlineTool;
import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral.data.model.Tip;
import com.dlsc.jfxcentral.data.model.Tool;
import com.dlsc.jfxcentral.data.model.Tutorial;
import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.iconfont.JFXCentralIcon;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.codicons.Codicons;
import org.kordamp.ikonli.coreui.CoreUiBrands;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.hawcons.HawconsStroke;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.materialdesign2.MaterialDesignN;

public interface IkonUtil {

    Ikon link = JFXCentralIcon.OPEN_LINK;
    Ikon timer = JFXCentralIcon.TIMER;
    Ikon champion = JFXCentralIcon.CHAMPION;
    Ikon rockstar = HawconsStroke.ROCK_N_ROLL;
    Ikon floppy = LineAwesomeSolid.SAVE;
    Ikon arrowLeft = MaterialDesign.MDI_ARROW_LEFT;
    Ikon arrowRight = MaterialDesign.MDI_ARROW_RIGHT;
    Ikon website = JFXCentralIcon.WWW;
    Ikon edit = JFXCentralIcon.EDIT;
    Ikon delete = JFXCentralIcon.DELETE;
    Ikon close = JFXCentralIcon.COSS;
    Ikon people = JFXCentralIcon.PEOPLE;
    Ikon copy = MaterialDesign.MDI_CONTENT_COPY;

    Ikon play = JFXCentralIcon.PLAY;
    Ikon github = JFXCentralIcon.GITHUB;
    Ikon twitter = JFXCentralIcon.TWITTER;
    Ikon mastodon = CoreUiBrands.MASTODON;
    Ikon reddit = MaterialDesign.MDI_REDDIT;
    Ikon facebook = JFXCentralIcon.FACEBOOK;
    Ikon linkedin = JFXCentralIcon.LINKEDIN;
    Ikon mail = JFXCentralIcon.EMAIL;
    Ikon app = JFXCentralIcon.APP;
    Ikon blog = JFXCentralIcon.BLOGS;
    Ikon book = JFXCentralIcon.BOOKS;
    Ikon company = JFXCentralIcon.COMPANIES;
    Ikon download = JFXCentralIcon.DOWNLOAD;
    Ikon library = JFXCentralIcon.LIBRARIES;
    Ikon person = JFXCentralIcon.PEOPLE;
    Ikon tip = JFXCentralIcon.TIPS_TRICKS;
    Ikon tool = JFXCentralIcon.TOOLS;
    Ikon tutorial = JFXCentralIcon.TUTORIALS;
    Ikon video = JFXCentralIcon.VIDEOS;
    Ikon icons = MaterialDesign.MDI_EMOTICON;
    Ikon documentation = Codicons.BOOK;
    Ikon onlineTools = FluentUiRegularAL.DOCUMENT_TOOLBOX_20;

    Ikon news = MaterialDesignN.NEWSPAPER_VARIANT_OUTLINE;
    Ikon linkOfTheWeek = JFXCentralIcon.LINKS_OF_THE_WEEK;

    static Ikon getModelIkon(ModelObject mo) {
        return getModelIkon(mo.getClass());
    }

    static Ikon getModelIkon(Class<? extends ModelObject> clazz) {
        if (clazz == RealWorldApp.class) {
            return app;
        } else if (clazz == Blog.class) {
            return blog;
        } else if (clazz == Book.class) {
            return book;
        } else if (clazz == Company.class) {
            return company;
        } else if (clazz == Download.class) {
            return download;
        } else if (clazz == Library.class) {
            return library;
        } else if (clazz == Person.class) {
            return person;
        } else if (clazz == Tip.class) {
            return tip;
        } else if (clazz == Tool.class) {
            return tool;
        } else if (clazz == Tutorial.class) {
            return tutorial;
        } else if (clazz == Video.class) {
            return video;
        } else if (clazz == LinksOfTheWeek.class) {
            return linkOfTheWeek;
        } else if (clazz == IkonliPack.class) {
            return icons;
        } else if (clazz == News.class) {
            return news;
        } else if (clazz == Documentation.class) {
            return documentation;
        }  else if (clazz == OnlineTool.class){
            return onlineTools;
        } else {
            return null;
        }
    }

}
