package org.ybygjy.web.calendar;
/**
 * 事务对象模型
 * @author WangYanCheng
 * @version 2011-3-24
 */
public class EventModel {
    /**编码*/
    private String id;
    /**类型*/
    private int type;
    /**标题*/
    private String title;
    /**内容*/
    private String content;
    /**起始日期*/
    private String startDay;
    /**起始时间*/
    private String startTime;
    /**结束日期*/
    private String endDay;
    /**结束时间*/
    private String endTime;
    /**全天标记*/
    private boolean allDayFlag;
    /**
     * 构造函数初始化属性值
     * @param id 编码
     * @param type 类型
     * @param title 标题
     * @param content 内容
     * @param startDay 起始日期
     * @param startTime 起始时间
     * @param endDay 结束日期
     * @param endTime 结束时间
     * @param allDayFlag 全天标记
     */
    public EventModel(String id, int type, String title, String content, String startDay, String startTime,
        String endDay, String endTime, boolean allDayFlag) {
        super();
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDay = endDay;
        this.endTime = endTime;
        this.allDayFlag = allDayFlag;
    }
    /**
     * 负责转换为JSON格式
     * @return jsonStr JSON格式
     */
    public String toJSON() {
        StringBuilder sbud = new StringBuilder();
        sbud.append("\"id\"").append(":\"").append(this.id).append("\",")
        .append("\"type\"").append(":\"").append(type).append("\",")
        .append("\"title\"").append(":\"").append(title).append("\",")
        .append("\"content\"").append(":\"").append(content).append("\",")
        .append("\"start\":\"").append(startDay).append("\",")
        .append("\"startTime\":\"").append(startTime).append("\",")
        .append("\"end\":\"").append(endDay).append("\",")
        .append("\"endTime\":\"").append(endTime).append("\",")
        .append("\"allDay\":").append(allDayFlag).append("");
        return sbud.toString();
    }
}
