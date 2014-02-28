package org.ybygjy.pattern.interpreter;

public class Client {
    private Context ctx;
    private Expression exp;
    public Client() {
        ctx = new Context();
    }
    public void doWork() {
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Constant c = new Constant(true);
        ctx.assign(x, false);
        ctx.assign(y, true);
        exp = new Or(new And(y, x), new And(x, new Not(x)));
        System.out.println("x=" + x.interpret(ctx));
        System.out.println("y=" + y.interpret(ctx));
        System.out.println(exp.toString() + "=" + exp.interpret(ctx));
    }
    public static void main(String[] args) {
        new Client().doWork();
    }
}
