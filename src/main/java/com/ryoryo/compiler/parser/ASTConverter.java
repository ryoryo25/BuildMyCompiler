package com.ryoryo.compiler.parser;

import java.util.List;

import com.ryoryo.compiler.expression.Constant;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.expression.Variable;
import com.ryoryo.compiler.expression.binop.Addition;
import com.ryoryo.compiler.expression.binop.Division;
import com.ryoryo.compiler.expression.binop.Modulo;
import com.ryoryo.compiler.expression.binop.Multiplication;
import com.ryoryo.compiler.expression.binop.Subtraction;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.VNumber;

public class ASTConverter implements ParserVisitor {

    @Override
    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTRoot node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, null);
    }

    @Override
    public Object visit(ASTExpression node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, null);
    }

    @Override
    public Object visit(ASTAdditiveExpression node, Object data) {
        List<Token> ops = autoCast(node.jjtGetValue());
        int size = node.jjtGetNumChildren();
        Expression expr1 = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        
        int i = 1;
        for (Token op : ops) {
            if (i >= size) {
                break;
            }
            
            Expression expr2 = (Expression) node.jjtGetChild(i).jjtAccept(this, null);
            switch (op.toString()) {
                case "+":
                    expr1 = new Addition(expr1, expr2);
                    break;
                case "-":
                    expr1 = new Subtraction(expr1, expr2);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + op.toString());
            }
            i ++;
        }
        
        return expr1;
    }

    @Override
    public Object visit(ASTMultiplicativeExpression node, Object data) {
        List<Token> ops = autoCast(node.jjtGetValue());
        int size = node.jjtGetNumChildren();
        Expression expr1 = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        
        int i = 1;
        for (Token op : ops) {
            if (i >= size) {
                break;
            }
            
            Expression expr2 = (Expression) node.jjtGetChild(i).jjtAccept(this, null);
            switch (op.toString()) {
                case "*":
                    expr1 = new Multiplication(expr1, expr2);
                    break;
                case "/":
                    expr1 = new Division(expr1, expr2);
                    break;
                case "%":
                    expr1 = new Modulo(expr1, expr2);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + op.toString());
            }
            i ++;
        }
        
        return expr1;
    }

    @Override
    public Object visit(ASTUnaryExpression node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, null);
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        Token varToken = (Token) node.jjtGetValue();
        return new Variable(new TVariable(varToken.toString()));
    }

    @Override
    public Object visit(ASTInteger node, Object data) {
        Token valueToken = (Token) node.jjtGetValue();
        return new Constant(new VNumber(Integer.parseInt(valueToken.toString())));
    }

    @SuppressWarnings("unchecked")
    private <T> T autoCast(Object obj) {
        return (T) obj;
    }
}
