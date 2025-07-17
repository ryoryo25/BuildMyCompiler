package com.ryoryo.compiler.value;

import com.ryoryo.compiler.exception.TypeUnmatchedException;

public class VBoolean extends Value {

    public static final VBoolean TRUE = new VBoolean(true);
    public static final VBoolean FALSE = new VBoolean(false);
    
    private boolean mBool;

    public VBoolean(boolean bool) {
        mBool = bool;
    }

    @Override
    public int asNumber() throws TypeUnmatchedException {
        throw new TypeUnmatchedException("needs numerical value");
    }

    @Override
    public Boolean asBoolean() {
        return mBool;
    }

    @Override
    public String toString() {
        return String.valueOf(mBool);
    }

    @Override
    public String display() {
        return toString();
    }
}
