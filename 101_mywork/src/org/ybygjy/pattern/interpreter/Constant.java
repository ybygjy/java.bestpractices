package org.ybygjy.pattern.interpreter;

public class Constant extends Expression {
    private boolean value;
    
    public Constant(boolean value) {
        this.value = value;
    }

    @Override
    public boolean interpret(Context ctx) {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Constant) {
            return this.value == ((Constant) o).value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.toString()).hashCode();
    }

    @Override
    public String toString() {
        return new Boolean(this.value).toString();
    }

}
