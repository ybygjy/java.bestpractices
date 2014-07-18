package org.ybygjy.jcip.chap4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基于委托的线程安全的车辆追踪器
 * @author WangYanCheng
 * @version 2014-7-18
 */
public class DelegatingVehicleTrackerTest {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        int vehicleNums = 3;
        Map<String, Point> points = new HashMap<String, Point>();
        String vehicleNamePrefix = "VehicleThread_";
        for (int i = 1; i < vehicleNums; i++) {
            String vehicleName = vehicleNamePrefix + i;
            points.put(vehicleName, new Point(0, 0));
        }
        DelegatingVehicleTracker vehicleTracker = new DelegatingVehicleTracker(points);
        for (int i = 1; i < vehicleNums; i++) {
            String vehicleName = vehicleNamePrefix + i;
            new Vehicle(vehicleName, vehicleTracker).start();
        }
        new ViewControll(vehicleTracker).start();
    }
}

/**
 * 负责车辆信息数据采集
 * @author WangYanCheng
 * @version 2014-7-18
 */
class DelegatingVehicleTracker {
    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;
    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<String, Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }
    /**
     * 取车辆位置信息，实时非快照方式
     * @return
     */
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }
    public Point getLocation(String id) {
        return locations.get(id);
    }
    public void setLocation(String id, int x, int y) {
        if (null == locations.replace(id, new Point(x, y))) {
            throw new IllegalArgumentException("Invalid vehicle name: " + id);
        }
    }
}

/**
 * 负责定义车辆跟踪线程
 * @author WangYanCheng
 * @version 2014-7-18
 */
class Vehicle extends Thread {
    private final DelegatingVehicleTracker vehicleTracker;
    private Random random = new Random(500);
    public Vehicle(String vehicleName, DelegatingVehicleTracker vehicleTracker) {
        super(vehicleName);
        this.vehicleTracker = vehicleTracker;
    }
    @Override
    public void run() {
        while (true) {
            vehicleTracker.setLocation(getName(), random.nextInt(), random.nextInt());
            try {
                sleep((long)(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 前端渲染线程
 * @author WangYanCheng
 * @version 2014-7-18
 */
class ViewControll extends Thread {
    private final DelegatingVehicleTracker vehicleTracker;
    public ViewControll(DelegatingVehicleTracker vehicleTracker) {
        this.vehicleTracker = vehicleTracker;
    }
    @Override
    public void run() {
        while (true) {
            Map<String, Point> vehicleLocations = vehicleTracker.getLocations();
            for (Map.Entry<String, Point> entry : vehicleLocations.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}