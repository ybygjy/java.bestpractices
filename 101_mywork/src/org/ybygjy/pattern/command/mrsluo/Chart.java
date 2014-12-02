package org.ybygjy.pattern.command.mrsluo;

import java.util.ArrayList;
import java.util.List;

/**
 * 实际图形绘制实例
 * @author WangYanCheng
 * @version 2011-8-1
 */
public class Chart {
    /**重做*/
    private List<ICommand> redoCmdArray;
    /**回滚*/
    private List<ICommand> undoCmdArray;
    public Chart() {
        redoCmdArray = new ArrayList<ICommand>();
        undoCmdArray = new ArrayList<ICommand>();
    }
    public void execCmd(ICommand cmd) {
        cmd.redo();
        undoCmdArray.add(cmd);
    }
    /**
     * 重做
     */
    public void redo() {
        int size = redoCmdArray.size();
        if (size > 0) {
            ICommand cmd = redoCmdArray.remove(size - 1);
            cmd.redo();
            undoCmdArray.add(cmd);
        }
    }
    /**
     * 回滚
     */
    public void undo() {
        int size = undoCmdArray.size();
        if (size > 0) {
            ICommand cmmd = undoCmdArray.remove(size - 1);
            cmmd.undo();
            redoCmdArray.add(cmmd);
        }
    }
}
