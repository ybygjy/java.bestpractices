package org.ybygjy.dbcompare.model;

/**
 * ´¥·¢Æ÷ÊµÀý
 * @author WangYanCheng
 * @version 2011-10-9
 */
public class TriggerMeta {
    private AbstractObjectMeta tableInst;
    private String triggName;
    private String triggType;
    private String triggEvent;
    private String triggObjType;
    private String triggWhenClause;
    private String triggStatus;

    public AbstractObjectMeta getTableInst() {
        return tableInst;
    }

    public void setTableInst(AbstractObjectMeta tableInst) {
        this.tableInst = tableInst;
    }

    public String getTriggName() {
        return triggName;
    }

    public void setTriggName(String triggName) {
        this.triggName = triggName;
    }

    public String getTriggType() {
        return triggType;
    }

    public void setTriggType(String triggType) {
        this.triggType = triggType;
    }

    public String getTriggEvent() {
        return triggEvent;
    }

    public void setTriggEvent(String triggEvent) {
        this.triggEvent = triggEvent;
    }

    public String getTriggObjType() {
        return triggObjType;
    }

    public void setTriggObjType(String triggObjType) {
        this.triggObjType = triggObjType;
    }

    public String getTriggWhenClause() {
        return triggWhenClause;
    }

    public void setTriggWhenClause(String triggWhenClause) {
        this.triggWhenClause = triggWhenClause;
    }

    public void setTriggStatus(String triggStatus) {
        this.triggStatus = triggStatus;
    }

    public String getTriggStatus() {
        return triggStatus;
    }
}
