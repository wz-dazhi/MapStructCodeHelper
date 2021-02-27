package com.wz.mapstruct.util;

import java.util.Collection;

/**
 * @package: com.wz.com.wz.mapstruct.util
 * @className: CollectionUtil
 * @description:
 * @author: zhi
 * @date: 2021/2/1
 * @version: 1.0
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return !isEmpty(collection);
    }

    public static <E> boolean isEmpty(Collection<E> collection) {
        return null == collection || collection.isEmpty();
    }
}
