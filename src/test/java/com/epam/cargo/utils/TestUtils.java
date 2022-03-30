package com.epam.cargo.utils;

import com.epam.cargo.entity.DirectionDelivery;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TestUtils {

    public static <T> boolean isSorted(List<T> list, Comparator<T> comparator){
        Iterator<T> it = list.iterator();
        if (it.hasNext()) {
            T o1 = it.next();
            T o2;
            while(it.hasNext()){
                o2 = it.next();
                if (comparator.compare(o1, o2) > 0){
                    return false;
                }
                o1 = o2;
            }
        }
        return true;
    }

}
