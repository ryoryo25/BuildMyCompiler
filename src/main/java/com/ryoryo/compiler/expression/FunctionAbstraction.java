package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.VClosure;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;
import com.ryoryo.compiler.vm.PopCount;

public class FunctionAbstraction extends Expression {

    private TVariable mArg;
    private Expression mBody;

    public FunctionAbstraction(TVariable arg, Expression body) {
        mArg = arg;
        mBody = body;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        return new VClosure(mArg, mBody, env);
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {

        var args = new TVariable[] { mArg };
        var bodyCompiled = mBody.compile(
                env.extend(args),
                CompiledCode.insn(OpCode.RETURN, null, new PopCount(args.length + 1)));

        return CompiledCode.insn(OpCode.FUNCTIONAL, next, bodyCompiled);
    }

    @Override
    public String toString() {
        return "(λ " + mArg + " . " + mBody + ")";
    }
}
