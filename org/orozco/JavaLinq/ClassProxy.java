package JavaLinq;

public class ClassProxy {
    private Class clazz;
    private boolean isParameterizedType;
    private boolean isArray;


    public ClassProxy(Class clazz) {
        this.clazz = clazz;
        initProxy(clazz);
    }


    private void initProxy(Class clazz) {
        isArray = clazz.isArray();
        isParameterizedType = clazz.getTypeParameters().length > 0;
    }

}
