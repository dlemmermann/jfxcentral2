package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Video;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class VideosFilterView extends SimpleModelObjectSearchFilterView<Video> {

    private static List<FilterItem<Video>> typeFilterItems;
    private static List<FilterItem<Video>> eventFilterItems;
    private static List<FilterItem<Video>> domainFilterItems;

    public VideosFilterView() {
        getStyleClass().add("videos-filter-view");

        setSortGroup(null);
        setSearchPromptText("Search for a video ...");

        if (typeFilterItems == null) {
            typeFilterItems = getVideoFilterItems(Video::getType, createFilterPredicate(Video::getType));
        }

        if (eventFilterItems == null) {
            eventFilterItems = getVideoFilterItems(Video::getEvent, createFilterPredicate(Video::getEvent));
        }

        if (domainFilterItems == null) {
            domainFilterItems = getVideoFilterItems(Video::getDomain, createFilterPredicate(Video::getDomain));
        }

        getFilterGroups().setAll(List.of(
                new FilterGroup<>("Type", typeFilterItems),
                new FilterGroup<>("Event", eventFilterItems),
                new FilterGroup<>("Domain", domainFilterItems)
        ));

        setOnSearch(text -> video -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(video.getName(), text)
                || StringUtils.containsIgnoreCase(video.getDescription(), text)
        );
    }

    private BiPredicate<Video, String> createFilterPredicate(Function<Video, String> attrGetter) {
        return (video, item) -> {
            String attribute = attrGetter.apply(video);
            return attribute != null && StringUtils.containsIgnoreCase(attribute, item);
        };
    }

    private List<FilterItem<Video>> getVideoFilterItems(
            Function<Video, String> attrGetter,
            BiPredicate<Video, String> predicate) {
        List<Video> appList = DataRepository.getInstance().getVideos();

        ArrayList<FilterItem<Video>> filterItems = new ArrayList<>(appList.stream()
                .flatMap(app -> Optional.ofNullable(attrGetter.apply(app)).stream()
                        .flatMap(attr -> Arrays.stream(attr.split(","))))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .sorted()
                .distinct()
                .map(item -> new FilterItem<Video>(item, it -> predicate.test(it, item)))
                .toList());
        filterItems.add(0, new FilterItem<>("ALL", item -> true, true));

        return filterItems;
    }
}
