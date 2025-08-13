package com.ryoryo.compiler.value;

import com.ryoryo.compiler.exception.TypeUnmatchedException;

public class VNumber extends Value {

    public static final VNumber ZERO = new VNumber(0);
    public static final VNumber ONE = new VNumber(1);
    public static final VNumber TWO = new VNumber(2);
    public static final VNumber THREE = new VNumber(3);
    public static final VNumber FOUR = new VNumber(4);
    public static final VNumber FIVE = new VNumber(5);
    public static final VNumber SIX = new VNumber(6);
    public static final VNumber SEVEN = new VNumber(7);
    public static final VNumber EIGHT = new VNumber(8);
    public static final VNumber NINE = new VNumber(9);
    public static final VNumber TEN = new VNumber(10);
    public static final VNumber NEGATIVE_ONE = new VNumber(-1);
    public static final VNumber NEGATIVE_TWO = new VNumber(-2);
    public static final VNumber NEGATIVE_THREE = new VNumber(-3);
    public static final VNumber NEGATIVE_FOUR = new VNumber(-4);
    public static final VNumber NEGATIVE_FIVE = new VNumber(-5);
    public static final VNumber NEGATIVE_SIX = new VNumber(-6);
    public static final VNumber NEGATIVE_SEVEN = new VNumber(-7);
    public static final VNumber NEGATIVE_EIGHT = new VNumber(-8);
    public static final VNumber NEGATIVE_NINE = new VNumber(-9);
    public static final VNumber NEGATIVE_TEN = new VNumber(-10);
    // Other numbers will be created dynamically
    
    private int mNum;

    private VNumber(int num) {
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
        return this.toString();
    }
    
    public static VNumber fromInt(int i) {
        switch (i) {
            case -10: return NEGATIVE_TEN;
            case -9: return NEGATIVE_NINE;
            case -8: return NEGATIVE_EIGHT;
            case -7: return NEGATIVE_SEVEN;
            case -6: return NEGATIVE_SIX;
            case -5: return NEGATIVE_FIVE;
            case -4: return NEGATIVE_FOUR;
            case -3: return NEGATIVE_THREE;
            case -2: return NEGATIVE_TWO;
            case -1: return NEGATIVE_ONE;
            case 0: return ZERO;
            case 1: return ONE;
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            case 5: return FIVE;
            case 6: return SIX;
            case 7: return SEVEN;
            case 8: return EIGHT;
            case 9: return NINE;
            case 10: return TEN;
            default: return new VNumber(i);
        }
    }
    
    public static VNumber fromString(String str) {
        return fromInt(Integer.parseInt(str));
    }
}