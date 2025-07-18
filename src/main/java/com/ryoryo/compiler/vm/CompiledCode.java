package com.ryoryo.compiler.vm;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompiledCode implements StackContent, CompiledCodeArg {

    private OpCode mOpCode;
    private CompiledCodeArg[] mArgs;
    private CompiledCode mNext;

    private CompiledCode(OpCode opCode, CompiledCode next, CompiledCodeArg... args) {
        mOpCode = opCode;
        mArgs = args;
        mNext = next;
    }

    public OpCode getOpCode() {
        return mOpCode;
    }

    public CompiledCodeArg[] getArgs() {
        return mArgs;
    }

    public CompiledCode getNext() {
        return mNext;
    }

    public static CompiledCode insn(OpCode opCode, CompiledCode next, CompiledCodeArg... args) {
        return new CompiledCode(opCode, next, args);
    }

    public static enum OpCode {
        HALT, REFER, CONSTANT, FUNCTIONAL, TEST, FRAME, ARGUMENT, APPLY, POP, RETURN, ADD, SUB, MULT, DIV, MOD, EQUAL, NOT_EQUAL, LESS_THAN, LESS_THAN_EQUAL, LANDS, LORS, LNOT
    }

    private String argJoin() {
        if (mArgs.length == 0) {
            return "";
        }
        return Stream.of(mArgs)
                .map(CompiledCodeArg::display)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String display() {
        var opName = mOpCode.name();
        if (mNext == null) {
            return String.format("%s(%s) -|", opName, argJoin());
        }
        return String.format("%s(%s) -> %s", opName, argJoin(), mNext.display());
    }
}
