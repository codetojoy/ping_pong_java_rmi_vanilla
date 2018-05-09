package net.codetojoy.common;
 
import java.rmi.Naming;

public class PingServiceClient {
    public PingService getPingService() {
        PingService pingService = null;

        try {
            pingService = (PingService) Naming.lookup("//localhost:2020/pingService");
        } catch (Exception ex) {
            System.err.println("TRACER caught exception : " + ex.getMessage());
        }

        return pingService;
    }
}
