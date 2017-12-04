package JavaLinq;

import java.util.Collection;

public class CollectionLinQBuilder<T> extends LinQExpressionBuilder<T> {

    public CollectionLinQBuilder(Collection<T> initValue) {
        this.initValue = initValue;
    }

}
