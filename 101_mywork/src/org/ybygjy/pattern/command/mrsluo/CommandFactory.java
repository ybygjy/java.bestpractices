package org.ybygjy.pattern.command.mrsluo;
/**
 * 命令工厂方法
 * @author WangYanCheng
 * @version 2011-8-1
 */
public class CommandFactory {
    /**
     * 获取命令实例
     * @param commType 命令类型
     * @return 命令实例
     */
    public static ICommand getCommandInst(String commType) {
        if ("ellipse".equals(commType)) {
            return new DrawEllipseCommand();
        } else if("line".equals(commType)) {
            return new DrawLineCommand();
        } else if ("rect".equals(commType)) {
            return new DrawRectCommand();
        } else {
            return null;
        }
    }
}
