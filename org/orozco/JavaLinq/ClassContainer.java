package JavaLinq;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassContainer {
    Field[] fields;
    Method[] methods;
    private Map<String, Field> fieldMap;
    private Map<String, Method> methodMap;

    private ClassContainer() {
    }

    public static ClassContainer fromClass(Class clazz) {
        ClassContainer container = new ClassContainer();
        container.fields = clazz.getDeclaredFields();
        container.methods = clazz.getDeclaredMethods();
        container.fieldMap = buildFieldMap(container.fields);
        container.methodMap = buildMethodMap(container.methods);
        return container;
    }


    private static Map<String, Field> buildFieldMap(Field[] fields) {
        HashSet<String> val = new HashSet<>();
        return Arrays.stream(fields)
                     .filter(field -> {
                         if(!val.contains(field.getName())){
                             val.add(field.getName());
                             return true;
                         }
                         return false;
                     })
                     .collect(Collectors.toMap(Field::getName, field -> field));
    }


    private static Map<String, Method> buildMethodMap(Method[] methods) {
        HashSet<String> val = new HashSet<>();
        return Arrays.stream(methods)
                     .filter(method -> {
                         if(!val.contains(method.getName())){
                             val.add(method.getName());
                             return true;
                         }
                         return false;
                     })
                     .collect(Collectors.toMap(Method::getName, method -> method));
    }

    public Map<String, Field> getFieldMap() {
        if (fieldMap == null) {
            return new HashMap<>();
        }
        return fieldMap;
    }


    public Map<String, Method> getMethodMap() {
        if (methodMap == null) {
            methodMap = new HashMap<>();
        }
        return methodMap;
    }
}
