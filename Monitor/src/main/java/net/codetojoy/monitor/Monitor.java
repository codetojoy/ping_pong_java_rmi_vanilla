package net.codetojoy.monitor; 

import net.codetojoy.common.*;

import java.util.*;
import java.util.concurrent.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Task implements Runnable {
    private static final String PING_SERVICE = "pingService";
    private static final String PONG_SERVICE = "pongService";

    private final Set ALL_ENTRIES = new HashSet<>();

    private PingService pingService = null;
    private PongService pongService = null;

    Task(PingService pingService, PongService pongService) {
        this.pingService = pingService;
        this.pongService = pongService;

        ALL_ENTRIES.add(PING_SERVICE); 
        ALL_ENTRIES.add(PONG_SERVICE); 
    }

    private void healthCheck(String serviceName) {
        try {
            String result = "N/A";

            if (serviceName.equals(PING_SERVICE)) {
                long healthCheckResult = pingService.healthCheck();
                result = new Date(healthCheckResult).toString();
            } else if (serviceName.equals(PONG_SERVICE)) {
                long healthCheckResult = pongService.healthCheck();
                result = new Date(healthCheckResult).toString();
            } 

            System.out.println("TRACER from " + serviceName + " : " + result);
        } catch(Exception ex) {
            System.err.println("TRACER caught ex: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("--------------------------");
            System.out.println("\n\nTRACER " + new Date() + " checking...");
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2020);
            String[] entries = registry.list();

            if (entries != null && entries.length > 0) {
                for (String entry : entries) {
                    System.out.println("\nTRACER found entry: " + entry); 
                    healthCheck(entry);
                }
/*
                Set<String> refSet = new HashSet(ALL_ENTRIES); 
                Set<String> entrySet = new HashSet<String>(Arrays.asList(entries));
                refSet.removeAll(entrySet);

                if (! refSet.isEmpty()) {
                    for (String entry : refSet) {
                        System.out.println("\nTRACER found entry: " + entry); 
                        healthCheck(entry);
                    }
                }
*/
            } else {
                System.out.println("TRACER no entries found");
            } 
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}

public class Monitor {
    private final PingService pingService;
    private final PongService pongService;
    private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);

    public Monitor(PingService pingService, PongService pongService) {
        this.pingService = pingService;
        this.pongService = pongService;
    }

    public void start() {
        final int delayInSeconds = 4;
        scheduledPool.scheduleAtFixedRate(new Task(pingService, pongService), 
                                            delayInSeconds, delayInSeconds, TimeUnit.SECONDS);
    }
    
    public static void main(String[] args) {
        try {
            PingService pingService = new PingServiceClient().getPingService();
            PongService pongService = new PongServiceClient().getPongService();
            Monitor monitor = new Monitor(pingService, pongService);
            monitor.start();

            while (true) {
                Thread.sleep(30*1000); 
            }
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}
