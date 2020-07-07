package com.ezfun.guess.spring.v2.event;

import com.ezfun.guess.spring.v2.event.sun.MethodExecutionStatus;
import org.springframework.context.ApplicationEvent;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public class MethodExecutionEvent extends ApplicationEvent {
    private static final long serialVersionUID = -2635599187099841521L;

    private String methodName;

    private MethodExecutionStatus methodExecutionStatus;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName, MethodExecutionStatus methodExecutionStatus) {
        super(source);
        this.methodName = methodName;
        this.methodExecutionStatus = methodExecutionStatus;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodExecutionStatus getMethodExecutionStatus() {
        return methodExecutionStatus;
    }

    public void setMethodExecutionStatus(MethodExecutionStatus methodExecutionStatus) {
        this.methodExecutionStatus = methodExecutionStatus;
    }
}
