package ru.job4j.bmb.conditions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RealModeCondition implements Condition {
    @Value("S{telegram.mode}")
    private String value;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !"fake".equals(value);
    }
}
