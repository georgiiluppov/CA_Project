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
    public static String sendRandomAccidentAlert() {
        String result = "";
        try {
            // Creating JmDNS instance for local host
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Wait a bit for discovery (without this line client cannot find service if
            // both client/server have been started simultaneously
            Thread.sleep(3000);
            // Lookup service info by type and name
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_smartCar._tcp.local.", "SmartCarService");
            jmdns.close();

            // If service was not found, print error and stop
            if (serviceInfo == null) {
                result = "SmartCar gRPC service not found.";
                System.err.println(result);
                return result;
            }

            // Getting host IP and port from service info
            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered SmartCar service at " + host + " Port: " + port);

            // Create gRPC channel to connect to the server
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            // Creating blocking stub for service calls
            SmartCarServiceGrpc.SmartCarServiceBlockingStub stub = SmartCarServiceGrpc.newBlockingStub(channel);

            // Generate random num (severity) between 1 and 10
            int severity = (int) (Math.random() * 10) + 1;
            String status;

            // Condition if severity less than or equal to 5 (assigns "Minor"/"Serious")
            if (severity <= 5){
                status = "Minor";
            } else {
                status = "Serious";
            }
            System.out.println("Severity: " + severity);

            // Building request object
            AccidentAlertRequest request = AccidentAlertRequest.newBuilder()
                    // Setting current time in seconds
                    .setTimestamp((int) (System.currentTimeMillis() / 1000))
                    // Setting location string
                    .setLocation("53.34919551832582, -6.241966724377")
                    // Setting accident status
                    .setStatus(status)
                    .build();

            // Call gRPC method and get response
            AccidentAlertResponse response = stub.sendAccidentAlert(request);
            result = response.getMessage();
            System.out.println(result);

            // Shutdown the channel
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
            result = "Error: " + e.getMessage();
        }

        return result;
    }

    public static void main(String[] args) {
        sendRandomAccidentAlert();
    }
}
