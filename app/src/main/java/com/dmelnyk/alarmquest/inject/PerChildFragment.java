package com.dmelnyk.alarmquest.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by d264 on 1/2/18.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerChildFragment {
}