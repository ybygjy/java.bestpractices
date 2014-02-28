package org.ybygjy.pattern.visitor;

import org.ybygjy.pattern.visitor.receiver.Pc;
import org.ybygjy.pattern.visitor.visitor.InventoryVisitor;
import org.ybygjy.pattern.visitor.visitor.PriceVisitor;

/**
 * 客户端
 * @author WangYanCheng
 * @version 2013-2-6
 */
public class Client {
    private PriceVisitor priceVisitor;
    private InventoryVisitor inventoryVisitor;
    private Equipment equipment;
    public Client() {
        //设备
        equipment = new Pc();
        //价格Visitor
        priceVisitor = new PriceVisitor();
        //库存Visitor
        inventoryVisitor = new InventoryVisitor();
    }
    public void doWork() {
        //设备接口
        equipment.accept(priceVisitor);
        //设备接口
        equipment.accept(inventoryVisitor);
        //统计金额
        System.out.println("Price: ".concat(String.valueOf(priceVisitor.value())));
        //统计库存
        System.out.println("Number of parts: ".concat(String.valueOf(inventoryVisitor.getSize())));
    }
    public static void main(String[] args) {
        new Client().doWork();
    }
}
