package org.ybygjy.pattern.command.mrsluo;

/**
 * 绘制线段
 * @author WangYanCheng
 * @version 2011-8-1
 */
public class DrawLineCommand implements ICommand {
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
        System.out.println("绘制线段｛" + this.grapObj + "｝");
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
        System.out.println("擦除线段｛" + this.grapObj + "｝");
    }
}
