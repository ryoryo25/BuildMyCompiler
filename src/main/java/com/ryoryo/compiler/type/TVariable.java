package com.ryoryo.compiler.type;

public class TVariable {
    
    private String mVarName;
    
    public TVariable(String varName) {
        mVarName = varName;
    }
    
    @Override
    public String toString() {
        return mVarName;
    }
}
