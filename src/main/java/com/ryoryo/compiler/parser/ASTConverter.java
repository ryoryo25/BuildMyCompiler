package com.ryoryo.compiler.parser;

import java.util.List;

import com.ryoryo.compiler.expression.Branch;
import com.ryoryo.compiler.expression.Constant;
import com.ryoryo.compiler.expression.Expression;
import com.ryoryo.compiler.expression.FunctionAbstraction;
import com.ryoryo.compiler.expression.FunctionApplication;
import com.ryoryo.compiler.expression.LocalBind;
import com.ryoryo.compiler.expression.LogicalNot;
import com.ryoryo.compiler.expression.Negative;
import com.ryoryo.compiler.expression.Variable;
import com.ryoryo.compiler.expression.binop.Addition;
import com.ryoryo.compiler.expression.binop.Division;
import com.ryoryo.compiler.expression.binop.Equal;
import com.ryoryo.compiler.expression.binop.LessThan;
import com.ryoryo.compiler.expression.binop.LessThanEqual;
import com.ryoryo.compiler.expression.binop.LogicalAnd;
import com.ryoryo.compiler.expression.binop.LogicalOr;
import com.ryoryo.compiler.expression.binop.Modulo;
import com.ryoryo.compiler.expression.binop.Multiplication;
import com.ryoryo.compiler.expression.binop.NotEqual;
import com.ryoryo.compiler.expression.binop.Subtraction;
import com.ryoryo.compiler.type.TVariable;
import com.ryoryo.compiler.value.VBoolean;
import com.ryoryo.compiler.value.VNumber;

public class ASTConverter implements ParserVisitor {

    @Override
    public Object visit(SimpleNode node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTProgram node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, null);
    }
    
    @Override
    public Object visit(ASTExpression node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, null);
    }
    
    @Override
    public Object visit(ASTLambdaExpression node, Object data) {
        Token argToken = (Token) node.jjtGetValue();
        Expression body = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        return new FunctionAbstraction(new TVariable(argToken.toString()), body);
    }
    
    @Override
    public Object visit(ASTLetExpression node, Object data) {
        Token varToken = (Token) node.jjtGetValue();
        Expression def = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        Expression body = (Expression) node.jjtGetChild(1).jjtAccept(this, null);
        return new LocalBind(new TVariable(varToken.toString()), def, body);
    }
    
    @Override
    public Object visit(ASTConditionalExpression node, Object data) {
        int n = node.jjtGetNumChildren();
        switch (n) {
            case 1:
                return node.jjtGetChild(0).jjtAccept(this, null); // Just return the single expression
            case 3:
                // if then else
                Expression condition = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
                Expression thenExpr = (Expression) node.jjtGetChild(1).jjtAccept(this, null);
                Expression elseExpr = (Expression) node.jjtGetChild(2).jjtAccept(this, null);
                return new Branch(condition, thenExpr, elseExpr);
            default:
                throw new IllegalArgumentException("Unexpected value: " + n);
        }
    }

    @Override
    public Object visit(ASTOrExpression node, Object data) {
        return visitBase(node, data, (op, e1, e2) -> {
            if ("||".equals(op)) {
                return new LogicalOr(e1, e2);
            } else {
                throw new IllegalArgumentException("Unexpected value: " + op);
            }
        });
    }

    @Override
    public Object visit(ASTAndExpression node, Object data) {
        return visitBase(node, data, (op, e1, e2) -> {
            if ("&&".equals(op)) {
                return new LogicalAnd(e1, e2);
            } else {
                throw new IllegalArgumentException("Unexpected value: " + op);
            }
        });
    }
    
    @Override
    public Object visit(ASTEqualityExpression node, Object data) {
        return visitBase(node, data, (op, e1, e2) -> {
            switch (op) {
                case "==":
                    return new Equal(e1, e2);
                case "!=":
                    return new NotEqual(e1, e2);
                default:
                    throw new IllegalArgumentException("Unexpected value: " + op);
            }
        });
    }

    @Override
    public Object visit(ASTRelationalExpression node, Object data) {
        return visitBase(node, data, (op, e1, e2) -> {
            switch (op) {
                case "<":
                    return new LessThan(e1, e2);
                case "<=":
                    return new LessThanEqual(e1, e2);
                case ">":
                    return new LessThan(e2, e1);
                case ">=":
                    return new LessThanEqual(e2, e1);
                default:
                    throw new IllegalArgumentException("Unexpected value: " + op);
            }
        });
    }

    @Override
    public Object visit(ASTAdditiveExpression node, Object data) {
        return visitBase(node, data, (op, e1, e2) -> {
            switch (op) {
                case "+":
                    return new Addition(e1, e2);
                case "-":
                    return new Subtraction(e1, e2);
                default:
                    throw new IllegalArgumentException("Unexpected value: " + op);
            }
        });
    }

    @Override
    public Object visit(ASTMultiplicativeExpression node, Object data) {
        return visitBase(node, data, (op, e1, e2) -> {
            switch (op) {
                case "*":
                    return new Multiplication(e1, e2);
                case "/":
                    return new Division(e1, e2);
                case "%":
                    return new Modulo(e1, e2);
                default:
                    throw new IllegalArgumentException("Unexpected value: " + op);
            }
        });
    }

    @Override
    public Object visit(ASTUnaryExpression node, Object data) {
        Token opToken = (Token) node.jjtGetValue();
        Expression expr = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        if (opToken == null) {
            return expr; // No unary operator, just return child
        }
        
        switch (opToken.toString()) {
            case "+":
                return expr; // Unary plus, no change
            case "-":
                return new Negative(expr); // Unary minus
            case "!":
                return new LogicalNot(expr);
            default:
                throw new IllegalArgumentException("Unexpected value: " + opToken.toString());
        }
    }

    @Override
    public Object visit(ASTApplicationExpression node, Object data) {
        int size = node.jjtGetNumChildren();
        Expression expr1 = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        
        for (int i = 1; i < size; i++) {
            Expression expr2 = (Expression) node.jjtGetChild(i).jjtAccept(this, null);
            expr1 = new FunctionApplication(expr1, expr2); // new expression
        }
        
        return expr1;
    }

    @Override
    public Object visit(ASTPrimaryExpression node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, null);
    }

    @Override
    public Object visit(ASTIdentifier node, Object data) {
        Token varToken = (Token) node.jjtGetValue();
        return new Variable(new TVariable(varToken.toString()));
    }

    @Override
    public Object visit(ASTBoolean node, Object data) {
        Token valueToken = (Token) node.jjtGetValue();
        return new Constant(VBoolean.fromString(valueToken.toString()));
    }
    
    @Override
    public Object visit(ASTInteger node, Object data) {
        Token valueToken = (Token) node.jjtGetValue();
        return new Constant(VNumber.fromString(valueToken.toString()));
    }

    @SuppressWarnings("unchecked")
    private <T> T autoCast(Object obj) {
        return (T) obj;
    }
    
    private Object visitBase(SimpleNode node, Object data, VisitBody body) {
        List<Token> ops = autoCast(node.jjtGetValue());
        int size = node.jjtGetNumChildren();
        Expression expr1 = (Expression) node.jjtGetChild(0).jjtAccept(this, null);
        
        int i = 1;
        for (Token op : ops) {
            if (i >= size) {
                break;
            }
            
            Expression expr2 = (Expression) node.jjtGetChild(i).jjtAccept(this, null);
            expr1 = body.visit(op.toString(), expr1, expr2); // new expression
            i ++;
        }
        
        return expr1;
    }

    private static interface VisitBody {
        Expression visit(String op, Expression expr1, Expression expr2);
    }
}
