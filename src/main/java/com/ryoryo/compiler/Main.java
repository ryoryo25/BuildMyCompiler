package com.ryoryo.compiler;

import com.ryoryo.compiler.environment.CompileEnvironment;
import com.ryoryo.compiler.environment.Environment;
import com.ryoryo.compiler.exception.TypeUnmatchedException;
import com.ryoryo.compiler.exception.VariableNotFoundException;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.parser.Parser;
import com.ryoryo.compiler.vm.CompiledCode;
import com.ryoryo.compiler.vm.CompiledCode.OpCode;
import com.ryoryo.compiler.vm.VM;

public class Main {
    public static void main(String[] args) {
        //        var expr = new Equal(
        //                new Variable(new TVariable("b")),
        //                new Constant(new VNumber(88)));
        //        var env = Environment.EMPTY_ENVIRONMENT
        //                .addBind(new TVariable("a"), new VNumber(99))
        //                .addBind(new TVariable("b"), new VNumber(88));
        //        eval(expr, env);

        System.out.print("> ");
        var expr = Parser.parse(System.in);
        System.out.println(expr);
//        var expr = new LessThanEqual(
//                new Constant(new VNumber(2)),
//                new Constant(new VNumber(2)));
//        compileEval(expr);
    }

    public static void eval(Expression expr, Environment env) {
        try {
            System.out.println(expr.eval(env));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compileEval(Expression expr) {
        try {
            var program = expr.compile(new CompileEnvironment(null), CompiledCode.insn(OpCode.HALT, null));
            var vm = new VM();
            System.out.println(vm.execute(program));
        } catch (VariableNotFoundException | TypeUnmatchedException e) {
            e.printStackTrace();
        }
    }
}
