package com.delacasa.auth.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = {})
public @interface BasicVarchar {

}
