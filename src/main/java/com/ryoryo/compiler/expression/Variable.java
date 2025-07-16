package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class Variable extends Expression {

    private TVariable mVar;

    public Variable(TVariable var) {
        mVar = var;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException {
        return env.findBind(mVar);
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next) throws VariableNotFoundException {
        return CompiledCode.insn(OpCode.REFER, next, env.lookup(mVar));
    }

    @Override
    public String toString() {
        return "Var(" + mVar + ")";
    }
}
