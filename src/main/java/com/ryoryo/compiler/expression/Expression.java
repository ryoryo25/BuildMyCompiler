package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;

public abstract class Expression {
    public abstract Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException;

    public abstract CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException;
    
    public abstract String toString();
}
