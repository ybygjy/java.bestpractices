package org.ybygjy.pattern.visitor;


/**
 * 访问者接口，定义调用被访问者
 * @author WangYanCheng
 * @version 2013-2-6
 */
public interface Visitor {
    public void visitCase(Equipment caseInst);
    public void visitCPU(Equipment cpm);
    public void visitHardDisk(Equipment hardDisk);
    public void visitIntegratedBoard(Equipment integratedBoard);
    public void visitMainBoard(Equipment mainBoard);
}
