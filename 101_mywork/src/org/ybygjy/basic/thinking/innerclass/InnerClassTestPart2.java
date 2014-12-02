package org.ybygjy.basic.thinking.innerclass;

/**
 * 内部类测试
 * @author WangYanCheng
 * @version 2010-5-31
 */
public class InnerClassTestPart2 {
    public Destination dest(final String dest, final float price) {
        return new Destination() {
            private int cost;
            private String label = "dest";
            {
                cost = Math.round(price);
                if (this.cost > 1000) {
                    System.out.println("Over budget!");
                }
            }
            public String readLabel() {
                return this.label;
            }
        };
    }
    public static void main(String[] args) {
        InnerClassTestPart2 ictpInst = new InnerClassTestPart2();
        System.out.println(ictpInst.dest("Hello InnerCompiler World", 1001).readLabel());
    }
    interface Destination {
        String readLabel();
    }
}
