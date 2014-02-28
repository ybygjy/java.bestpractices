package org.ybygjy.pattern.visitor.visitor;

import java.util.ArrayList;
import java.util.List;

import org.ybygjy.pattern.visitor.Equipment;
import org.ybygjy.pattern.visitor.Visitor;

public class InventoryVisitor implements Visitor {
    public void visitCase(Equipment caseInst) {
        equipments.add(caseInst);
    }

    public void visitCPU(Equipment cpu) {
        equipments.add(cpu);
    }

    public void visitHardDisk(Equipment hardDisk) {
        equipments.add(hardDisk);
    }

    public void visitIntegratedBoard(Equipment integratedBoard) {
        equipments.add(integratedBoard);
    }

    public void visitMainBoard(Equipment mainBoard) {
        equipments.add(mainBoard);
    }
    public int getSize() {
        return equipments.size();
    }
    private List<Equipment> equipments = new ArrayList<Equipment>();
}
