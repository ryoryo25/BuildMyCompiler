package com.ryoryo.compiler.expression;

import static com.ryoryo.compiler.vm.CompiledCode.*;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.VClosure;
import com.ryoryo.compiler.value.Value;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;

public class FunctionApplication extends Expression {

    private Expression mOperator;
    private Expression mOperand;

    public FunctionApplication(Expression operator, Expression operand) {
        mOperator = operator;
        mOperand = operand;
    }

    @Override
    public Value eval(Environment env) throws VariableNotFoundException, TypeUnmatchedException {
        VClosure f = (VClosure) mOperator.eval(env);
        TVariable param = f.getArg();
        Expression body = f.getBody();
        Environment capturedEnv = f.getEnvironment();

        Value arg = mOperand.eval(env);

        Environment newEnv = capturedEnv.extend();
        newEnv.addBind(param, arg);

        return body.eval(newEnv);
    }

    @Override
    public CompiledCode compile(CompileEnvironment env, CompiledCode next)
            throws VariableNotFoundException, TypeUnmatchedException {

        var funcCompiled = mOperator.compile(env, insn(OpCode.APPLY, null));
        var argCompiled = mOperand.compile(env, insn(OpCode.ARGUMENT, funcCompiled));

        return insn(OpCode.FRAME, next, argCompiled);
    }

    @Override
    public String toString() {
        return mOperator + " " + mOperand;
    }
}
