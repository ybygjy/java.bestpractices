package org.ybygjy.pattern.flyweight.coffee;


/**
 * 负责模拟消费者\使用者
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class Client {
    /***/
    private Order[] orders = new Order[100];
    /***/
    private static int ordersMade = 0;
    /***/
    private FlavorFactory flavorFactory;
    /**
     * Constructor
     */
    public Client() {
        flavorFactory = FlavorFactory.getInstance();
    }
    /**
     * takeOrder
     * @param flavor flavor
     */
    public void takeOrders(String flavor) {
        orders[ordersMade++] = flavorFactory.getOrder(flavor);
    }
    /**
     * 卖给客人
     * @param orderNum 类型编码
     * @param tableNumber 桌位号
     */
    public void serve(int orderNum, int tableNumber) {
        orders[orderNum].serve(new Table(tableNumber));
    }
    /**
     * ordersMade
     * @return ordersMade
     */
    public int getOrdersMade() {
        return ordersMade;
    }
    /**
     * @return the flavorFactory
     */
    public FlavorFactory getFlavorFactory() {
        return flavorFactory;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.takeOrders("Black Coffee");
        client.takeOrders("Capucino");
        client.takeOrders("Espresso");
        client.takeOrders("Espresso");
        for (int i = 0; i < client.getOrdersMade(); i++) {
            client.serve(i, i);
        }
        System.out.println("TotalFlavors:" + client.getFlavorFactory().getTotalFlavors());
    }
}
