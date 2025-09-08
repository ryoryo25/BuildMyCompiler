package com.ryoryo.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

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
    
    public static boolean isDebug = false;
    
    public static void main(String[] args) {
        // Check options
        if (args.length > 0) {
            if (Stream.of(args).anyMatch(arg -> arg.equals("-d") || arg.equals("--debug"))) {
                isDebug = true;
            }
            
            if (Stream.of(args).anyMatch(arg -> arg.equals("-v") || arg.equals("--version"))) {
                System.out.println("My Tiny Interpreter/Compiler v1.0.1");
                return;
            }
            
            if (Stream.of(args).anyMatch(arg -> arg.equals("-h") || arg.equals("--help"))) {
                System.out.println("Usage: java -jar MyTinyCompiler.jar [-d|--debug|-v|--version|-h|--help]");
                System.out.println("Options:");
                System.out.println("  -d, --debug   Enable debug mode");
                System.out.println("  -v, --version Show version information");
                System.out.println("  -h, --help    Show this help message");
                return;
            }
        }
        
        // Main program
        System.out.println("Welcome to My Tiny Interpreter/Compiler!");
        if (isDebug) {
            System.out.println("Debug mode is ON.");
        }
        String type = null;
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                System.out.print("Run as [I]nterpreter/[C]ompiler? ");
                type = reader.readLine();
                if (type.equalsIgnoreCase("I") || type.equalsIgnoreCase("C")) {
                    break;
                } else {
                    System.out.println("Please enter 'I' or 'C'.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        while (true) {
            System.out.print("> ");
            var expr = Parser.parse(System.in);
            if (expr == null) {
                System.out.println("Bye.");
                break;
            }
            if (isDebug) {
                System.out.println("Parsed as: " + expr);
            }
            
            if (type.equalsIgnoreCase("I")) {
                eval(expr, Environment.EMPTY_ENVIRONMENT);
            } else if (type.equalsIgnoreCase("C")) {
                compileEval(expr);
            } else {
                throw new IllegalArgumentException("Invalid run type: " + type);
            }
        }
    }

    private static void eval(Expression expr, Environment env) {
        try {
            System.out.println(expr.eval(env));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void compileEval(Expression expr) {
        try {
            var program = expr.compile(new CompileEnvironment(null), CompiledCode.insn(OpCode.HALT, null));
            var vm = new VM();
            System.out.println(vm.execute(program));
        } catch (VariableNotFoundException | TypeUnmatchedException e) {
            e.printStackTrace();
        }
    }
}
