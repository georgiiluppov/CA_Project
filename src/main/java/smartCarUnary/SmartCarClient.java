package smartCarUnary;

import com.generated.grpc.AccidentAlertRequest;
import com.generated.grpc.AccidentAlertResponse;
import com.generated.grpc.SmartCarServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class SmartCarClient {
    public static Integer severityFromGUI = null;
    
    public static void main(String[] args) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            Thread.sleep(3000);
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_smartCar._tcp.local.", "SmartCarService");
            jmdns.close();

            if (serviceInfo == null) {
                System.err.println("SmartCar gRPC service not found.");
                return;
            }

            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered SmartCar service at " + host + " Port: " + port);

            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            SmartCarServiceGrpc.SmartCarServiceBlockingStub stub = SmartCarServiceGrpc.newBlockingStub(channel);
            
            int severity;
            if (severityFromGUI != null) {
                severity = severityFromGUI;
            } else {
                severity = (int) (Math.random() * 10) + 1;
            }
            
            String status;

            if (severity <= 5){
                status = "Minor";
            } else {
                status = "Serious";
            }
            System.out.println("Severity: " + severity);

            AccidentAlertRequest request = AccidentAlertRequest.newBuilder()
                    .setTimestamp((int) (System.currentTimeMillis() / 1000))
                    .setLocation("53.34919551832582, -6.241966724377")
                    .setStatus(status)
                    .build();

            AccidentAlertResponse response = stub.sendAccidentAlert(request);
            System.out.println(response.getMessage());

            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}