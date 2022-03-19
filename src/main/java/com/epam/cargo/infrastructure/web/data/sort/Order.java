package com.epam.cargo.infrastructure.web.data.sort;

/**
 * Class which contains a sorting characteristic.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public final class Order {

    /**
     * Object property which we sort by.<br>
     * @since 1.0
     * */
    private final String property;

    /**
     * Direction of sorting.<br>
     * @since 1.0
     * */
    private final Direction direction;

    /**
     * Makes sort order according to the given parameters.<br>
     * @param property object property which we sort by
     * @param direction order of sorting
     * @since 1.0
     * @see Direction
     * */
    public Order(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Checks whether sorting direction is ascending.
     * @return true if direction is ASC, otherwise false
     * @since 1.0
     * */
    public boolean isAscending(){
        return direction.equals(Direction.ASC);
    }

    /**
     * Checks whether sorting direction is descending.
     * @return true if direction is DESC, otherwise false
     * @since 1.0
     * */
    public boolean isDescending(){
        return direction.equals(Direction.DESC);
    }

    /**
     * Enum with sorting direction constants.<br>
     * Has two constants.<br>
     * ASC - for ascending sorting order.<br>
     * DESC - for descending sorting order.<br>
     * @author Roman Kovalchuk
     * @version 1.0
     * */
    public enum Direction{
        ASC, DESC
    }

}
