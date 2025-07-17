package com.ryoryo.compiler.expression.binop;

import static com.ryoryo.compiler.vm.CompiledCode.*;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.value.VBoolean;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class LogicalAnd extends BinaryOperation {

    public LogicalAnd(Expression e1, Expression e2) {
        super(e1, e2);
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        return new VBoolean(mExpr1.eval(env).asBoolean() && mExpr2.eval(env).asBoolean()); // for short-circuit evaluation
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        // if not short-circuitted, the result of expr2 is the result of this expression
        var e2Compiled = mExpr2.compile(env, next);
        return mExpr1.compile(env, insn(OpCode.LANDS, e2Compiled, next));
    }

    @Override
    public String toString() {
        return "(" + mExpr1 + " && " + mExpr2 + ")";
    }
}
