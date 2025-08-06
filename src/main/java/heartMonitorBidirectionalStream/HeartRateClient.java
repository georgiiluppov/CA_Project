package heartMonitorBidirectionalStream;

import GUI.GUIHeartMonitor;
import com.generated.grpc.HeartRate;
import com.generated.grpc.HeartRateFeedback;
import com.generated.grpc.HeartRateServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class HeartRateClient {
    public static void main(String[] args) throws InterruptedException {
        try {
            // Creating JmDNS instance for local host
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Wait a bit for discovery (without this line client cannot find service if
            // both client/server have been started simultaneously
            Thread.sleep(3000);
            // Lookup service info by type and name
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_heartMonitor._tcp.local.", "HeartMonitor");
            jmdns.close();

            // If service was not found, print error and stop
            if (serviceInfo == null) {
                System.err.println("HeartMonitor gRPC service not found.");
                return;
            }

            // Getting host IP and port from service info
            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered HeartMonitor service at " + host + " Port: " + port);

            // Create gRPC channel to connect to the server
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            // Creating async stub for HeartRateService
            HeartRateServiceGrpc.HeartRateServiceStub asyncStub = HeartRateServiceGrpc.newStub(channel);

            // Creating a StreamObserver to handle responses from the server
            StreamObserver<HeartRate> requestObserver = asyncStub.monitorHeartRate(new StreamObserver<HeartRateFeedback>() {
                @Override
                public void onNext(HeartRateFeedback feedback) {
                    // Called when server sends a feedback message
                    System.out.println("Server feedback: " + feedback.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    // If an error occurs
                    System.err.println("Error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    // Called when server finishes sending feedback
                    System.out.println("Server finished sending feedback.");
                }
            });


            // Send 30 heart rate messages to the server, 1 per 3 seconds
            for (int i = 0; i < 30; i++) {
                // Generate a random bpm value between 10 and 250 (BPM)
                int bpm = 10 + (int) (Math.random() * 241);
                System.out.println("-----------------------------------------------");
                System.out.println("Sending BPM: " + bpm + " at: " + LocalTime.now());

                // Building HeartRate message
                HeartRate heartRate = HeartRate.newBuilder().setBpm(bpm).build();
                // Send the message to the server
                requestObserver.onNext(heartRate);

                // Sleep for 3 seconds before sending next message
                Thread.sleep(3000);

                if (GUIHeartMonitor.stopStreamGUI){
                    System.out.println("Client cancelling the stream");
                    requestObserver.onError(
                            Status.CANCELLED
                                    .withDescription("Client cancelled the stream")
                                    .asRuntimeException()
                    );
                    break;
                }
            }

            // If the stream was not cancelled, complete it normally
            requestObserver.onCompleted();

            // Shutdown the channel cleanly
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e){
            // Printing stack trace and error message
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
