package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;

public class LocalBind extends Expression {

    private TVariable mVar;
    private Expression mDef;
    private Expression mBody;

    public LocalBind(TVariable var, Expression def, Expression body) {
        mVar = var;
        mDef = def;
        mBody = body;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        Value defVal = mDef.eval(env);

        Environment newEnv = env.extend();
        newEnv.addBind(mVar, defVal);

        return mBody.eval(newEnv);
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        // Compile local bind as function abstraction and application
        var expr = new FunctionApplication(
                new FunctionAbstraction(mVar, mBody),
                mDef
        );
        return expr.compile(env, next);
    }

    @Override
    public String toString() {
        return "let " + mVar + " = " + mDef + " in " + mBody;
    }
}
