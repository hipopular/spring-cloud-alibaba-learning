package org.example;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by huen on 2021/10/27 11:02
 */
public class MybatisGenerator {

    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * 运行 jar
         */
        Runtime.getRuntime()
                .exec("java -jar /Users/Hoon/Documents/workspace/popular/server/src/test/resources/lib/mybatis-plus-code-generator-3.5.1.3.jar");

        TimeUnit.MILLISECONDS.sleep(5500);
        /**
         * 打开网页
         */
        Runtime.getRuntime()
                .exec(new String[] {"/usr/bin/open", "-a", "/Applications/Google Chrome.app", "http:/localhost:8080"});

    }

}