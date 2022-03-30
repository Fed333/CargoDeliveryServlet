package com.epam.cargo.infrastructure.web.binding.constants;

public enum ConstantParameters {

    PAGE_PARAMETER("page"),
    SIZE_PARAMETER("size"),
    SORT_PROPERTY_PARAMETER("sort"),
    SORT_DIRECTION_PARAMETER("order");

    private final String name;

    ConstantParameters(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
