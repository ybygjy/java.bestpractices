package org.ybygjy.basic.thinking.innerclass;
/**
 * 内部类测试
 * @author WangYanCheng
 * @version 2010-5-31
 */
public class InnerClassTestPart3LinkRef {
    private interface Selector {
        boolean end();
        Object current();
        int next();
    }
    private Object[] objects = null;
    private int next = 0;
    public InnerClassTestPart3LinkRef(int size) {
        this.objects = new Object[size];
    }
    public void add(Object x) {
        if (next < objects.length) {
            objects[next++] = x;
        }
    }
    private class SelectorImpl implements Selector {
        private int i = 0;
        public Object current() {
            return objects[i];
        }
        public boolean end() {
            return (i == objects.length);
        }
        public int next() {
            i = i < objects.length ? i + 1 : i;
            return i;
        }
    }
    public Selector getSelector( ){
        return new SelectorImpl();
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        int index = 10;
        InnerClassTestPart3LinkRef ictp3lrInst = new InnerClassTestPart3LinkRef(index);
        for (int i = 0; i < index; i++) {
            ictp3lrInst.add(i);
        }
        Selector siInst = ictp3lrInst.getSelector();
        while (!siInst.end()) {
            System.out.print(siInst.current() + "\t");
            System.out.println("siInst.next::" + siInst.next());
        }
    }
}
