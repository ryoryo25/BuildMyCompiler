package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class Constant extends Expression {

    private Value mVal;

    public Constant(Value val) {
        mVal = val;
    }

    @Override
    public Value eval(Environment env) {
        return mVal;
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next) {
        return CompiledCode.insn(OpCode.CONSTANT, next, mVal);
    }
}
