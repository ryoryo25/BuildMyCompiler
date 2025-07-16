package com.ryoryo.compiler.expression;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;

public class LocalBindRecursive extends Expression {

    private TVariable mVar;
    private Expression mDef;
    private Expression mBody;

    public LocalBindRecursive(TVariable var, Expression def, Expression body) {
        mVar = var;
        mDef = def;
        mBody = body;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        Environment newEnv = env.extend();
        newEnv.addBind(mVar, null);

        Value defVal = mDef.eval(newEnv);

        newEnv.updateBind(mVar, defVal);

        return mBody.eval(newEnv);
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public String toString() {
        return "LETREC " + mVar + " = " + mDef + " IN " + mBody;
    }
}
