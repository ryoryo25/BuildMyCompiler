package com.ryoryo.compiler.environment;

import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.vm.LookupResult;

public class CompileEnvironment {

    private final CompileEnvironmentFrame mTopFrame;

    public CompileEnvironment(CompileEnvironmentFrame frame) {
        mTopFrame = frame;
    }

    public LookupResult lookup(TVariable var) throws VariableNotFoundException {
        var frame = mTopFrame;
        int rib = 0;

        while (frame != null) {
            int offset = frame.offsetOf(var);
            if (offset >= 0) { // found
                return new LookupResult(rib, offset);
            }
            frame = frame.getPreviousFrame();
            rib++;
        }

        // variable not found
        throw new VariableNotFoundException(var.toString());
    }

    public CompileEnvironmentFrame getTopFrame() {
        return mTopFrame;
    }

    public CompileEnvironment extend(TVariable[] vars) {
        return new CompileEnvironment(new CompileEnvironmentFrame(vars, mTopFrame));
    }
}
