package com.epam.cargo.service;

import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.infrastructure.web.data.sort.Order.*;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
import com.epam.cargo.utils.TestUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

class ServiceUtilsTest {

    public static List<SortedClass> fullASCSortedList = List.of(
            new SortedClass("a", 0L, false),
            new SortedClass("a", 1L, false),
            new SortedClass("a", 1L, true),
            new SortedClass("b", 0L, false),
            new SortedClass("b", 1L, false),
            new SortedClass("b", 1L, true)
    );

    public static List<SortedClass> fullDESCSortedList = List.of(
            new SortedClass("b", 1L, true),
            new SortedClass("b", 1L, false),
            new SortedClass("b", 0L, false),
            new SortedClass("a", 1L, true),
            new SortedClass("a", 1L, false),
            new SortedClass("a", 0L, false)
    );

    public static Stream<Arguments> sortedTestCase() {
        return Stream.of(
                Arguments.of(listToSort(), fullPropertyASCSort(), fullComparatorASC()),
                Arguments.of(listToSort(), fullPropertyDESCSort(), fullComparatorDESC())
        );
    }

    @NotNull
    private static Comparator<SortedClass> fullComparatorASC() {
        return Comparator.comparing(SortedClass::getProperty1)
                .thenComparing(SortedClass::getProperty2)
                .thenComparing(SortedClass::getProperty3);
    }

    @NotNull
    private static Comparator<SortedClass> fullComparatorDESC() {
        return Comparator.comparing(SortedClass::getProperty1)
                .thenComparing(SortedClass::getProperty2)
                .thenComparing(SortedClass::getProperty3).reversed();
    }

    @NotNull
    private static Sort fullPropertyDESCSort() {
        return Sort.by(
                new Order("property1", Direction.DESC),
                new Order("property2", Direction.DESC),
                new Order("property3", Direction.DESC)
        );
    }

    @NotNull
    private static Sort fullPropertyASCSort() {
        return Sort.by(
                new Order("property1", Direction.ASC),
                new Order("property2", Direction.ASC),
                new Order("property3", Direction.ASC)
        );
    }

    public static List<SortedClass> listToSort(){
        List<SortedClass> list = new ArrayList<>(fullASCSortedList);
        Collections.shuffle(list);
        return list;
    }

    @ParameterizedTest
    @MethodSource("sortedTestCase")
    void sortList(List<SortedClass> sourceSort, Sort sort, Comparator<SortedClass> expectedComparator) {
        ServiceUtils.sortList(sourceSort, sort, new SortedClassComparatorRecognized());
        Assertions.assertTrue(TestUtils.isSorted(sourceSort, expectedComparator));
    }

    static class SortedClass {

        private String property1;
        private Long property2;
        private Boolean property3;

        public SortedClass(String property1, Long property2, Boolean property3) {
            this.property1 = property1;
            this.property2 = property2;
            this.property3 = property3;
        }

        public String getProperty1() {
            return property1;
        }

        public void setProperty1(String property1) {
            this.property1 = property1;
        }

        public Long getProperty2() {
            return property2;
        }

        public void setProperty2(Long property2) {
            this.property2 = property2;
        }

        public Boolean getProperty3() {
            return property3;
        }

        public void setProperty3(Boolean property3) {
            this.property3 = property3;
        }

        @Override
        public String toString() {
            return "{" +
                    "" + property1  +
                    " " + property2 +
                    " " + property3 +
                    '}';
        }
    }

    static class SortedClassComparatorRecognized implements ServiceUtils.ComparatorRecognizer<SortedClass> {

        private final Map<String, Comparator<SortedClass>> comparators =
                Map.of(
                  "property1", Comparator.comparing(SortedClass::getProperty1),
                  "property2", Comparator.comparing(SortedClass::getProperty2),
                  "property3", Comparator.comparing(SortedClass::getProperty3)
                );

        @Override
        public Comparator<SortedClass> getComparator(Order order) {
            Comparator<SortedClass> cmp = comparators.get(order.getProperty());
            if (order.isDescending() && Objects.nonNull(cmp)){
                cmp = cmp.reversed();
            }
            return cmp;
        }
    }
}