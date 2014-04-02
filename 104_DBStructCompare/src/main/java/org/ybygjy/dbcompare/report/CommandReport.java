package org.ybygjy.dbcompare.report;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ybygjy.dbcompare.TaskReport;
import org.ybygjy.dbcompare.model.AbstractObjectFieldMeta;
import org.ybygjy.dbcompare.model.AbstractObjectMeta;
import org.ybygjy.dbcompare.model.ConstraintMeta;
import org.ybygjy.dbcompare.model.ContextModel;
import org.ybygjy.dbcompare.model.MetaConstant;
import org.ybygjy.dbcompare.model.MetaType;
import org.ybygjy.dbcompare.model.TriggerMeta;
import org.ybygjy.dbcompare.model.TypeMeta;


/**
 * 命令行方式构建报表
 * @author WangYanCheng
 * @version 2011-10-9
 */
public class CommandReport implements TaskReport {
    /** 命令行输出对象 */
    private PrintWriter commandPrintWriter;
    private String srcUser;
    private String targetUser;
    /** 转储流对象 */
    private PrintStream restoreStream;
    private Map<MetaType, InnerWork> innerReportObj;
    private int defStep = 20;
    /**换行*/
    private static String lineFeed = "\r\n";

    /**
     * Constructor
     */
    public CommandReport() {
        commandPrintWriter = new PrintWriter(System.out);
        innerReportObj = new HashMap<MetaType, InnerWork>();
        innerReportObj.put(MetaType.TABLE_OBJ, new TableObjCompReport());
        innerReportObj.put(MetaType.VIEW_OBJ, new ViewObjCompReport());
        innerReportObj.put(MetaType.TABLE_FIELDOBJ, new TableFieldObjCompReport());
        innerReportObj.put(MetaType.SEQ_OBJ, new SequenceObjCompReport());
        innerReportObj.put(MetaType.TRIG_OBJ, new TriggerObjCompReport());
        innerReportObj.put(MetaType.PROC_OBJ, new ProcedureObjCompReport());
        innerReportObj.put(MetaType.FUNC_OBJ, new FunctionObjCompReport());
        innerReportObj.put(MetaType.TYPE_OBJ, new TypeObjCompReport());
        innerReportObj.put(MetaType.CONS_OBJ, new ConstraintObjCompReport());
        innerReportObj.put(MetaType.INVALID_OBJ, new InvalidObjCompReport());
    }

    /**
     * {@inheritDoc}
     */
    public void setReOutputStream(OutputStream ous) {
        if (null == ous) {
            return;
        }
        restoreStream = new PrintStream(ous);
    }
    
    /**
     * {@inheritDoc}
     */
    public void setSrcUser(String srcUser) {
    	this.srcUser = srcUser.concat(".");
	}
    
    /**
     * {@inheritDoc}
     */
	public void setTargetUser(String targetUser) {
		this.targetUser = targetUser.concat(".");
	}

	/**
     * {@inheritDoc}
     */
    public void generateReport(ContextModel[] commonModels) {
        for (ContextModel contextModel : commonModels) {
            if (!contextModel.getTaskInfo().isFinished()) {
                outputTaskInfo(contextModel);
                continue;
            }
            switch (contextModel.getTaskInfo().getTaskType()) {
	            case CONS_OBJ:
	            	innerReportObj.get(MetaType.CONS_OBJ).doWork(contextModel);
	            	break;
                case TABLE_OBJ:
                    innerReportObj.get(MetaType.TABLE_OBJ).doWork(contextModel);
                    break;
                case VIEW_OBJ:
                    innerReportObj.get(MetaType.VIEW_OBJ).doWork(contextModel);
                    break;
                case TABLE_FIELDOBJ:
                    innerReportObj.get(MetaType.TABLE_FIELDOBJ).doWork(contextModel);
                    break;
                case SEQ_OBJ:
                    innerReportObj.get(MetaType.SEQ_OBJ).doWork(contextModel);
                    break;
                case TRIG_OBJ:
                    innerReportObj.get(MetaType.TRIG_OBJ).doWork(contextModel);
                    break;
                case PROC_OBJ:
                    innerReportObj.get(MetaType.PROC_OBJ).doWork(contextModel);
                    break;
                case FUNC_OBJ:
                    innerReportObj.get(MetaType.FUNC_OBJ).doWork(contextModel);
                    break;
                case TYPE_OBJ:
                    innerReportObj.get(MetaType.TYPE_OBJ).doWork(contextModel);
                    break;
                case INVALID_OBJ:
                	innerReportObj.get(MetaType.INVALID_OBJ).doWork(contextModel);
                	break;
                default:
                    break;
            }
            outputTaskInfo(contextModel);
            flush();
        }
    }
    private void outputSeperator() {
    	StringBuilder sbud = new StringBuilder();
    	for (int i = 0; i < 100; i++) {
    		sbud.append("*");
    	}
    	outputLine(sbud.toString());
    }
    /**
     * 注意换行符的支持问题\n只支持在Window下内容是换行的，而Other OS需要\r\n
     */
    private void outputLine() {
        print("", "\r\n");
    }

    private void print(String msg, String fmt) {
        commandPrintWriter.printf(fmt, msg);
        if (restoreStream != null) {
            restoreStream.printf(fmt, msg);
        }
    }

    private void flush() {
        commandPrintWriter.flush();
        if (null != restoreStream) {
            restoreStream.flush();
        }
    }

    /**
     * 输出并换行
     * @param str str
     */
    private void outputLine(String str) {
        outputMsg(str);
        outputLine();
    }

    private void outputMsg(String message) {
        print("%-40s", (null == message) ? " " : message);
    }

    private void outputMsg(int stepSize, String message) {
        print(("%-40s"), "  ".concat((null == message) ? "" : message));
    }

    private void outputMapEntry(Map<String, String> tmpMap) {
        for (Iterator<Map.Entry<String, String>> iterator = tmpMap.entrySet().iterator(); iterator
            .hasNext();) {
            Map.Entry<String, String> entry = iterator.next();
            outputMsg(entry.getKey().toString());
            outputMsg(defStep, entry.getValue().toString());
            outputLine();
        }
    }

    private void outputTaskInfo(ContextModel cm) {
        StringBuilder sbud = new StringBuilder();
        sbud.append(cm.getTaskInfo().getTaskName())
            .append(cm.getTaskInfo().isFinished() ? "任务执行完成" : "任务启动").append("\n");
        outputMsg(sbud.toString());
        outputSeperator();
    }

    private void outputList(List<AbstractObjectMeta> tmpArr, String prefix) {
        for (Iterator<AbstractObjectMeta> iterator = tmpArr.iterator(); iterator.hasNext();) {
            AbstractObjectMeta tableObj = iterator.next();
            outputMsg(defStep, prefix.concat(tableObj.getObjectName()));
            outputLine();
        }
    }

    /**
     * 验证字段相等性，忽略空格、换行等字符
     * @param srcStr 源字符串
     * @return true/false
     */
    private boolean testEquals4FieldDefValue(String srcStr) {
    	String[] srcStrArr = srcStr.split("!=");
    	if (srcStrArr.length != 2) {
    		return false;
    	}
    	srcStrArr[0] = srcStrArr[0].trim();
    	srcStrArr[1] = srcStrArr[1].trim();
    	if (srcStrArr[0].equals(srcStrArr[1])) {
    		return true;
    	}
    	return false;
    }
    /**
     * 内部标记接口
     * @author WangYanCheng
     * @version 2011-10-9
     */
    interface InnerWork {
        /**
         * doWork
         * @param contextModel 领域对象管理
         */
        public void doWork(ContextModel contextModel);
    }

    /**
     * 表对象
     * @author WangYanCheng
     * @version 2011-10-9
     */
    class TableObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, String> countMap = (Map<String, String>) contextModel.getRawData(MetaConstant.OBJ_COUNT);
            outputLine("表对象数量");
            outputMapEntry(countMap);
            outputLine();
            Map<String, List<AbstractObjectMeta>> lostAndExcessMap = (Map<String, List<AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            List<AbstractObjectMeta> tmpObjs = lostAndExcessMap.get(MetaConstant.OBJ_LOST);
            outputLine("表对象缺失\t\t" + tmpObjs.size());
            outputLine("\t缺失表对象编码");
            innerOutputList(tmpObjs, targetUser);
            tmpObjs = lostAndExcessMap.get(MetaConstant.OBJ_EXCESS);
            outputLine("表对象多余\t\t" + tmpObjs.size());
            outputLine("\t多余表对象编码");
            innerOutputList(tmpObjs, srcUser);
            outputLine();
            flush();
        }

        private void innerOutputList(List<AbstractObjectMeta> tabArr, String prefix) {
            for (Iterator<AbstractObjectMeta> iterator = tabArr.iterator(); iterator.hasNext();) {
                outputMsg(defStep, prefix + (iterator.next().getObjectName()));
                outputLine();
            }
        }
    }

    /**
     * 表字段对象
     * @author WangYanCheng
     * @version 2011-10-9
     */
    class TableFieldObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, Map<String, AbstractObjectMeta>> lostAndExcessMap = (Map<String, Map<String, AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            if (null == lostAndExcessMap) {
            	return;
            }
            Map<String, AbstractObjectMeta> tmpMap = lostAndExcessMap.get(MetaConstant.OBJ_LOST);
            outputLine();
            outputLine("字段对象缺失信息.......................................................");
            innerOutput(lostAndExcessMap.get(MetaConstant.OBJ_LOST), true);
            innerOutput(lostAndExcessMap.get(MetaConstant.OBJ_EXCESS), false);
            tmpMap = (Map<String, AbstractObjectMeta>) contextModel.getRawData(MetaConstant.OBJ_COMPAREDETAIL);
            outputLine();
            if (null == tmpMap) {
            	return;
            }
            outputLine("字段对象结构比较明细...................................................");
            innerOutput(tmpMap);
            lostAndExcessMap = null;
            tmpMap = null;
            flush();
        }

        private void innerOutput(Map<String, AbstractObjectMeta> tmpMap) {
            for (Iterator<Map.Entry<String, AbstractObjectMeta>> iterator = tmpMap.entrySet().iterator(); iterator
                .hasNext();) {
                Map.Entry<String, AbstractObjectMeta> tmpEntry = iterator.next();
                AbstractObjectMeta tmpTab = tmpEntry.getValue();
                List<AbstractObjectFieldMeta> tableFields = tmpTab.getObjectFieldArr();
                StringBuilder sbud = new StringBuilder();
                sbud.append("表/视图编码\t\t".concat(srcUser).concat(tmpEntry.getKey().toString()).concat("\t\t字段不匹配数量\t".concat(String.valueOf(tableFields.size()))))
                	.append(lineFeed);
                if (tableFields.size() < 1) {
                    continue;
                }
                sbud.append("字段明细：");
                
                // 遍历list
                StringBuilder sbud2 = new StringBuilder();
                for (Iterator<AbstractObjectFieldMeta> iterator1 = tableFields.iterator(); iterator1.hasNext();) {
                    AbstractObjectFieldMeta tfoInst = iterator1.next();
                    String tmpStr = outputFieldDetail(tfoInst);
                    if (tmpStr != null) {
                    	sbud2.append(tmpStr).append(lineFeed);
                    }
                }
                if (sbud2.length() > 0) {
                	outputLine(sbud.toString());
                	outputLine(sbud2.toString());
                }
            }
        }

        private void innerOutput(Map<String, AbstractObjectMeta> tmpMap, boolean lostOrExcess) {
            for (Iterator<Map.Entry<String, AbstractObjectMeta>> iterator = tmpMap.entrySet().iterator(); iterator
                .hasNext();) {
                Map.Entry<String, AbstractObjectMeta> tmpEntry = iterator.next();
                AbstractObjectMeta tmpTab = tmpEntry.getValue();
                List<AbstractObjectFieldMeta> tableFields = tmpTab.getObjectFieldArr();
                outputLine("表/视图编码\t".concat(tmpEntry.getKey().toString()).concat((lostOrExcess ? "\t缺失" : "\t多余").concat("字段数量 [").concat(
                		tableFields.size() + "]")));
                if (tableFields.size() < 1) {
                    continue;
                }
                // 遍历list
                for (Iterator<AbstractObjectFieldMeta> iterator1 = tableFields.iterator(); iterator1.hasNext();) {
                	outputLine("\t\t\t".concat(iterator1.next().getFieldCode()));
                }
            }
        }

        private String outputFieldDetail(AbstractObjectFieldMeta tfoInst) {
            String tmp = tfoInst.getFieldType();
            StringBuilder sbud = new StringBuilder();
            if (null != tmp) {
            	sbud.append("\t\t字段类型：").append(tmp);
            }
            tmp = tfoInst.getFieldLen();
            if (tmp != null) {
            	sbud.append("\t\t字段长度：").append(tmp);
            }
            tmp = tfoInst.getFieldPre();
            if (null != tmp) {
            	sbud.append("\t\t字段精度：").append(tmp);
            }
            tmp = tfoInst.getFieldSca();
            if (null != tmp) {
            	sbud.append("\t\t字段小数位数：").append(tmp);
            }
            tmp = tfoInst.getFieldNull();
            if (null != tmp) {
            	sbud.append("\t\t字段非空标记：").append(tmp);
            }
            tmp = tfoInst.getFieldDefValue();
            if (null != tmp && !testEquals4FieldDefValue(tmp)) {
            	/*
            	if (null != tfoInst.getFieldDefvLen()) {
             		sbud.append("\t\t字段默认值长度：").append(tfoInst.getFieldDefvLen());
            	}
            	*/
            	sbud.append("\t\t字段默认值：").append(tmp);
            }
            tmp = tfoInst.getFieldOrder();
            if (null != tmp) {
            	sbud.append("\t\t字段顺序：").append(tmp);
            }
            if (sbud.length() > 0) {
            	return ("\t\t\t字段编码：" + tfoInst.getFieldCode().concat("\t\t").concat(sbud.toString()));
            }
            return null;
        }
    }

    /**
     * 序列对象
     * @author WangYanCheng
     * @version 2011-10-9
     */
    class SequenceObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, List<AbstractObjectMeta>> tmpMap = (Map<String, List<AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            List<AbstractObjectMeta> tmpList = tmpMap.get(MetaConstant.OBJ_LOST);
            if (tmpList.size() > 0) {
            	outputMsg("序列对象缺失：" + tmpList.size());
            	outputLine();
            	outputLine("缺失明细：");
            	doPrintGeneralObj(tmpList, targetUser);
            }
            tmpList = tmpMap.get(MetaConstant.OBJ_EXCESS);
            if (tmpList.size() > 0) {
            	outputMsg("序列对象多余：" + tmpList.size());
            	outputLine();
            	outputLine("多余明细：");
            	doPrintGeneralObj(tmpList, srcUser);
            }
            flush();
        }

        private void doPrintGeneralObj(List<AbstractObjectMeta> objList, String prefix) {
            for (Iterator<AbstractObjectMeta> iterator = objList.iterator(); iterator.hasNext();) {
                AbstractObjectMeta go = iterator.next();
                outputMsg(defStep, prefix.concat(go.getObjectName()));
                outputLine();
            }
        }
    }

    /**
     * 触发器对象
     * @author WangYanCheng
     * @version 2011-10-10
     */
    class TriggerObjCompReport implements InnerWork {

        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, Map<String, AbstractObjectMeta>> lostAndExcessMap = (Map<String, Map<String, AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            Map<String, AbstractObjectMeta> tmpMap = lostAndExcessMap.get(MetaConstant.OBJ_LOST);
            if (null != tmpMap) {
            	outputLine("缺失触发器明细");
            	outputMsg(defStep, "关联对象编码\t\t\t触发器编码\t\t\t其它信息");
            	outputLine();
            	innerOutput(tmpMap, targetUser);
            }
            tmpMap = lostAndExcessMap.get(MetaConstant.OBJ_EXCESS);
            if (null != tmpMap) {
            	outputLine("多余触发器明细");
            	outputMsg(defStep, "关联对象编码\t\t\t触发器编码\t\t\t其它信息");
            	outputLine();
            	innerOutput(tmpMap, srcUser);
            }
            tmpMap = (Map<String, AbstractObjectMeta>) contextModel.getRawData(MetaConstant.OBJ_COMPAREDETAIL);
            if (null != tmpMap) {
            	outputLine("触发器对象内容比较明细");
            	outputMsg(defStep, "关联对象编码\t\t\t触发器编码\t\t\t其它信息");
            	outputLine();
            	innerOutput(tmpMap, "");
            }
            flush();
        }

        private void innerOutput(Map<String, AbstractObjectMeta> tmpMap, String prefix) {
            for (Iterator<String> iterator = tmpMap.keySet().iterator(); iterator.hasNext();) {
                String tableCode = iterator.next();
                AbstractObjectMeta tmpTOInst = tmpMap.get(tableCode);
                List<TriggerMeta> tmpList = tmpTOInst.getTriggerObjArr();
                outputMsg(defStep, tableCode + " [" + tmpList.size() + "]");
                outputLine();
                for (Iterator<TriggerMeta> iterator1 = tmpList.iterator(); iterator1.hasNext();) {
                    TriggerMeta triObj = iterator1.next();
                    outputMsg("\t\t\t\t");
                    outputMsg(prefix.concat(triObj.getTriggName()).concat("\t\t").concat(triObj.getTriggType() == null ? "" : triObj.getTriggType()));
                    outputLine();
                }
            }
            outputLine();
        }
    }

    /**
     * 视图对象报表
     * @author WangYanCheng
     * @version 2011-10-10
     */
    class ViewObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
        	Map<String, String> countMap = (Map<String, String>) contextModel.getRawData(MetaConstant.OBJ_COUNT);
        	if (null != countMap) {
        		outputLine("视图对象数量统计：");
        		outputMapEntry(countMap);
        		outputLine();
        	}
            Map<String, List<AbstractObjectMeta>> lostAndExcessArr = (Map<String, List<AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            if (null != lostAndExcessArr) {
            	List<AbstractObjectMeta> tmpArr = lostAndExcessArr.get(MetaConstant.OBJ_LOST);
            	outputLine("视图对象缺失\t数量：".concat(String.valueOf(tmpArr.size())));
            	outputList(tmpArr, targetUser);
            	outputLine();
            	tmpArr = lostAndExcessArr.get(MetaConstant.OBJ_EXCESS);
            	outputLine("视图对象多余\t数量：".concat(String.valueOf(tmpArr.size())));
            	outputLine();
            	outputList(tmpArr, srcUser);
            }
            flush();
        }
    }

    /**
     * 存储过程对象比较报表
     * @author WangYanCheng
     * @version 2011-10-10
     */
    class ProcedureObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, AbstractObjectMeta> lostAndExcessColl = (Map<String, AbstractObjectMeta>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            List<AbstractObjectMeta> tmpArr = (List<AbstractObjectMeta>) lostAndExcessColl.get(MetaConstant.OBJ_LOST);
            outputLine("存储过程对象缺失\t数量：".concat(String.valueOf(tmpArr.size())));
            outputList(tmpArr, targetUser);
            tmpArr = (List<AbstractObjectMeta>) lostAndExcessColl.get(MetaConstant.OBJ_EXCESS);
            outputLine("存储过程对象多余\t数量：".concat(String.valueOf(tmpArr.size())));
            outputList(tmpArr, srcUser);
            outputLine();
            flush();
        }
    }

    /**
     * 函数对象比较报表
     * @author WangYanCheng
     * @version 2011-10-10
     */
    class FunctionObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, AbstractObjectMeta> lostAndExcessColl = (Map<String, AbstractObjectMeta>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            List<AbstractObjectMeta> tmpArr = (List<AbstractObjectMeta>) lostAndExcessColl.get(MetaConstant.OBJ_LOST);
            outputLine("函数对象缺失\t数量：".concat(String.valueOf(tmpArr.size())));
            outputList(tmpArr, srcUser);
            tmpArr = (List<AbstractObjectMeta>) lostAndExcessColl.get(MetaConstant.OBJ_EXCESS);
            outputLine("函数对象多余\t数量：".concat(String.valueOf(tmpArr.size())));
            outputList(tmpArr, targetUser);
            outputLine();
            flush();
        }
    }

    /**
     * 类型对象比较报表
     * @author WangYanCheng
     * @version 2011-10-10
     */
    class TypeObjCompReport implements InnerWork {
        /**
         * {@inheritDoc}
         */
        public void doWork(ContextModel contextModel) {
            Map<String, TypeMeta> lostAndExcessColl = (Map<String, TypeMeta>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
            List<TypeMeta> tmpArr = (List<TypeMeta>) lostAndExcessColl.get(MetaConstant.OBJ_LOST);
            outputLine("类型对象缺失\t数量：".concat(String.valueOf(tmpArr.size())));
            outputMsg(defStep, "类型名称\t\t\t\t类型类别");
            outputLine();
            outputList(tmpArr, targetUser);
            tmpArr = (List<TypeMeta>) lostAndExcessColl.get(MetaConstant.OBJ_EXCESS);
            outputLine("类型对象多余\t数量：".concat(String.valueOf(tmpArr.size())));
            if (tmpArr.size() > 0) {
            	outputMsg(defStep, "类型名称\t\t\t\t类型类别");
            	outputLine();
            	outputList(tmpArr, srcUser);
            }
            outputLine();
            flush();
        }

        private void outputList(List<TypeMeta> tmpArr, String prefix) {
            for (Iterator<TypeMeta> iterator = tmpArr.iterator(); iterator.hasNext();) {
                TypeMeta tableObj = iterator.next();
                outputMsg(defStep, prefix.concat(tableObj.getTypeName()).concat("\t\t\t\t").concat(tableObj.getTypeCode()));
                outputLine();
            }
        }
    }

    /**
     * 对象约束结构比较报表
     * @author WangYanCheng
     * @version 2011-10-17
     */
    class ConstraintObjCompReport implements InnerWork {
    	/**
    	 * {@inheritDoc}
    	 */
		public void doWork(ContextModel contextModel) {
			Map<String, List<AbstractObjectMeta>> lostAndExcessColl = (Map<String, List<AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_LOSTANDEXCESS);
			List<AbstractObjectMeta> tmpList = lostAndExcessColl.get(MetaConstant.OBJ_LOST);
			outputLine("对象约束缺失：[" +  tmpList.size() + "]");
			if (tmpList != null && tmpList.size() > 0) {
				innerOutput(tmpList);
			}
			tmpList = lostAndExcessColl.get(MetaConstant.OBJ_EXCESS);
			outputLine("对象约束多余：[" + tmpList.size() + "]");
			if (tmpList != null && tmpList.size() > 0) {
				innerOutput(tmpList);
			}
			outputLine();
			flush();
		}

		/**
		 * innerOutput
		 * @param innerList 对象列表集
		 */
		private void innerOutput(List<AbstractObjectMeta> innerList) {
			outputLine("关联对象编码\t\t约束名称\t");
			for (Iterator<AbstractObjectMeta> iterator = innerList.iterator(); iterator.hasNext();) {
				AbstractObjectMeta objInst = iterator.next();
				List<ConstraintMeta> tmpList = objInst.getConstraintArr();
				outputLine(objInst.getObjectName().concat(" [" + tmpList.size() + "]"));
				if (tmpList.size() > 0) {
					for (Iterator<ConstraintMeta> iterator1 = tmpList.iterator(); iterator1.hasNext();) {
						ConstraintMeta dbcInst = iterator1.next();
						outputLine("\t\t\t\t".concat(dbcInst.getConsName()));
					}
				}
			}
			outputLine();
		}
    }

    /**
     * 状态非法对象
     * @author WangYanCheng
     * @version 2011-10-19
     */
    class InvalidObjCompReport implements InnerWork {
    	/**
    	 * {@inheritDoc}
    	 */
		public void doWork(ContextModel contextModel) {
			Map<MetaType, List<AbstractObjectMeta>> invalidObjArr = (Map<MetaType, List<AbstractObjectMeta>>) contextModel.getRawData(MetaConstant.OBJ_INVALIDDETAIL);
			if (null == invalidObjArr) {
				return;
			}
			List<AbstractObjectMeta> tmpArr = null;
			for (Iterator<MetaType> iterator = invalidObjArr.keySet().iterator();iterator.hasNext();) {
				MetaType metaType = iterator.next();
				tmpArr = invalidObjArr.get(metaType);
				if (tmpArr.size() <= 0) {
					continue;
				}
				switch (metaType) {
					case FUNC_OBJ:
						innerPrint("函数", tmpArr);
						break;
					case PROC_OBJ:
						innerPrint("过程", tmpArr);
						break;
					case VIEW_OBJ:
						innerPrint("视图", tmpArr);
						break;
					case TRIG_OBJ:
						innerPrint("触发器", tmpArr);
						break;
					case PACKAGE_OBJ:
						innerPrint("包对象", tmpArr);
						break;
					default:
						innerPrint("未知类型", tmpArr);
						break;
				}
			}
		}

		/**
		 * 内部输出
		 * @param title 头信息
		 * @param tmpArr 实例集
		 */
		public void innerPrint(String title, List<AbstractObjectMeta> tmpArr) {
			outputLine(title.concat("对象非法，数量 " + tmpArr.size()));
			outputLine("\t\t".concat(title).concat("对象名称"));
			for (Iterator<AbstractObjectMeta> iterator = tmpArr.iterator(); iterator.hasNext();) {
				AbstractObjectMeta dbomInst = iterator.next();
				outputLine("\t\t\t".concat(srcUser).concat(dbomInst.getObjectName()));
			}
		}
    }
}
