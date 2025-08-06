package smartwatchClientStream;

import GUI.GUISmartWatch;
import com.generated.grpc.SmartWatchServiceGrpc;
import com.generated.grpc.StepData;
import com.generated.grpc.StepSummary;
import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SmartWatchClient {
    public static String userIDGUI;
    private static SmartWatchServiceGrpc.SmartWatchServiceStub asyncStub;

    public static void main(String[] args) throws InterruptedException {
        try {
            // Create JmDNS instance to discover services on local network
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Wait a bit for discovery (without this line client cannot find service if
            // both client/server have been started simultaneously
            Thread.sleep(3000);
            // Look for service called "SmartWatch" and type "_smartWatch._tcp.local."
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_smartWatch._tcp.local.", "SmartWatch");
            jmdns.close();

            // If service was not found, print error and stop
            if (serviceInfo == null) {
                System.err.println("SmartWatch gRPC service not found.");
                return;
            }

            // Getting host IP and port from service info
            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered SmartWatch service at " + host + " Port: " + port);

            // Create gRPC channel to connect to the server
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();

            // Client ID string to send with requests
            // The problem was to run from both: GUI (with and without button clicked) and IDE
            // and if 'invalid' button was clicked in GUI, throw an error,
            // but run from IDE using hardcoded 'test' clientID
            String clientId = "";

            if(GUISmartWatch.validID){
                clientId = "valid";
            } else if (GUISmartWatch.invalidID) {
                clientId = "invalid";
            }

            // To run in IDE
            if(!GUISmartWatch.isServerRunningWatch) {
                clientId = "test";
            }

            // Metadata container
            Metadata metadata = new Metadata();
            // Metadata key for "client-id"
            Metadata.Key<String> clientIdKey = Metadata.Key.of("client-id", Metadata.ASCII_STRING_MARSHALLER);
            // Put client ID value into metadata
            metadata.put(clientIdKey, clientId);

            // Check if clientId is valid (not null or empty)
            if (clientId == null || clientId.isEmpty()) {
                System.err.println("No client ID provided. Cannot proceed.");
                return;
            }

            // Create interceptor to attach metadata to the call
            ClientInterceptor interceptor = MetadataUtils.newAttachHeadersInterceptor(metadata);
            Channel interceptedChannel = ClientInterceptors.intercept(channel, interceptor);
            // Async stub
            asyncStub = SmartWatchServiceGrpc.newStub(interceptedChannel);

            // CountDownLatch is to wait for server to complete
            CountDownLatch latch = new CountDownLatch(1);

            // Observer to handle server responses
            StreamObserver<StepSummary> responseObserver = new StreamObserver<StepSummary>() {
                @Override
                public void onNext(StepSummary stepSummary) {
                    // Printing summary data received from server
                    System.out.println("Summary received:");
                    System.out.println("Total Steps: " + stepSummary.getTotalSteps());
                    System.out.println("Calories Burned: " + stepSummary.getCaloriesBurned());
                    System.out.println("Active Time (minutes): " + stepSummary.getTotalActiveTime());
                    System.out.println("Feedback: " + stepSummary.getFeedback());
                }

                @Override
                public void onError(Throwable t) {
                    // Printing error message and count down latch
                    System.err.println("Error in receiving summary: " + t);
                    latch.countDown();
                }

                @Override
                public void onCompleted() {
                    // Server finished sending response, count down latch
                    System.out.println("Server completed sending response");
                    latch.countDown();
                }
            };

            // Start client streaming call to server with response observer
            StreamObserver<StepData> requestObserver = asyncStub.trackSteps(responseObserver);

            // Array of steps to send to the server
            int[] stepsArray = {
                    900, 450, 500, 600, 950, 700, 800, 250, 400, 550
            };

            // Random hour for feedback time (user sets this data initially, to receive a feedback at specific hour)
            int timeForFeedback = (int) (Math.random() * 24) + 1;

            try {
                // Loop to send steps array to the server with delay of 1 sec
                for (int steps : stepsArray) {
                    
                    if(ClientIdInterceptor.stopStreamInvalidID){
                        break;
                    }
                    
                    // Building stepData message with steps and timeForFeedback
                    StepData stepData = StepData.newBuilder()
                            .setSteps(steps)
                            .setTimeForFeedback(timeForFeedback)
                            .build();

                    System.out.println("Sending steps: " + steps + " at " + LocalTime.now());
                    // Sending message to server
                    requestObserver.onNext(stepData);
                    // Wait 1 second between sending
                    Thread.sleep(2000);
                }
            } catch (RuntimeException | InterruptedException e) {
                // Printing stack trace and message error
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            System.out.println("---------------------------------");

            // Determining if it is AM/PM, based on random hour generated before
            String amPm = "";
            if (timeForFeedback >= 0 && timeForFeedback < 12) {
                amPm = "AM";
            } else if (timeForFeedback >= 12 && timeForFeedback <= 23) {
                amPm = "PM";
            }

            if(!ClientIdInterceptor.stopStreamInvalidID){
                System.out.println("Random hour: " + timeForFeedback + " " + amPm);
            }
            
            // Finishing sending
            requestObserver.onCompleted();

            // Wait for response or timeout
            latch.await(10, TimeUnit.SECONDS);

            // Shutdown channel
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (IOException e){
            // Printing exception if discovery or connection fails
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
