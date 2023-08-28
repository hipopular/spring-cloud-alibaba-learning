package org.example;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huen on 2023/8/9 8:41
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, Map<String,Integer>> map = new HashMap<>();
        Map<String, Integer> map1 = new ConcurrentHashMap<>();
        map1.put("a", 1);
        map1.put("b", 2);
        map.put("1", map1);


        Map<String, Map<String,Integer>> map2 = new HashMap<>();
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("a", 3);
        map3.put("c", 4);

        map2.put("1", map3);
        map2.put("2", map3);
        map2.put("3", map1);

        Set<String> allSet = new HashSet<>();
        allSet.addAll(map.keySet());
        allSet.addAll(map2.keySet());


        Map<String, Map<String,Integer>> mergeMap = new HashMap<>();

        allSet.forEach(e -> {
            Set<String> childSet = new HashSet<>();
            Map<String, Integer> m1 = MapUtil.isEmpty(map.get(e)) ? Maps.newHashMap() : map.get(e);
            Map<String, Integer> m2 = MapUtil.isEmpty(map2.get(e)) ? Maps.newHashMap() : map2.get(e);

            if(m1 != null) {
                childSet.addAll(m1.keySet());
            }
            if(m2 != null) {
                childSet.addAll(m2.keySet());
            }
            Map<String, Integer> tempMap = new HashMap<>();

            for (String i : childSet) {
                Integer i1 = m1.get(i);
                Integer i2 = m2.get(i);
                if (i1 != null  && i2 != null){
                    tempMap.put(i,i1+i2);
                }
                else if (i1 != null){
                    tempMap.put(i,i1);
                }
                else if (i2 != null){
                    tempMap.put(i,i2);
                }
            }
            mergeMap.put(e,tempMap);
        });
        System.out.println(mergeMap);

    }
}
