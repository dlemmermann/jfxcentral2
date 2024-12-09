package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Learn;
import com.dlsc.jfxcentral.data.model.LearnJavaFX;
import com.dlsc.jfxcentral.data.model.LearnMobile;
import com.dlsc.jfxcentral.data.model.LearnRaspberryPi;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;

public class LearnFilterView extends SimpleModelObjectSearchFilterView<Learn> {

    public LearnFilterView() {
        getStyleClass().addAll("learn-filter-view", "tools-filter-view");

        setSearchPromptText("Search for a lesson ...");

        setOnSearch(text -> learn -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(learn.getName(), text)
        );

        setSortGroup(new SortGroup<>("ORDER", List.of(
                new SortItem<>("Natural Order", Comparator.comparing((Learn modelObject) -> {
                    if (modelObject instanceof LearnJavaFX fxmodel) {
                        List<LearnJavaFX> fxList = DataRepository.getInstance().getLearnJavaFX();
                        return fxList.indexOf(fxmodel);
                    } else if (modelObject instanceof LearnMobile mobile) {
                        List<LearnMobile> mobileList = DataRepository.getInstance().getLearnMobile();
                        return mobileList.indexOf(mobile);
                    } else if (modelObject instanceof LearnRaspberryPi pi) {
                        List<LearnRaspberryPi> piList = DataRepository.getInstance().getLearnRaspberryPi();
                        return piList.indexOf(pi);
                    }
                    return 0;
                })),
                new SortItem<>("From A to Z", Comparator.comparing((Learn modelObject) -> modelObject.getName().toLowerCase())),
                new SortItem<>("From Z to A", Comparator.comparing((Learn modelObject) -> modelObject.getName().toLowerCase()).reversed()))
        ));
    }
}
