package ru.job4j.bmb.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* ru.job4j.bmb.services.*.*(..))")
    private void serviceLayer() { }

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Вызов метода: " + joinPoint.getSignature().getName());
        Arrays.stream(joinPoint.getArgs()).forEach(
                arg -> System.out.println("Значение аргумента: " + arg.toString())
        );
    }
}
