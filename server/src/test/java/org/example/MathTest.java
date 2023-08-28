package org.example;

import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author huen on 2023/8/28 20:25
 */
public class MathTest {
    public static void main(String[] args) {
        for (int i = 0; i < 11 ; i++) {
            System.out.println(RandomUtil.randomNumbers(4));
        }
    }
}
