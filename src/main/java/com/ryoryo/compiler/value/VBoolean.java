package com.ryoryo.compiler.value;

import com.ryoryo.compiler.exception.TypeUnmatchedException;

public class VBoolean extends Value {

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
