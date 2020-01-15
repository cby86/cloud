package com.spring.cloud.aop;

import com.spring.cloud.base.Command;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by chenbangyuan on 2019/11/16.
 * 对所有进行command传值的进行拦截检查,完成command依赖注入
 */
@Aspect
@Component
public class ModelResolver implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 对所有Controller类，方法含有@RequestMapping
     */
    @Around("execution(* com.*.cloud.controller..*.*(..)) && @annotation(annotation)")
    public Object advice(ProceedingJoinPoint joinPoint, RequestMapping annotation) throws Throwable {
        Object[] args = joinPoint.getArgs();
        advice(args);
        return joinPoint.proceed();
    }

    private void advice(Object[] args){
        if (args != null) {
            for (Object arg : args) {
                if (arg != null && arg instanceof Command) {
                    Command model = (Command) arg;
                    dependenceInject(model, model.getClass());
                }
            }
        }
    }

    public void dependenceInject(Command model, Class target) {
        if (!target.equals(Command.class)) {
            dependenceInject(model, target.getSuperclass());
            declaredFields(model, target.getDeclaredFields());
        } else {
            declaredFields(model, target.getDeclaredFields());
        }
    }

    private void declaredFields(Command model, Field[] declaredFields) {
        for (Field declaredField : declaredFields) {
            Autowired annotation = declaredField.getAnnotation(Autowired.class);
            if (annotation != null) {
                declaredField.setAccessible(true);
                try {
                    Object bean = null;
                    Map<String, Class> beans = (Map<String, Class>) applicationContext.getBeansOfType(declaredField.getType());
                    if (beans != null && !beans.isEmpty()) {
                        Iterator<Map.Entry<String, Class>> iterator = beans.entrySet().iterator();
                        bean = iterator.next().getValue();
                    }
                    if (bean == null) {
                        throw new IllegalArgumentException("注入错误");
                    }
                    declaredField.set(model, bean);
                } catch (IllegalAccessException e) {
                    log.error("依赖注入错误:" + model.getClass() + "." + declaredField.getName());
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
