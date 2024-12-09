package com.dlsc.jfxcentral2.components.filters;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Documentation;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class DocumentationFilterView extends SimpleSearchFilterView<Documentation> {
    private static List<FilterItem<Documentation>> typeFilterItems;

    public DocumentationFilterView() {
        getStyleClass().addAll("simple-model-object-filter-view", "tools-filter-view", "doc-filter-view");

        //Documentation are still displayed in alphabetical order.
        setSortGroup(new SortGroup<>("ORDER", List.of(
                new SortItem<>("From A to Z", Comparator.comparing((Documentation modelObject) -> modelObject.getName().toLowerCase())),
                new SortItem<>("From Z to A", Comparator.comparing((Documentation modelObject) -> modelObject.getName().toLowerCase()).reversed()))
        ));

        setSearchPromptText("Search for documentation ...");

        setOnSearch(text -> tool -> StringUtils.isBlank(text)
                || StringUtils.containsIgnoreCase(tool.getName(), text)
                || StringUtils.containsIgnoreCase(tool.getDescription(), text)
        );

        if (typeFilterItems == null) {
            typeFilterItems = getVideoFilterItems(Documentation::getTags, createFilterPredicate(Documentation::getTags));
        }
        getFilterGroups().setAll(List.of(
                new FilterGroup<>("TYPE", typeFilterItems)
        ));
    }

    private BiPredicate<Documentation, String> createFilterPredicate(Function<Documentation, String> attrGetter) {
        return (doc, item) -> {
            String attribute = attrGetter.apply(doc);
            return attribute != null && StringUtils.containsIgnoreCase(attribute, item);
        };
    }

    private List<FilterItem<Documentation>> getVideoFilterItems(
            Function<Documentation, String> attrGetter,
            BiPredicate<Documentation, String> predicate) {
        List<Documentation> appList = DataRepository.getInstance().getDocumentation();

        ArrayList<FilterItem<Documentation>> filterItems = new ArrayList<>(appList.stream()
                .flatMap(app -> Optional.ofNullable(attrGetter.apply(app)).stream()
                        .flatMap(attr -> Arrays.stream(attr.split(","))))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .sorted()
                .distinct()
                .map(item -> new FilterItem<Documentation>(item, it -> predicate.test(it, item)))
                .toList());
        filterItems.add(0, new FilterItem<>("ALL", item -> true, true));

        return filterItems;
    }
}

