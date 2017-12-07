package JavaLinq.utils;

import java.util.Collection;

public class CollectionUtils {


    public static boolean inArray(Object needle, Object[] haystack) {
        for (Object tempObject : haystack) {
            if (needle.equals(tempObject)) return true;
        }
        return false;
    }

    public static boolean NullOrEmpty(Object[] value) {
        return value == null || value.length < 1;
    }

    public static boolean NullOrEmpty(Collection<Object> value) {
        return value == null || value.size() < 1;
    }
}
