package JavaLinq;

import sun.rmi.transport.ObjectTable;

import java.lang.reflect.Field;

public abstract class LinQ<T> {
    static ClassContainer classContainer;

    public static Object getFieldValueFromNameAndType(String fieldName, Object value,
                                                      Class<?> clazz) {
        Class classValue = value.getClass();
        classContainer = getClassContainer(classValue);
        Field field = classContainer.getFieldMap().get(fieldName);
        if (field == null) {
            throw new RuntimeException(
                String.format("Class [%s] does not have field [%s]", classValue.getName(), fieldName));
        }

        if (!field.getType().equals(clazz)) {
            throw new RuntimeException(
                String.format("Field [%s] is not of Type [%s]", fieldName, clazz.getName()));
        }


        try {
            return field.get(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private static ClassContainer getClassContainer(Class<?> clazz) {
        if (classContainer == null) {
            classContainer = ClassContainer.fromClass(clazz);
        }
        return classContainer;
    }
}
