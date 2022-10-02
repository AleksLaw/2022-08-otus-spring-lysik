package homework.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@ConditionalOnProperty(prefix = "AOP", name = "aspectUpperCase", havingValue = "true")
public class UpperCaseStringAspect {

    @Around(value = "execution(* homework.service.impl.IOServiceSystemImpl.outputString(..))")
    public void stringToUpperCase(ProceedingJoinPoint joinPoint) {

        Arrays.stream(joinPoint.getArgs())
                .findFirst()
                .ifPresent(o -> System.out.println(o.toString().toUpperCase()));
    }
}
