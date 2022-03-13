package com.epam.cargo.service;

import com.epam.cargo.dto.SortRequest;
import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.DeliveryApplication;
import com.epam.cargo.entity.User;
import com.epam.cargo.exception.ModelErrorAttribute;
import com.epam.cargo.exception.WrongDataAttributeException;
import com.epam.cargo.exception.WrongInput;

import com.epam.cargo.dto.SortRequest.Order;

import java.util.*;

/**
 * Utils service class.<br>
 * Has common useful for services static methods.
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class ServiceUtils {

    /**
     * Checks whether delivery application's sending and receiving data are valid.<br>
     * Declares that receiving date cannot precede a sending one.<br>
     * @throws WrongDataAttributeException if receiving date precedes sending
     * @since 1.0
     * @author Roman Kovalchuk
     * */
    private static void requireValidDates(DeliveryApplication object, ResourceBundle bundle) throws WrongDataAttributeException {
        if (object.getSendingDate().isAfter(object.getReceivingDate())){
            throw new WrongDataAttributeException(ModelErrorAttribute.RECEIVING.getAttr(), bundle, WrongInput.REQUIRED_BEING_AFTER_SENDING);
        }
    }

    /**
     * Checks whether addresses are different.<br>
     * Declares that sender and receiver address cannot be equal.<br>
     * @throws IllegalArgumentException if sender and receiver addresses are the same
     * @since 1.0
     * @author Roman Kovalchuk
     * */
    private static void requireDifferentAddresses(Address senderAddress, Address receiverAddress) {
        if (senderAddress.equals(receiverAddress)){
            throw new IllegalArgumentException("Sender and Receiver address mustn't be equal!");
        }
    }

    /**
     * Common list sorting with SortRequest and ComparatorRecognizer.<br>
     * @param list List to sort
     * @param sort request of sorting by
     * @param recognizer mean of recognizing according to the SortRequest comparators
     * @since 1.0
     * @see SortRequest
     * @see ComparatorRecognizer
     * @author Roman Kovalhchuk
     * */
    static <T> void sortList(List<T> list, SortRequest sort, ComparatorRecognizer<T> recognizer) {
        Objects.requireNonNull(sort.getProperty(), "Sort property cannot be null");
        Comparator<T> comparator = recognizer.getComparator(sort.getProperty(), Optional.ofNullable(sort.getOrder()).orElse(Order.ASC));

        if (!Objects.isNull(comparator)) {
            list.sort(comparator);
        }
    }

    /**
     * Checks authorized rights for personal actions.
     * @param user specified user for comparison
     * @param initiator user from context who initiated the action
     * @return equals of login and password
     * @since 1.0
     * @author Roman Kovalchuk
     * */
    static boolean credentialsEquals(User user, User initiator) {
        return Objects.equals(user.getLogin(), initiator.getLogin()) && Objects.equals(user.getPassword(), initiator.getPassword());
    }

    public interface ComparatorRecognizer<T> {
        Comparator<T> getComparator(String property, Order order);
    }

}
