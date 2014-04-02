package org.ybygjy.dbcompare;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.ybygjy.dbcompare.task.oracle.FunctionObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.InvalidObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.ProcedureObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.SequenceObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.TableFieldObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.TableObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.TriggerObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.TypeObjCompare4Oracle;
import org.ybygjy.dbcompare.task.oracle.ViewObjCompare4Oracle;
import org.ybygjy.dbcompare.task.sqlserver.ConstraintsObjCompare4MSSql;
import org.ybygjy.dbcompare.task.sqlserver.FunctionObjCompare4MSSql;
import org.ybygjy.dbcompare.task.sqlserver.ProcedureObjCompare4MSSql;
import org.ybygjy.dbcompare.task.sqlserver.TableFieldObjCompare4MSSql;
import org.ybygjy.dbcompare.task.sqlserver.TableObjCompare4MSSql;
import org.ybygjy.dbcompare.task.sqlserver.TriggerObjCompare4MSSql;
import org.ybygjy.dbcompare.task.sqlserver.ViewObjCompare4MSSql;


/**
 * 任务实例工厂
 * @author WangYanCheng
 * @version 2011-10-9
 */
public class TaskFactory {
    private static TaskFactory taskFactInst = new TaskFactory();

    /**
     * Constructor
     */
    private TaskFactory() {
    }

    /**
     * 取任务对象创建工厂实例
     * @return taskFactInst taskFactInst
     */
    public static final TaskFactory getTaskFactoryInst() {
        return taskFactInst;
    }

    /**
     * 取得表对象结构比较实例
     * @param srcUser 源用户
     * @param targetUser 参照用户
     * @param conn 连接实例
     * @param taskLis 监听器
     * @return rtnTask rtnTask
     */
    public Task createTabObjCompTask(String srcUser, String targetUser, Connection conn,
                                     TaskListener taskLis) {
        Task rtnTask = new TableObjCompare4Oracle(srcUser, targetUser);
        rtnTask.setConn(conn);
        rtnTask.addListener(taskLis);
        return rtnTask;
    }

    /**
     * 取对象结构比对任务集
     * @param srcUser 源用户
     * @param targetUser 参照用户
     * @param conn 连接实例
     * @param taskLis 监听器
     * @return rtnTaskArr rtnTaskArr
     */
    public Task[] getTaskArray4Ora(String srcUser, String targetUser, Connection conn, TaskListener taskLis) {
        List<Task> rtnList = new ArrayList<Task>();
        rtnList.add(innerTaskBuilder(new TableObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new ViewObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new TableFieldObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new SequenceObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new TriggerObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new ProcedureObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new FunctionObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new TypeObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        rtnList.add(innerTaskBuilder(new InvalidObjCompare4Oracle(srcUser, targetUser), conn, taskLis));
        return rtnList.toArray(new Task[rtnList.size()]);
    }
    /**
     * 取对象结构比对任务集
     * @param srcUser 源用户
     * @param targetUser 参照用户
     * @param conn 连接实例
     * @param taskLis 监听器
     * @return rtnTaskArr rtnTaskArr
     */
    public Task[] getTaskArray4MSSql(String srcUser, String targetUser, Connection conn, TaskListener taskLis) {
    	List<Task> rtnList = new ArrayList<Task>();
    	rtnList.add(innerTaskBuilder(new TableObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	rtnList.add(innerTaskBuilder(new TableFieldObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	rtnList.add(innerTaskBuilder(new ViewObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	rtnList.add(innerTaskBuilder(new TriggerObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	rtnList.add(innerTaskBuilder(new ProcedureObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	rtnList.add(innerTaskBuilder(new FunctionObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	rtnList.add(innerTaskBuilder(new ConstraintsObjCompare4MSSql(srcUser, targetUser), conn, taskLis));
    	return rtnList.toArray(new Task[rtnList.size()]);
    }

    /**
     * 封装创建重复工作
     * @param taskInst taskInst
     * @param conn conn
     * @param taskLis {@link TaskListener}
     * @return taskInst
     */
    private Task innerTaskBuilder(Task taskInst, Connection conn, TaskListener taskLis) {
    	taskInst.setConn(conn);
    	taskInst.addListener(taskLis);
    	return taskInst;
    }
}
