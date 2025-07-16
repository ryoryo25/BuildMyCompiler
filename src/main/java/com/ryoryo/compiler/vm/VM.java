package com.ryoryo.compiler.vm;

import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.value.VBoolean;
import com.ryoryo.compiler.value.VFunctionalCompiled;
import com.ryoryo.compiler.value.VNumber;
import com.ryoryo.compiler.value.Value;

public class VM {
    private static final int STACK_SIZE = 10;

    private final StackContent[] mStack = new StackContent[STACK_SIZE];

    public VM() {
    }

    private Address push(Address sp, StackContent... objs) {
        int spInt = sp.getValue();
        int i = 0;
        for (StackContent obj : objs) {
            mStack[spInt + i] = obj;
            i++;
        }
        return sp.add(i);
    }

    private Address indexOffset(Address sp, int offset) {
        return sp.sub(offset + 1);
    }

    private StackContent indexRef(Address sp, int offset) {
        return mStack[indexOffset(sp, offset).getValue()];
    }

    private Address findLink(int rib, Address frm) {
        while (rib != 0) {
            rib--;
            frm = (Address) indexRef(frm, -1);
        }

        return frm;
    }

    private void printStack(Address sp) {
        for (int i = 0; i < STACK_SIZE; i++) {
            System.out.println(
                    String.format(
                            "%s %2d: %s",
                            sp.getValue() == i ? "->" : "  ",
                            i,
                            mStack[i] == null ? "null" : mStack[i].display()));
        }
    }

    private void printDebug(Value acc, CompiledCode exp, Address sp) {
        System.out.println("acc = " + (acc == null ? "null" : acc.display()));
        System.out.println("exp = " + exp.display());
        printStack(sp);
        System.out.println("---");
    }

    public Value execute(CompiledCode program) throws TypeUnmatchedException {
        Value acc = null; // accumulator
        CompiledCode exp = program; // current expression
        Address frm = Address.ZERO; // frame pointer
        Address sp = Address.ZERO; // stack pointer

        while (true) {
            printDebug(acc, exp, sp);

            switch (exp.getOpCode()) {
                default:
                case HALT:
                    return acc;
                case REFER:
                    var ref = (LookupResult) exp.getArgs()[0];
                    acc = (Value) indexRef(findLink(ref.getRib(), frm), ref.getOffset());
                    exp = exp.getNext();
                    break;
                case CONSTANT:
                    acc = (Value) exp.getArgs()[0];
                    exp = exp.getNext();
                    break;
                case FUNCTIONAL:
                    acc = new VFunctionalCompiled(
                            (CompiledCode) exp.getArgs()[0],
                            frm);
                    exp = exp.getNext();
                    break;
                case TEST:
                    if (acc.asBoolean()) {
                        exp = (CompiledCode) exp.getArgs()[0];
                    } else {
                        exp = (CompiledCode) exp.getArgs()[1];
                    }
                    break;
                case FRAME:
                    sp = push(sp, frm, exp.getNext());
                    exp = (CompiledCode) exp.getArgs()[0];
                    break;
                case ARGUMENT:
                    sp = push(sp, acc);
                    exp = exp.getNext();
                    break;
                case APPLY:
                    var fun = (VFunctionalCompiled) acc;
                    sp = push(sp, fun.getLink());
                    exp = fun.getBody();
                    break;
                case POP:
                    sp = sp.sub(((PopCount) exp.getArgs()[0]).getCount());
                    exp = exp.getNext();
                    break;
                case RETURN:
                    sp = sp.sub(((PopCount) exp.getArgs()[0]).getCount());
                    exp = (CompiledCode) indexRef(sp, 0);
                    frm = (Address) indexRef(sp, 1);
                    sp = sp.sub(2);
                    break;
                case ADD:
                    acc = new VNumber(((Value) indexRef(sp, 0)).asNumber() + acc.asNumber());
                    exp = exp.getNext();
                    break;
                case SUB:
                    acc = new VNumber(((Value) indexRef(sp, 0)).asNumber() - acc.asNumber());
                    exp = exp.getNext();
                    break;
                case MULT:
                    acc = new VNumber(((Value) indexRef(sp, 0)).asNumber() * acc.asNumber());
                    exp = exp.getNext();
                    break;
                case DIV:
                    acc = new VNumber(((Value) indexRef(sp, 0)).asNumber() / acc.asNumber());
                    exp = exp.getNext();
                    break;
                case MOD:
                    acc = new VNumber(((Value) indexRef(sp, 0)).asNumber() % acc.asNumber());
                    exp = exp.getNext();
                    break;
                case EQUAL:
                    acc = new VBoolean(((Value) indexRef(sp, 0)).asNumber() == acc.asNumber());
                    exp = exp.getNext();
                    break;
                case NOT_EQUAL:
                    acc = new VBoolean(((Value) indexRef(sp, 0)).asNumber() != acc.asNumber());
                    exp = exp.getNext();
                    break;
                case LESS_THAN:
                    acc = new VBoolean(((Value) indexRef(sp, 0)).asNumber() < acc.asNumber());
                    exp = exp.getNext();
                    break;
                case LESS_THAN_EQUAL:
                    acc = new VBoolean(((Value) indexRef(sp, 0)).asNumber() <= acc.asNumber());
                    exp = exp.getNext();
                    break;
            }
        }
    }
}
