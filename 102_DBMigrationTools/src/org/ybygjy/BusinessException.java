package org.ybygjy;

/**
 * 逻辑级别异常
 * <p>
 * 考虑到分层结构会出现异常丢失、异常信息不全面的问题
 * <p>
 * 此类负责定义业务逻辑层异常， 其它子系统需要依据{@link Exception}的继承关系做出相关对象向上转型
 * <p>
 * 示例，在UI层会产生如下代码
 * 
 * <pre>
 * try {
 *  DBUtils.testConnection(dbInfo);
 * } catch(Exception e) {
 *  throws new UIException("数据库连接失败", e);
 * }
 * </pre>
 * @author WangYanCheng
 * @version 2012-11-15
 */
public class BusinessException extends Exception {
    /**
     * serial number
     */
    private static final long serialVersionUID = 2796913021739651254L;

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(Throwable cause) {
        this("", cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
