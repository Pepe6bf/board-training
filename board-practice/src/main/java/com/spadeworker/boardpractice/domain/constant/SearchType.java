package com.spadeworker.boardpractice.domain.constant;

public enum SearchType {
    TITLE("title"),
    CONTENT("content"),
    ID("id"),
    NICKNAME("nickname"),
    HASHTAG("hashtag");

    private final String typeName;

    SearchType(String typeName) {
        this.typeName = typeName;
    }

    public static SearchType of(String typeName) {
        if (typeName == null) {
            throw new IllegalArgumentException();
        }

        for (SearchType st : SearchType.values()) {
            if (st.typeName.equals(typeName)) {
                return st;
            }
        }

        throw new IllegalArgumentException("일치하는 검색 타입이 없습니다.");
    }
}
