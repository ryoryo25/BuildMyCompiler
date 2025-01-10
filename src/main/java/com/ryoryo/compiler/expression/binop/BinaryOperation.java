package com.ryoryo.compiler.expression.binop;

import static com.ryoryo.compiler.vm.CompiledCode.*;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;
import com.ryoryo.compiler.vm.PopCount;

public abstract class BinaryOperation extends Expression {

    protected Expression mExpr1;
    protected Expression mExpr2;

    public BinaryOperation(Expression e1, Expression e2) {
        mExpr1 = e1;
        mExpr2 = e2;
    }

    public CompiledCode compileBase(OpCode opCode, CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        var returnCompiled = insn(OpCode.POP, next, new PopCount(1));
        var e2Compiled = mExpr2.compile(env, insn(opCode, returnCompiled));
        return mExpr1.compile(env, insn(OpCode.ARGUMENT, e2Compiled));
    }
}
