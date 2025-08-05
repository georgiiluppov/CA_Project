package RegistryDiscovery;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServiceRegistration {
    private static JmDNS jmdns;
    private static ServiceRegistration theRegister;

    private ServiceRegistration() throws UnknownHostException, IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Service Registry started on: " + InetAddress.getLocalHost());
    }

    public static ServiceRegistration getInstance() throws IOException {
        if (theRegister == null) {
            theRegister = new ServiceRegistration();
        }
        return theRegister;
    }

    public void registerService(String type, String name, int port, String info) throws IOException {
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, info);
        jmdns.registerService(serviceInfo);
        System.out.println("Name: " + name);
        System.out.println("Port: " + port);
        System.out.println("Info: " + info);
    }
}

