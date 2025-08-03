package RegistryDiscovery;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;

public class ServiceDiscovery {
    public static class GenericListener implements ServiceListener {
        private final String serviceLabel;

        public GenericListener(String serviceLabel) {
            this.serviceLabel = serviceLabel;
        }

        @Override
        public void serviceAdded(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            if (info.hasData()) {
                System.out.println("[" + serviceLabel + "] has data: " + String.join(",", info.getHostAddresses()));
            }
            System.out.println("[" + serviceLabel + "] Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("[" + serviceLabel + "] Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            System.out.println("[" + serviceLabel + "] Service resolved: " + info.getName() + " at " + info.getHostAddresses()[0] + ":" + info.getPort());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            System.out.println("Service Discovery started on: " + InetAddress.getLocalHost());

            jmdns.addServiceListener("_smartCar._tcp.local.", new GenericListener("SmartCar"));
            jmdns.addServiceListener("_smartWatch._tcp.local.", new GenericListener("SmartWatch"));
            jmdns.addServiceListener("_mobileApp._tcp.local.", new GenericListener("MobileApp"));
            jmdns.addServiceListener("_heartMonitor._tcp.local.", new GenericListener("HeartMonitor"));

            while (true) {
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

