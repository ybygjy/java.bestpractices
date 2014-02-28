package org.ybygjy.pattern.proxy.rmistate.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.ybygjy.pattern.proxy.rmistate.GumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IGumballMachine;

/**
 * Server<br>
 * <p>
 * 1、Java环境提供了一个单机测试的RMI注册机制，但仅限与单机测试，无法与其它主机联通测试。
 * </p>
 * 2、可使用Java提供的rmiregistry终端模拟真实环境<br>
 * <ol>
 * <li>声明remote接口子类</li>
 * <li>声明继承UnicastRemoteObject并且负责实现业务接口的具体实现类 </li>
 * <li>使用rmic&nbsp;MyRemoteImpl 生成Stub</li>
 * </ol>
 * 3、编写Client类注意
 * <ol>
 * <li>引入RemoteInterface.class</li>
 * <li>引入RemoteImpl_Stub.class</li>
 * <li>无须引入RemoteImpl.class</li>
 * </ol>
 * 4、启用
 * <ol>
 * <li>在相对classes目录下执行rmiregistry命令</li>
 * <li>执行Server端绑定</li>
 * <li>执行远程Client端测试</li>
 * </ol>
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class Server {
    /***/
    public static String rmiURL = "Gumball";

    /**
     * 测试入口
     * @param args args
     * @throws RemoteException RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        String url = "//" + args[0] + "/Gumball";
        IGumballMachine gumball = new GumballMachine(url, Integer.parseInt(args[1]));
        try {
            Naming.rebind(url, gumball);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // innerRebind("Gumball", gumball);
    }

    /**
     * rebind
     * @param url url
     * @param obj obj
     */
    static void innerRebind(String url, Remote obj) {
        try {
            createRegistry(java.rmi.registry.Registry.REGISTRY_PORT);
            Naming.rebind(url, obj);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * doCreateRegistry
     * @param port port
     */
    static void createRegistry(int port) {
        try {
            LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
