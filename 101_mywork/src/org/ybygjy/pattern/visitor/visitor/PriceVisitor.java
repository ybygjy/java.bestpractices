package org.ybygjy.pattern.visitor.visitor;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;

public class PriceVisitor implements Visitor {
    private float value;
    public void visitCase(Equipment caseInst) {
        value += caseInst.price();
    }

    public void visitCPU(Equipment cpu) {
        value += cpu.price();
    }

    public void visitHardDisk(Equipment hardDisk) {
        value += hardDisk.price();
    }

    public void visitIntegratedBoard(Equipment integratedBoard) {
        value += integratedBoard.price();
    }

    public void visitMainBoard(Equipment mainBoard) {
        value += mainBoard.price();
    }
    public float value() {
        return value;
    }
}
