package com.ryoryo.compiler.vm;

public class Address implements StackContent {

    public static final Address ZERO = new Address(0);

    private int mValue;

    public Address(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }

    public Address add(int adder) {
        return new Address(mValue + adder);
    }

    public Address sub(int diff) {
        return new Address(mValue - diff);
    }

    @Override
    public String display() {
        return "Address = " + mValue;
    }
}
