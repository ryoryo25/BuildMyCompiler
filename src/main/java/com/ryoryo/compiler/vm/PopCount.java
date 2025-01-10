package com.ryoryo.compiler.vm;

public class PopCount implements CompiledCodeArg {

    private int mCount;

    public PopCount(int count) {
        mCount = count;
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public String display() {
        return String.valueOf(mCount);
    }
}
