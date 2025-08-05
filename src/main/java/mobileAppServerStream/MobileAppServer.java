package mobileAppServerStream;

import GUI.GUIMobileApp;
import RegistryDiscovery.ServiceRegistration;
import com.generated.grpc.HydrationNotification;
import com.generated.grpc.HydrationRequest;
import com.generated.grpc.MobileAppServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Logger;
import static smartCarUnary.SmartCarClient.severityFromGUI;

public class MobileAppServer extends MobileAppServiceGrpc.MobileAppServiceImplBase {
    private static final Logger logger = Logger.getLogger(MobileAppServer.class.getName());
    public static Integer secFromGUI = null;
    
    public static void main(String[] args) {
        int port = 50053;
        MobileAppServer mobileAppServer = new MobileAppServer();

        try {
            // Building the server
            Server server = ServerBuilder
                    .forPort(port)
                    .addService(mobileAppServer)
                    .build();

            System.out.println("Starting MobileApp gRPC server on port 50053");
            // Start the server
            server.start();

            // Register service with discovery system
            ServiceRegistration
                    .getInstance()
                    .registerService("_mobileApp._tcp.local.", "MobileApp", 50053,
                            "gRPC MobileApp hydration reminder service");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            // Printing stack trace and error message
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void hydrationReminder(HydrationRequest request, StreamObserver<HydrationNotification> responseObserver) {
        double intervalMinutes = request.getIntervalMinutes();

        int sec;
        if (secFromGUI != null) {
            sec = secFromGUI;
        } else {
            // Random sec between 1 and 10
            sec = (int) (Math.random() * 10) + 1;
        }
        
        System.out.println("Reminder will be sent every " + sec + " sec.");
        
        try {
            // Loop to keep the server sending messages only 10 times, not indefinitely
            for (int i = 0; i < 30; i++) {
                // Build the notification message with current time
                HydrationNotification notification = HydrationNotification.newBuilder()
                        .setMessage("Time to drink water! " + LocalTime.now())
                        .build();

                // Sending notification to the client
                System.out.println("Sending reminder " + (i + 1) + " at " + LocalTime.now());
                responseObserver.onNext(notification);

                // Simulate interval delay between reminders (interval * 10 * 100ms)
                Thread.sleep((long) (sec * 10 * 100));

                // This part is to simulate server delay longer than deadline of the client (65s)
                if (GUIMobileApp.simulateDelayGUI){
                    Thread.sleep((long) (15 * 10 * 100));
                    break;
                }
            }

            // Streaming is complete
            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            logger.info("Closing stream");
            System.out.println("Closing stream");
            // Printing stack trace and error message
            System.out.println(e.getMessage());
            responseObserver.onCompleted();
        }
    }
}
