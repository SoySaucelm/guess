package com.ezfun.guess.spring.v2.event.sun;

import java.util.EventObject;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public class MethodExecutionEvent extends EventObject {
    private static final long serialVersionUID = -2635599187099841521L;

    private String methodName;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName) {
        super(source);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
