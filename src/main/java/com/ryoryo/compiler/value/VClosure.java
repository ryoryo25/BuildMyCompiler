package com.ryoryo.compiler.value;

import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.type.TVariable;

public class VClosure extends Value {

    private TVariable mArg;
    private Expression mBody;
    private Environment mEnv;

    public VClosure(TVariable arg, Expression body, Environment env) {
        mArg = arg;
        mBody = body;
        mEnv = env;
    }

    @Override
    public int asNumber() throws TypeUnmatchedException {
        throw new TypeUnmatchedException("needs numerical value");
    }

    @Override
    public Boolean asBoolean() throws TypeUnmatchedException {
        throw new TypeUnmatchedException("needs boolean value");
    }

    public TVariable getArg() {
        return mArg;
    }

    public Expression getBody() {
        return mBody;
    }

    public Environment getEnvironment() {
        return mEnv;
    }

    @Override
    public String toString() {
        // TODO
        return "Closure<arg: " + mArg + ">";
    }

    @Override
    public String display() {
        return toString();
    }
}
