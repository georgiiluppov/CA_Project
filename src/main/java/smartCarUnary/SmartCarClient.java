package smartCarUnary;

import GUI.GUISmartCar;
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
    // Static var to assign input from GUI
    public static Integer severityFromGUI = null;

    public static void main(String[] args) {
        try {
            // Create JmDNS instance to discover services on local network
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Wait a bit for discovery (without this line client cannot find service if
            // both client/server have been started simultaneously
            Thread.sleep(3000);
            // Look for service
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_smartCar._tcp.local.", "SmartCarService");
            jmdns.close();

            // If service was not found, print error and stop
            if (serviceInfo == null) {
                System.err.println("SmartCar gRPC service not found.");
                return;
            }

            // Getting host IP and port from service info
            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered SmartCar service at " + host + " Port: " + port);

            // Creating gRPC channel to connect to the server
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            // Creating a blocking stub
            SmartCarServiceGrpc.SmartCarServiceBlockingStub stub = SmartCarServiceGrpc.newBlockingStub(channel);

            // Determine severity: use GUI input if available, otherwise generate a random num
            int severity;
            if (severityFromGUI != null) {
                severity = severityFromGUI;
            } else {
                // Random severity between 1 and 10
                severity = (int) (Math.random() * 10) + 1;
            }

            // Define accident status based on severity
            // Can be received from GUI, so it will be assigned to empty string
            // Otherwise will be assigned to a Minor/Serious depending on a severity number
            String status;
            if (GUISmartCar.makeStatusEmptyGUI){
                status = "";
            } else if (severity <= 5){
                status = "Minor";
            } else {
                status = "Serious";
            }
            System.out.println("Severity: " + severity);
            
            // Building gRPC request message
            AccidentAlertRequest request = AccidentAlertRequest.newBuilder()
                    .setTimestamp((int) (System.currentTimeMillis() / 1000))
                    .setLocation("53.34919551832582, -6.241966724377")
                    .setStatus(status)
                    .build();

            // Sending the request to the server and receive the response
            AccidentAlertResponse response = stub.sendAccidentAlert(request);
            System.out.println(response.getMessage());

            // Shutdown the gRPC channel
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e){
            // Printing stack trace and error message
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}