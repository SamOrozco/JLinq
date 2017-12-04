package JavaLinq;

import apptree.condition.Condition;
import apptree.condition.ConditionStatement;
import apptree.condition.conditions.BasicCondition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class LinQExpressionBuilder<T> {
    Collection<T> initValue;

    public LinQBuilder<T> Select() {
        return new LinQBuilder<>();
    }

    public class LinQBuilder<T> extends LinQ<T> {
        private List<Condition<T>> conditionalList;


        public LinQBuilder<T> eq(String fieldName, String value) {
            Condition<T> condition = BasicCondition.<T>withCondition(conditionValue ->
                                                                         Objects.equals(
                                                                             getFieldValueFromNameAndType(
                                                                                 fieldName,
                                                                                 conditionValue,
                                                                                 String.class),
                                                                             value));
            getConditionalList().add(condition);
            return this;
        }


        public List<T> findList() {
            return pruneValues((Collection<T>) initValue,
                               new ConditionStatement<T>(conditionalList));
        }

        private List<T> pruneValues(Collection<T> values, Condition<T> condition) {
            List<T> returnList = new ArrayList<>();
            for (T val : values) {
                if (condition.evaluate(val)) {
                    returnList.add(val);
                }
            }
            return returnList;
        }


        private Condition<T> build() {
            return new ConditionStatement<T>(conditionalList);
        }

        private List<Condition<T>> getConditionalList() {
            if (conditionalList == null) {
                conditionalList = new ArrayList<Condition<T>>();
            }
            return conditionalList;
        }
    }
}
