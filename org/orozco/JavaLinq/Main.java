package JavaLinq;

import apptree.condition.Condition;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Test> sam = new ArrayList<>();
        sam.add(Test.test());
        List<Test> testCondition = JLinq.from(sam).Select()
                                        .eq("name", "testing")
                                        .OR()
                                        .eq("name", "sam")
                                        .OR()
                                        .eq("age", 12)
                                        .findList();
        for (Test test : testCondition) {
            System.out.println(test.name);
        }
    }
}
