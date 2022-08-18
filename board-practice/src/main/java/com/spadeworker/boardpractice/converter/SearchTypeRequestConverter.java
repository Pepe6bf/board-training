package com.spadeworker.boardpractice.converter;

import com.spadeworker.boardpractice.domain.SearchType;
import org.springframework.core.convert.converter.Converter;

public class SearchTypeRequestConverter implements Converter<String, SearchType> {
    @Override
    public SearchType convert(String searchType) {
        return SearchType.of(searchType);
    }
}
