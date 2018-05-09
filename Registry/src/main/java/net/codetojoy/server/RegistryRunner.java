package net.codetojoy.server; 

import net.codetojoy.server.config.RegistryConfiguration;

public class RegistryRunner {
    public static void main(String[] args) {
        // this will be the registry, so `main` will not exit
        new RegistryConfiguration().getMyRegistry(); 

        System.out.println("\n\nTRACER: Registry ready !");

        while (true) {
            System.out.println("TRACER registry ...");
            try { Thread.sleep(2*1000); } catch (Exception ex) {}
        }
    }
}
