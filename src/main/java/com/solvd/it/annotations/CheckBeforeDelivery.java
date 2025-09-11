package com.solvd.it.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Queue;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckBeforeDelivery {
}
