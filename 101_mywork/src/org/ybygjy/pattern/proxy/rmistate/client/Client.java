package org.ybygjy.pattern.proxy.rmistate.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.ybygjy.pattern.proxy.rmistate.IGumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IState;

/**
 * GumballClient
 * @author WangYanCheng
 * @version 2011-1-4
 */
public class Client {
    /**后缀*/
    private static String postfixx = "Gumball";
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        String rmiUrl = "rmi://127.0.0.1:1099/" + postfixx;
//        String rmiUrl = "rmi://" + args[0] + "/" + postfixx;
        IGumballMachine igm = null;
        try {
            igm = (IGumballMachine) Naming.lookup(rmiUrl);
            System.out.println(igm.getLocation() + ":" + igm.getCount() + ":");
            IState state = igm.getState();
            System.out.println(state);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
