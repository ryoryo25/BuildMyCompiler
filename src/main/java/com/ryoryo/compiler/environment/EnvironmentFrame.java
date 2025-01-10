package com.ryoryo.compiler.environment;

import java.util.HashMap;
import java.util.Map;

import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.Value;

public class EnvironmentFrame {
    
    private Map<String, Value> mVar2Val;
    private EnvironmentFrame mPrevFrame;
    
    public EnvironmentFrame(EnvironmentFrame prevFrame) {
        mVar2Val = new HashMap<String, Value>();
        mPrevFrame = prevFrame;
    }
    
    public EnvironmentFrame updateBind(TVariable var, Value val) {
        mVar2Val.put(var.toString(), val);
        return this;
    }
    
    public Value findBind(TVariable var) {
        return mVar2Val.get(var.toString());
    }
    
    public boolean containsBind(TVariable var) {
        return mVar2Val.containsKey(var.toString());
    }
    
    public EnvironmentFrame getPreviousFrame() {
        return mPrevFrame;
    }
}
