package mobileAppServerStream;

import com.generated.grpc.HydrationNotification;
import com.generated.grpc.HydrationRequest;
import com.generated.grpc.MobileAppServiceGrpc;
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

public class MobileAppClient {
    public static void main(String[] args) {
        try {
            // Creating JmDNS instance for local host
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Wait a bit for discovery (without this line client cannot find service if
            // both client/server have been started simultaneously
            Thread.sleep(3000);
            // Lookup service info by type and name
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_mobileApp._tcp.local.", "MobileApp");
            jmdns.close();

            // If service was not found, print error and stop
            if (serviceInfo == null) {
                System.err.println("MobileApp gRPC service not found.");
                return;
            }

            // Getting host IP and port from service info
            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered MobileApp service at " + host + " Port: " + port);

            // Create gRPC channel to connect to the server
            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            // Adding deadline of 60 seconds to stub (if server does not respond, it will stop)
            MobileAppServiceGrpc.MobileAppServiceStub asyncStub = MobileAppServiceGrpc.newStub(channel)
                            .withDeadlineAfter(60, TimeUnit.SECONDS);

            try {
                // Building request with interval (in this case just every sec to demonstrate)
                HydrationRequest request = HydrationRequest.newBuilder()
                        .setIntervalMinutes(1)
                        .build();

                // Define what the client does when it receives messages from the server
                StreamObserver<HydrationNotification> responseObserver = new StreamObserver<HydrationNotification>() {
                    @Override
                    public void onNext(HydrationNotification notification) {
                        System.out.println("Client received reminder at "
                                + LocalTime.now() + ": " + notification.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) {
                        // If an error occurs during the stream (long waiting etc)
                        if (Status.fromThrowable(t).getCode() == Status.Code.DEADLINE_EXCEEDED) {
                            System.err.println("Request timed out: server took too long to respond.");
                        } else {
                            System.err.println("Error during hydration reminder: " + t.getMessage());
                        }
                    }

                    @Override
                    public void onCompleted() {
                        // Finishing sending messages
                        System.out.println("Finishing stream");
                    }
                };

                // Send the hydration reminder request to the server
                asyncStub.hydrationReminder(request, responseObserver);
                System.out.println(LocalTime.now() + " Client has sent hydration reminder request.");

                // Keep the client alive to continue receiving streamed messages
                Thread.sleep(100000);
                // Shut down the gRPC channel
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // Printing stack trace and error message
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e){
            // Printing stack trace and error message
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}