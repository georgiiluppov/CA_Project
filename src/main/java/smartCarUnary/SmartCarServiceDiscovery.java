package smartCarUnary;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SmartCarServiceDiscovery {
    public static class SmartCarListener implements ServiceListener {

        @Override
        public void serviceAdded(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            if (info.hasData() ) {
                System.out.println("has data " + info.getHostAddresses().toString() );
            }
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            System.out.println("Service resolved: " + info.getName() + " at " + info.getHostAddresses()[0] + ":" + info.getPort());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            System.out.println("SmartCar Discovery started on: " + InetAddress.getLocalHost());
            jmdns.addServiceListener("_smartCar._tcp.local.", new SmartCarListener());

            while (true) {
                Thread.sleep(2000);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

