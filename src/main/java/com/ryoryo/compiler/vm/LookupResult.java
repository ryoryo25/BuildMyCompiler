package com.ryoryo.compiler.vm;

public class LookupResult implements CompiledCodeArg {

    private int mRib;
    private int mOffset;

    public LookupResult(int rib, int offset) {
        mRib = rib;
        mOffset = offset;
    }

    public int getRib() {
        return mRib;
    }

    public int getOffset() {
        return mOffset;
    }

    @Override
    public String display() {
        return String.format("(%d, %d)", mRib, mOffset);
    }
}
