package com.ryoryo.compiler.expression;

import static com.ryoryo.compiler.vm.CompiledCode.*;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.value.VBoolean;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class LogicalNot extends Expression {

    private Expression mExpr;
    
    public LogicalNot(Expression expr) {
        mExpr = expr;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        return VBoolean.fromBoolean(!mExpr.eval(env).asBoolean());
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        return mExpr.compile(env, insn(OpCode.LNOT, next));
    }

    @Override
    public String toString() {
        return "!" + mExpr;
    }
}
