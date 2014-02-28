package org.ybygjy.pattern.command.mrsluo;

/**
 * »æÖÆÍÖÔ²
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
        System.out.println("»æÖÆÍÖÔ²£û" + getObject() + "£ı");
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
        System.out.println("²Á³ıÍÖÔ²£û" + getObject() + "£ı");
    }

}
