package net.codetojoy.client;

import net.codetojoy.common.*;
 
import java.rmi.Naming;

public class PongServiceClient {
    public PongService getPongService() {
        PongService pongService = null;

        try {
            pongService = (PongService) Naming.lookup("//localhost:2020/pongService");
        } catch (Exception ex) {
            System.err.println("TRACER caught exception : " + ex.getMessage());
        }

        return pongService;
    }
}
