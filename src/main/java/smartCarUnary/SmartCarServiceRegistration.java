package smartCarUnary;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SmartCarServiceRegistration {
    private static JmDNS jmdns;
    private static SmartCarServiceRegistration theRegister;

    private SmartCarServiceRegistration() throws UnknownHostException, IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("SmartCar Register: " + InetAddress.getLocalHost());
    }

    public static SmartCarServiceRegistration getInstance() throws IOException {
        if (theRegister == null) {
            theRegister = new SmartCarServiceRegistration();
        }
        return theRegister;
    }

    public void registerService(String type, String name, int port, String info) throws IOException {
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, info);
        jmdns.registerService(serviceInfo);
        System.out.println("SmartCar Registered Service: " + serviceInfo.toString());
    }
}

