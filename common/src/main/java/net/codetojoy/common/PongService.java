
package net.codetojoy.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PongService extends Remote {
    Ball pong(Ball ball) throws RemoteException;

    long healthCheck() throws RemoteException;
}
