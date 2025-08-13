package com.ryoryo.compiler.expression;

import static com.ryoryo.compiler.vm.CompiledCode.*;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.value.VNumber;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class Negative extends Expression {

    private Expression mExpr;
    
    public Negative(Expression expr) {
        mExpr = expr;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        return VNumber.fromInt(-mExpr.eval(env).asNumber());
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        return mExpr.compile(env, insn(OpCode.NEG, next));
    }

    @Override
    public String toString() {
        return "-" + mExpr;
    }
}
