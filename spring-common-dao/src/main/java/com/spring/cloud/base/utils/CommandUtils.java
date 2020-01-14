package com.spring.cloud.base.utils;

import com.spring.cloud.base.BaseEntity;
import com.spring.cloud.base.Command;
import com.spring.cloud.base.Entity;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * User: chenby
 * Date: 13-10-17
 * Time: 下午3:57
 */
public class CommandUtils {

    public static <T extends Command<D>, D extends BaseEntity> List<T> toCommands(List<D> domains, Class<T> aClass) {
        List<T> objects = new ArrayList<T>(domains.size());
        for (D d : domains) {
            try {
                T command = aClass.newInstance();
                command.fromDomain(d);
                objects.add(command);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    /**
     * BigDecimal类型转换
     * @param object
     * @return
     */
    public static BigDecimal formatBigDecimal(Object object){
        if (object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof Float || object instanceof BigDecimal){
            return new BigDecimal(object.toString());
        }
        return  null;
    }

    /**
     * 转换BigDecimal
     * @param value
     * @return
     */
    public static String formatAvailableBigDecimal(BigDecimal value){
        if (value == null){
            return "0.00";
        }
        return value.setScale(2).toString();
    }

}
