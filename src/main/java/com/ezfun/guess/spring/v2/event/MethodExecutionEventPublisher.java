package com.ezfun.guess.spring.v2.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public class MethodExecutionEventPublisher {

    private List<MethodExecutionEventListener> listeners = new ArrayList<>();

    public void methodToMonitor() {
        MethodExecutionEvent event2Publish = new MethodExecutionEvent(this, "methodToMonitor");
        publishEvent(MethodExecutionStatus.BEGIN, event2Publish);
        publishEvent(MethodExecutionStatus.END, event2Publish);
    }

    protected void publishEvent(MethodExecutionStatus status, MethodExecutionEvent event) {
        List<MethodExecutionEventListener> copyListeners = new ArrayList<>(listeners);
        for (MethodExecutionEventListener listener : copyListeners) {
            if (MethodExecutionStatus.BEGIN.equals(status)) {
                listener.onMethodBegin(event);
            } else {
                listener.onMethodEnd(event);
            }
        }
    }

    public void addMethodExecutionEventListener(MethodExecutionEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(MethodExecutionEventListener listener) {
        this.listeners.remove(listener);
    }

    public void removeAllListener(){
        this.listeners.clear();
    }

    public static void main(String[] args){
        MethodExecutionEventPublisher eventPublisher = new MethodExecutionEventPublisher();
        eventPublisher.addMethodExecutionEventListener(new MethodExecutionEventListenerImpl());
        eventPublisher.methodToMonitor();
    }


}
