package JavaLinq;

import apptree.condition.Condition;
import apptree.condition.ConditionClause;
import apptree.condition.ConditionStatement;
import apptree.condition.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class LinQExpressionBuilder<T> {
    Collection<T> initValue;

    public LinQBuilder<T> Select() {
        return new LinQBuilder<>();
    }

    public class LinQBuilder<T> extends LinQ<T> {
        private List<Condition<T>> conditionalList;
        private boolean combineNextAsOR;


        public <K> LinQBuilder<T> eq(String fieldName, K value) {
            Condition<T> condition = getEqualsCondition(fieldName, value);
            if (combineNextAsOR) {
                combineNextAsOR = false;
                addORCondition(getConditionalList(), condition);
            } else {
                getConditionalList().add(condition);
            }
            return this;
        }

        public <K> LinQBuilder<T> notEq(String fieldName, K value) {
            Condition<T> condition = getNotEqualsCondition(fieldName, value);
            if (combineNextAsOR) {
                combineNextAsOR = false;
                addORCondition(getConditionalList(), condition);
            } else {
                getConditionalList().add(condition);
            }
            return this;
        }

//        public <K> LinQBuilder<T> in(String fieldName, Collection<K> collection) {
//            Condition<T> condition = getInCondition(fieldName, collection);
//            return this;
//        }
//
//        public <K> LinQBuilder<T> in(String fieldName, K... collection) {
//            Condition<T> condition = getInCondition(fieldName, collection);
//            return this;
//        }

        public LinQBuilder<T> OR() {
            combineNextAsOR = true;
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

        private void addORCondition(List<Condition<T>> conditions,
                                    Condition<T> orCondition) {
            newConditionList(
                new ConditionClause<>(new ConditionStatement<T>(conditions), orCondition,
                                      Operator.OR));
        }


        private void newConditionList(Condition<T>... conditions) {
            this.conditionalList = new ArrayList<>(Arrays.asList(conditions));
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
