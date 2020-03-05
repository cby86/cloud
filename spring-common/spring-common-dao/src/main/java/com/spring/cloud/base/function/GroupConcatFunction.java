package com.spring.cloud.base.function;
import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.util.List;

public class GroupConcatFunction implements SQLFunction {
    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return true;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return StandardBasicTypes.STRING;
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        if (arguments.size() <=0) {
            throw new QueryException(new IllegalArgumentException(
                    "group_concat shoudl have one arg"));
        }
        if (arguments.size() == 1) {
            return "group_concat(" + arguments.get(0) + ")";
        }
        return "group_concat(distinct(" + arguments.get(0) + "))";
    }
}
