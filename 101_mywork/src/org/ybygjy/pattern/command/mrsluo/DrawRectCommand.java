package org.ybygjy.pattern.command.mrsluo;

/**
 * 绘制矩形
 * @author WangYanCheng
 * @version 2011-8-1
 */
public class DrawRectCommand implements ICommand {
    private String graphInst;
    /**
     * {@inheritDoc}
     */
    public String getObject() {
        return this.graphInst;
    }

    /**
     * {@inheritDoc}
     */
    public void redo() {
        System.out.println("绘制矩形｛" + this.getObject() + "｝");
    }

    /**
     * {@inheritDoc}
     */
    public void setObject(String obj) {
        this.graphInst = obj;
    }

    /**
     * {@inheritDoc}
     */
    public void undo() {
        System.out.println("擦除矩形｛" + this.getObject() + "｝");
    }

}
