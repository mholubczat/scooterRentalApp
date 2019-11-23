package com.example.scooterRentalApp.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectLogger{

    @Before("@within(org.springframework.transaction.annotation.Transactional)")
    public void rentScooterAdvice(){
        System.out.println("asd");
    }


}
