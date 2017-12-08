package JavaLinq;

import java.util.Collection;

public abstract class ConditionHelper {

    public <K> boolean objectInCondition(Object value, Collection<K> collection) {
        return collection.contains(value);
    }
}
