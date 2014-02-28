package org.ybygjy.pattern.proxy.rmistate;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 通用接口
 * @author WangYanCheng
 * @version 2010-12-24
 */
public interface IGumballMachine extends Remote {
    /**
     * getCount
     * @return get the gumball count
     * @throws RemoteException RemoteException
     */
    public int getCount() throws RemoteException;
    /**
     * getLocation
     * @return location
     * @throws RemoteException RemoteException
     */
    public String getLocation() throws RemoteException;
    /**
     * getState
     * @return state State
     * @throws RemoteException RemoteException
     */
    public IState getState() throws RemoteException;
}
