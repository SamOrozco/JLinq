package JavaLinq;

import java.util.Collection;

public class JLinq {
    private LinQ linQ;

    public JLinq(){
    }

    public static <T> CollectionLinQBuilder<T> from(Collection<T> value) {
        return new CollectionLinQBuilder<>(value);
    }


}
