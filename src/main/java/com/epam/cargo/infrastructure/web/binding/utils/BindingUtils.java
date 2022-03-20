package com.epam.cargo.infrastructure.web.binding.utils;

import com.epam.cargo.infrastructure.annotation.Qualifier;
import com.epam.cargo.infrastructure.context.ApplicationContext;
import com.epam.cargo.infrastructure.format.formatter.Formatter;
import com.epam.cargo.infrastructure.format.manager.FormatterManager;
import com.epam.cargo.infrastructure.web.data.sort.Order;
import com.epam.cargo.infrastructure.web.data.sort.Sort;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utils class for managing with common web binding operations.<br>
 * @author Roman Kovalchuk
 * @version 1.2
 * */
public class BindingUtils {

    /**
     * Binds value of given type from HttpServletRequest.<br>
     * @param <T> generic type of bind object
     * @param bindClass class of object to bind
     * @param req http web request where we bind from
     * @param parameterName name of source value to bind
     * @param context ApplicationContext for getting necessities infrastructure's objects
     * @return bind from req object of generic T class
     * @author Roman Kovalchuk
     * @see HttpServletRequest
     * @see ApplicationContext
     * @since 1.0
     * */
    @SuppressWarnings("unchecked")
    public static <T> T bindType(Class<T> bindClass, HttpServletRequest req, String parameterName, ApplicationContext context) {
        Object bindValue = req.getParameter(parameterName);
        if (Objects.nonNull(bindValue) && !bindClass.isAssignableFrom(bindValue.getClass())){
            if (!((String)bindValue).isBlank()) {
                bindValue = convertTypeFromString((String) bindValue, bindClass, context.getObject(FormatterManager.class));
            } else {
                bindValue = null;
            }
        }
        return (T) bindValue;
    }

    /**
     * Converts bind type from a plain String.<br>
     * @param <T> generic type of bind object
     * @param value String to convert into bind T class
     * @param bindClass type where we bind String to
     * @param manager mean of converting String to bind T class
     * @return bind from String object of generic T class
     * @author Roman Kovalchuk
     * @see FormatterManager
     * @see Formatter
     * @since 1.0
     * */
    @SuppressWarnings("unchecked")
    public static <T> T convertTypeFromString(String value, Class<T> bindClass, FormatterManager manager) {
        Optional<Formatter<String, T>> formatterOptional = manager.getFormatter(String.class, bindClass);
        if (formatterOptional.isPresent()){
            return formatterOptional.get().format(value);
        } else {
            if (bindClass.isEnum()){
                return (T) bindEnumType(bindClass, value);
            }
            throw new RuntimeException(String.format("No formatter from %s to %s was found! Please specify one with %s ", String.class, bindClass, FormatterManager.class));
        }
    }

    /**
     * Binds enum classes from String value.<br>
     * Uses standard {@link Enum#valueOf(Class, String)} method.
     * @param bindClass class which must be enum
     * @param constantName name of enum constant
     * @return according to the constantName enum constant, if constantName is null or blank returns null
     * @author Roman Kovalchuk
     * @see Enum
     * @since 1.0
     * */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Enum<?> bindEnumType(Class<?> bindClass, String constantName){
        if (!bindClass.isEnum()){
            throw new RuntimeException("Misuse of bindEnumType method. No enum class was passed as argument.");
        }

        if (Objects.nonNull(constantName) && !constantName.isBlank()) {
            return Enum.valueOf((Class)bindClass, constantName);
        }
        return null;
    }

    /**
     * Binds Sort object from HttpServletRequest.<br>
     * @param req source of the data
     * @param propertyParameter parameter of sorting property in req
     * @param directionParameter parameter of sorting direction in req
     * @return Sort object with sorting data
     * @since 1.1
     * */
    public static Sort bindSort(HttpServletRequest req, String propertyParameter, String directionParameter) {
        String[] properties = req.getParameterValues(propertyParameter);
        String[] directionsArray = Optional.ofNullable(req.getParameterValues(directionParameter)).orElse(new String[]{});
        Order.Direction[] directions = new Order.Direction[directionsArray.length];
        directions = Arrays.stream(directionsArray).map(Order.Direction::valueOf).collect(Collectors.toList()).toArray(directions);
        Order[] orders = bindOrders(properties, directions);
        return Sort.by(orders);
    }

    /**
     * Gets sorting orders from source arrays.<br>
     * @param properties array with sorting properties
     * @param directions arrays with sorting directions
     * @return Order[] with sorting orders, if passed null or empty properties returns empty array
     * @since 1.1
     * */
    public static Order[] bindOrders(String[] properties, Order.Direction[] directions){
        if (Objects.isNull(properties) || properties.length == 0){
            return new Order[]{};
        }
        Order[] orders = new Order[properties.length];

        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            Order.Direction direction = (i < directions.length) ? directions[i] : Order.Direction.ASC;
            orders[i] = new Order(property, direction);
        }
        return orders;
    }

    /**
     * Qualifies web parameter if it's annotated with @Qualifier annotation.<br>
     * @param parameter web parameter to qualify
     * @return prefix from qualifier value if present in format "value_", otherwise returns ""
     * @since 1.2
     * */
    @NotNull
    public static String qualify(Parameter parameter){
        if (parameter.isAnnotationPresent(Qualifier.class)){
            Qualifier qualifier = parameter.getAnnotation(Qualifier.class);
            return qualifier.value() + "_";
        } else {
            return "";
        }
    }

    /**
     * Qualifies parameterName if web parameter is annotated with {@link Qualifier} annotation.<br>
     * @param parameter web parameter which might be qualified
     * @param parameterName parameter name to qualify
     * @return result of concatenation a {@link #qualify(Parameter)} method and parameterName
     * @since 1.2
     * */
    @NotNull
    public static String qualify(Parameter parameter, String parameterName){
        return qualify(parameter) + parameterName;
    }

}
