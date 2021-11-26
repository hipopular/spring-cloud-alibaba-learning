package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


/**
 * Spring上下文辅助类
 * Created by huen on 2021/11/26 14:36
 */
@Slf4j
@Component
public class ApplicationContextTools implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
        ApplicationContextTools.context = context;
    }

    /**
     * 获取bean
     *
     * @param clazz class类
     * @param <T>   泛型
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }

        // 优先按type查
        T beanInstance = context.getBean(clazz);

        // 再按name查
        if (beanInstance == null) {
            String simpleName = clazz.getSimpleName();
            // 首字母小写
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            beanInstance = getBean(simpleName);

            if (beanInstance == null) {
                throw new RuntimeException("Component " + clazz + " can not be found in Spring Container");
            }
        }

        return beanInstance;
    }

    /**
     * 获取bean
     *
     * @param beanId beanId
     * @param <T>    泛型
     * @return T
     */
    public static <T> T getBean(String beanId) {
        if (beanId == null) {
            return null;
        }
        return (T) context.getBean(beanId);
    }

    /**
     * 获取bean
     *
     * @param beanName bean名称
     * @param clazz    class类
     * @param <T>      泛型
     * @return T
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null == beanName || "".equals(beanName.trim())) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        return context.getBean(beanName, clazz);
    }

    /**
     * 获取 ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        if (context == null) {
            return null;
        }
        return context;
    }

}
