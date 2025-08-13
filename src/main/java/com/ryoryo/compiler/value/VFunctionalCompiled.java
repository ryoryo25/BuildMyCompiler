package com.ryoryo.compiler.value;

import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.vm.Address;
import com.ryoryo.compiler.vm.CompiledCode;

public class VFunctionalCompiled extends Value {

    private CompiledCode mBody;
    private Address mLink;

    public VFunctionalCompiled(CompiledCode body, Address link) {
        mBody = body;
        mLink = link;
    }

    @Override
    public int asNumber() throws TypeUnmatchedException {
        throw new TypeUnmatchedException("needs numerical value");
    }

    @Override
    public Boolean asBoolean() throws TypeUnmatchedException {
        throw new TypeUnmatchedException("needs boolean value");
    }

    public CompiledCode getBody() {
        return mBody;
    }

    public Address getLink() {
        return mLink;
    }

    @Override
    public String toString() {
        // TODO
        return "ClosureCompiled<" + mBody.display() + ">";
    }

    @Override
    public String display() {
        return this.toString();
    }
}
