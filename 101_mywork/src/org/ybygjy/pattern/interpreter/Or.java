package org.ybygjy.pattern.interpreter;

public class Or extends Expression {
    private Expression left, right;
    
    public Or(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Context ctx) {
        return this.left.interpret(ctx) || this.right.interpret(ctx);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Or) {
            return this.left.equals(((Or)o).left)
                && this.right.equals(((Or)o).right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.toString()).hashCode();
    }

    @Override
    public String toString() {
        return "(" + this.left.toString()
            + " OR "
            + right.toString()
            + ")";
    }

}
