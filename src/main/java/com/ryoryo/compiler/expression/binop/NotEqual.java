package com.ryoryo.compiler.expression.binop;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.value.VBoolean;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class NotEqual extends BinaryOperation {

    public NotEqual(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        var v1 = mExpr1.eval(env).asNumber();
        var v2 = mExpr2.eval(env).asNumber();
        return new VBoolean(v1 != v2);
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        return compileBase(OpCode.NOT_EQUAL, env, next);
    }

    @Override
    public String toString() {
        return "(" + mExpr1 + " != " + mExpr2 + ")";
    }
}
