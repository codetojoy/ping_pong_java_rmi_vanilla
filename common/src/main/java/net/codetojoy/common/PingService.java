package net.codetojoy.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PingService extends Remote {
    Ball ping(Ball ball) throws RemoteException;

    long healthCheck() throws RemoteException;
}
