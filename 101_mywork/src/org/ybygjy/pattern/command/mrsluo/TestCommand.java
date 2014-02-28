package org.ybygjy.pattern.command.mrsluo;
/**
 * 命令模式测试入口
 * @author WangYanCheng
 * @version 2011-8-1
 */
public class TestCommand {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        System.out.println("================绘制图形================");
        Chart chart = new Chart();
        System.out.println("执行三个命令");
        ICommand cmd = CommandFactory.getCommandInst("line");
        cmd.setObject("直线:(3,3,8,10)");
        chart.execCmd(cmd);
        
        cmd = CommandFactory.getCommandInst("ellipse");
        cmd.setObject("椭圆:(10,20,8,10)");
        chart.execCmd(cmd);
        
        cmd = CommandFactory.getCommandInst("rect");
        cmd.setObject("矩形:(50,50,10,20)");
        chart.execCmd(cmd);
        
        System.out.println("======================回滚两个命令======================");
        chart.undo();
        chart.undo();
        System.out.println("======================重做两个命令======================");
        chart.redo();
        chart.redo();
    }
}
