package com.ryoryo.compiler.environment;

import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.Value;

public class Environment {

    public static final Environment EMPTY_ENVIRONMENT = new Environment(new EnvironmentFrame(null));

    private final EnvironmentFrame mTopFrame;

    public Environment(EnvironmentFrame frame) {
        mTopFrame = frame;
    }

    public Environment addBind(TVariable var, Value val) {
        mTopFrame.updateBind(var, val);
        return this;
    }

    /**
     * assign
     * @param var
     * @param val
     */
    public Environment updateBind(TVariable var, Value val) throws VariableNotFoundException {
        var frame = mTopFrame;
        while (frame != null) {
            if (frame.containsBind(var)) {
                frame.updateBind(var, val);
                return this;
            }
            frame = frame.getPreviousFrame();
        }
        throw new VariableNotFoundException(var.toString());
    }

    public Value findBind(TVariable var) throws VariableNotFoundException {
        var frame = mTopFrame;
        while (frame != null) {
            Value val = frame.findBind(var);
            if (val != null) {
                return val;
            }
            frame = frame.getPreviousFrame();
        }

        // variable not found
        throw new VariableNotFoundException(var.toString());
    }

    public EnvironmentFrame getTopFrame() {
        return mTopFrame;
    }

    public Environment extend() {
        return new Environment(new EnvironmentFrame(mTopFrame));
    }
}
