package com.ezfun.guess.akka;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author SoySauce
 * @date 2020/1/3
 */
public final class ImmutableMessage {
    private final int sequenceNumber;
    private final List<String> values;

    public ImmutableMessage(int sequenceNumber, List<String> values) {
        this.sequenceNumber = sequenceNumber;
        this.values = values;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public List<String> getValues() {
        return values;
    }

    public static void main(String[] args){
        ImmutableList<String> of = ImmutableList.of("22", "33");
        ArrayList<String> list = Lists.newArrayList();
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        ImmutableMessage im = new ImmutableMessage(88, of);
        System.out.println(im.getSequenceNumber());
        list.add("a");
        list.add("b");
        List<String> values = im.getValues();
        values.add("c");
        System.out.println(im.getValues());

    }
}
