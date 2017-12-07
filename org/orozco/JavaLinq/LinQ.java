package JavaLinq;

import apptree.condition.Condition;
import apptree.condition.conditions.BasicCondition;
import sun.rmi.transport.ObjectTable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

import static JavaLinq.utils.CollectionUtils.NullOrEmpty;
import static JavaLinq.utils.CollectionUtils.inArray;

public abstract class LinQ<T> {
    static ClassContainer classContainer;

    public static <K> Object getFieldValueFromNameAndType(String fieldName, K value, Class clazz) {

        classContainer = getClassContainer(value.getClass());
        ClassProxy proxy = new ClassProxy(clazz);
        Field field = classContainer.getFieldMap().get(fieldName);
        if (field == null) {
            throw new RuntimeException(
                String.format("Class [%s] does not have field [%s]", clazz.getName(),
                              fieldName));
        }

        if (!classEquals(field.getType(), clazz)) {
            throw new RuntimeException(
                String.format("Field [%s] is not of Type [%s]", fieldName, clazz));
        }

        try {
            return field.get(value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    <K> Condition<T> getEqualsCondition(String fieldName, K value) {
        return BasicCondition.withCondition(conditionValue ->
                                                Objects.equals(
                                                    getFieldValueFromNameAndType(
                                                        fieldName,
                                                        conditionValue,
                                                        value.getClass()
                                                    ),
                                                    value));

    }

    <K> Condition<T> getNotEqualsCondition(String fieldName, K value) {
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
