package org.ybygjy.basic.enumtype;

import org.ybygjy.basic.TestInterface;

/**
 * 学习enumeration类型
 * @author WangYanCheng
 * @version 2010-9-20
 */
public class EnumTest implements TestInterface {
    /**
     * {@inheritDoc}
     */
    public void doTest() {
        EnumTypeTest[] ett = EnumTypeTest.values();
        for (EnumTypeTest tmpETT : ett) {
            System.out.print(tmpETT.name() + "\t:\t" + tmpETT.toString() + "\n");
        }
        //System.out.println(EnumTypeTest.TEXT_TYPE.toString().equals("Text"));
        DirectionType[] dts = DirectionType.values();
        for (DirectionType dt : dts) {
            System.out.print(dt.name() + ":" + dt.toString() + "\n");
            System.out.println(DirectionType.NORTH == dt);
        }
    }

    /**
     * 定义测试用枚举类型
     * @author WangYanCheng
     * @version 2010-9-20
     */
    public enum EnumTypeTest {
        /** textType */
        TEXT_TYPE("Text"),
        /** binaryType */
        BINARY_TYPE("Binary");
        /** innerType */
        private String type;

        /**
         * Constructor
         * @param type type
         */
        private EnumTypeTest(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type.toString();
        }
    }

    /**
     * 方向
     * @author WangYanCheng
     * @version 2010-9-20
     */
    public enum DirectionType {
        /**方向{东,西,南,北,东北,西北,东南,西南}*/
        NORTH, SOUTH, EAST, NORTHEAST, SOUTHEAST, WEST, NORTHWEST, SOUTHWEST
    }
    public enum ExtDirType {
        NONE(0), NORTH(1), SOUTH(2),EAST(3),WEST(4),NORTHEAST(5),NORTHWEST(6),SOUTHEAST(7),SOUTHWEST(8);
        /** inner variable*/
        private int type;
        ExtDirType(int type) {
            this.type = type;
        }
    }
}
