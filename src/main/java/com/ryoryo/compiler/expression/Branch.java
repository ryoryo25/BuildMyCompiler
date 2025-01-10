package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.value.VBoolean;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class Branch extends Expression {

    private Expression mCond;
    private Expression mThen;
    private Expression mElse;

    public Branch(Expression cond, Expression thenExpr, Expression elseExpr) {
        mCond = cond;
        mThen = thenExpr;
        mElse = elseExpr;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        VBoolean condition = (VBoolean) mCond.eval(env);

        if (condition.asBoolean()) {
            return mThen.eval(env);
        } else {
            return mElse.eval(env);
        }
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {

        var thenCompiled = mThen.compile(env, next);
        var elseCompiled = mElse.compile(env, next);
        return mCond.compile(
                env,
                CompiledCode.insn(OpCode.TEST, null, thenCompiled, elseCompiled));
    }
}
