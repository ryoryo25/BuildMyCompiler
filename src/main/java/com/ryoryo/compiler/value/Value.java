package com.ryoryo.compiler.value;

import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.vm.CompiledCodeArg;
import com.ryoryo.compiler.vm.StackContent;

public abstract class Value implements StackContent, CompiledCodeArg {

    public abstract int asNumber() throws TypeUnmatchedException;

    public abstract Boolean asBoolean() throws TypeUnmatchedException;
}
