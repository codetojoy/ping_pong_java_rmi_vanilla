package net.codetojoy.server; 

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import net.codetojoy.common.*;
import net.codetojoy.common.rmi.*;

public class PingServiceImpl extends UnicastRemoteObject implements PingService {
    private static final long serialVersionUID = 1L;

    protected PingServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public long healthCheck() throws RemoteException {
        return System.currentTimeMillis();
    } 

    @Override
    public Ball ping(Ball ball) throws RemoteException {
        Ball result = ball;

        if (! ball.isMaxedOut()) {
            String message = "PING #: " + (ball.getNumHits() + 1);
            System.out.println("TRACER " + message);

            Ball newBall = ball.hit(message);
            
            try {
                System.out.println("TRACER halting sequence. numHits: " + ball.getNumHits());
                System.out.println("TRACER ball: " + ball.toString());
                PongService pongService = new PongServiceClient().getPongService();
                result = pongService.pong(newBall);
            } catch (Exception ex) {
                System.err.println("TRACER caught exception on ping service, so sequence will end here");
            }
        } else {
            System.out.println("TRACER halting sequence. numHits: " + ball.getNumHits());
            System.out.println("TRACER ball: " + ball.toString());
        }
        
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\n\nTRACER: PingService starting up...");

        Naming.rebind("rmi://localhost:2020/pingService", new PingServiceImpl());   

        System.out.println("\n\nTRACER: PingService ready !");
    }
}
