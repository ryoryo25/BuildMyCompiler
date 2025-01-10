package com.ryoryo.compiler.value;

import com.ryoryo.compiler.exception.TypeUnmatchedException;

public class VNumber extends Value {

    private int mNum;

    public VNumber(int num) {
        mNum = num;
    }

    @Override
    public int asNumber() {
        return mNum;
    }

    @Override
    public Boolean asBoolean() throws TypeUnmatchedException {
        throw new TypeUnmatchedException("needs boolean value");
    }

    @Override
    public String toString() {
        return String.valueOf(mNum);
    }

    @Override
    public String display() {
        return toString();
    }
}
