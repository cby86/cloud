package com.spring.cloud.base.dialect;
import com.spring.cloud.base.function.GroupConcatFunction;
import org.hibernate.dialect.MySQL57Dialect;

public class CustomerMysql5Dialect extends MySQL57Dialect{

    public CustomerMysql5Dialect() {
        super();
        registerFunction("group_contact",new GroupConcatFunction());
    }
}
