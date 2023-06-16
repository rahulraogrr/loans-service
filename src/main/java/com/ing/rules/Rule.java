package com.ing.rules;

public interface Rule<T> {
    boolean evaluateCondition(T request, String condition);
}

