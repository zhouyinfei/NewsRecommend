package com.wjj.jieba;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
 
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
 
public class SortTest {
 
    public static void main(String[] args) throws Exception {
 
        // 创建一个字符串为Key，数字为值的map
        Map<String, Integer> budget = new HashMap<>();
        budget.put("clothes", 120);
        budget.put("grocery", 150);
        budget.put("transportation", 100);
        budget.put("utility", 130);
        budget.put("rent", 1150);
        budget.put("miscellneous", 90);
        System.out.println("排序前: " + budget);
 
        // 按值排序 升序
        Map<String, Integer> sorted = budget
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
 
        System.out.println("升序按值排序后的map: " + sorted);
 
        // 按值排序降序
        sorted = budget
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
 
        System.out.println("降序按值排序后的map: " + sorted);
    }
 
 
}
