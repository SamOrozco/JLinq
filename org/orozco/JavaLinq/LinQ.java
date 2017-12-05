package JavaLinq;

import apptree.condition.Condition;
import apptree.condition.conditions.BasicCondition;
import sun.rmi.transport.ObjectTable;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class LinQ<T> {
    static ClassContainer classContainer;

    public static Object getFieldValueFromNameAndType(String fieldName, Object value,
                                                      Class<?> clazz) {
        Class classValue = value.getClass();
        classContainer = getClassContainer(classValue);
        Field field = classContainer.getFieldMap().get(fieldName);
        if (field == null) {
            throw new RuntimeException(
                String.format("Class [%s] does not have field [%s]", classValue.getName(),
                              fieldName));
        }

        if (!classEquals(field.getType(), clazz)) {
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


    Condition<T> getEqualsStringCondition(String fieldName, String value) {
        return BasicCondition.withCondition(conditionValue ->
                                                Objects.equals(
                                                    getFieldValueFromNameAndType(
                                                        fieldName,
                                                        conditionValue,
                                                        String.class),
                                                    value));

    }

    Condition<T> getEqualsIntegerCondition(String fieldName, Integer value) {
        return BasicCondition.withCondition(conditionValue ->
                                                Objects.equals(
                                                    getFieldValueFromNameAndType(
                                                        fieldName,
                                                        conditionValue,
                                                        value.getClass()),
                                                    value));

    }


    Condition<T> getNotEqualsStringCondition(String fieldName, String value) {
        return BasicCondition.withCondition(conditionValue ->
                                                !Objects.equals(
                                                    getFieldValueFromNameAndType(
                                                        fieldName,
                                                        conditionValue,
                                                        value.getClass()),
                                                    value));

    }


    Condition<T> getNotEqualsIntegerCondition(String fieldName, Integer value) {
        return BasicCondition.withCondition(conditionValue ->
                                                !Objects.equals(
                                                    getFieldValueFromNameAndType(
                                                        fieldName,
                                                        conditionValue,
                                                        value.getClass()),
                                                    value));

    }


    private static boolean classEquals(Class one, Class two) {
        String oneName = one.getName();
        String twoName = two.getName();
        switch (oneName) {
            case "Integer":
            case "int":
                return twoName.equalsIgnoreCase("Integer") || twoName.equalsIgnoreCase("int");
            case "Double":
            case "double":
                return twoName.equalsIgnoreCase("Double") || twoName.equalsIgnoreCase("double");
            case "Float":
            case "float":
                return twoName.equalsIgnoreCase("Float") || twoName.equalsIgnoreCase("float");
            default:
                return twoName.equalsIgnoreCase(oneName);
        }
    }


    private static ClassContainer getClassContainer(Class<?> clazz) {
        if (classContainer == null) {
            classContainer = ClassContainer.fromClass(clazz);
        }
        return classContainer;
    }
}
