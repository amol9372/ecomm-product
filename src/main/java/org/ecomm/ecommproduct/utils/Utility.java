package org.ecomm.ecommproduct.utils;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.stream.Stream;

public class Utility {

    public static <T> Stream<T> stream(Collection<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return Stream.empty();
        } else {
            return collection.stream();
        }
    }

}
