package org.ybygjy.pattern.single;

/**
 * SingletonPattern
 * @author WangYanCheng
 * @version 2010-10-25
 */
public class SingletonPattern {

}

/**
 * 不支持多线程的\存在线程安全问题的singleton pattern
 * @author WangYanCheng
 * @version 2010-10-25
 */
class SingletonPatternPart1 {
    /** singleton pattern */
    private static SingletonPatternPart1 spp1 = null;

    /**
     * Constructor
     */
    private SingletonPatternPart1() {
    }

    /**
     * getInstance
     * @return spp1 {@link SingletonPatternPart1}
     */
    public static SingletonPatternPart1 getInstance() {
        if (null == spp1) {
            spp1 = new SingletonPatternPart1();
        }
        return spp1;
    }
}

/**
 * 双重检查机制，线程安全的singleton pattern
 * @author WangYanCheng
 * @version 2010-10-25
 */
class SingletonPatternPart2 {
    /** singleton pattern */
    private static volatile SingletonPatternPart2 spp2;

    /**
     * Constructor
     */
    private SingletonPatternPart2() {
    }

    /**
     * getInstance
     * @return ssp2 {@link SingletonPatternPart2}
     */
    public static SingletonPatternPart2 getInstance() {
        if (null == spp2) {
            synchronized (SingletonPatternPart2.class) {
                if (spp2 == null) {
                    spp2 = new SingletonPatternPart2();
                }
            }
        }
        return spp2;
    }
}

/**
 * 饿汉策略
 * @author WangYanCheng
 * @version 2010-10-25
 */
class SingletonPatternPart3 {
    /** singleton pattern */
    private static SingletonPatternPart3 spp3 = new SingletonPatternPart3();

    /**
     * Constructor
     */
    private SingletonPatternPart3() {
    }

    /**
     * getInstance
     * @return spp3 {@link SingletonPatternPart3}
     */
    public static SingletonPatternPart3 getInstance() {
        return spp3;
    }
}

/**
 * 一种利用JVM加载机制替化对象创建时的Double Checked Locking
 * @author WangYanCheng
 * @version 2010-12-7
 */
class InitializeOnDemand {
    /**
     * Constructor
     */
    private InitializeOnDemand() {
    }
    /**
     * InnerCompiler,只有InitalizeOnDemand被调用时，该类才会被初始化
     * @author WangYanCheng
     * @version 2010-12-7
     */
    private static class InnerClass {
        /** 真实实例 */
        public static InitializeOnDemand demand = new InitializeOnDemand();
    }

    /**
     * getInstance
     * @return {@link InitializeOnDemand}
     */
    public static InitializeOnDemand getInstance() {
        return InnerClass.demand;
    }
}
