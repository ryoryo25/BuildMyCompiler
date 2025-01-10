package com.ryoryo.compiler.environment;

import com.ryoryo.compiler.type.TVariable;

public class CompileEnvironmentFrame {

    private TVariable[] mVars;
    private CompileEnvironmentFrame mPrevFrame;

    public CompileEnvironmentFrame(TVariable[] vars, CompileEnvironmentFrame prevFrame) {
        mVars = vars;
        mPrevFrame = prevFrame;
    }

    public int offsetOf(TVariable var) {
        for (int i = 0; i < mVars.length; i++) {
            if (var.toString().equals(mVars[i].toString())) {
                return i;
            }
        }
        return -1;
    }

    public CompileEnvironmentFrame getPreviousFrame() {
        return mPrevFrame;
    }
}
