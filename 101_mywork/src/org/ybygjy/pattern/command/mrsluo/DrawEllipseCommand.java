package org.ybygjy.pattern.command.mrsluo;

/**
 * 绘制椭圆
 * @author WangYanCheng
 * @version 2011-8-1
 */
public class DrawEllipseCommand implements ICommand {
    private String grapObj;
    /**
     * {@inheritDoc}
     */
    public String getObject() {
        return grapObj;
    }

    /**
     * {@inheritDoc}
     */
    public void redo() {
        System.out.println("绘制椭圆｛" + getObject() + "｝");
    }

    /**
     * {@inheritDoc}
     */
    public void setObject(String obj) {
        this.grapObj = obj;
    }

    /**
     * {@inheritDoc}
     */
    public void undo() {
        System.out.println("擦除椭圆｛" + getObject() + "｝");
    }

}
