package smartwatchClientStream;

import RegistryDiscovery.ServiceRegistration;
import com.generated.grpc.SmartWatchServiceGrpc;
import com.generated.grpc.StepData;
import com.generated.grpc.StepSummary;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SmartWatchServer extends SmartWatchServiceGrpc.SmartWatchServiceImplBase {
    private static final Logger logger = Logger.getLogger(SmartWatchServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        SmartWatchServer smartWatchServer = new SmartWatchServer();
        int port = 50052;

        try {
            // Build gRPC server with client ID interceptor and service implementation
            Server server = ServerBuilder.forPort(port)
                    .intercept(new ClientIdInterceptor())
                    .addService(smartWatchServer)
                    .build()
                    .start();

            logger.info("Server started, listening on " + port);
            System.out.println("Server started, listening on " + port);

            // Register service for discovery on local network
            ServiceRegistration
                    .getInstance()
                    .registerService("_smartWatch._tcp.local.", "SmartWatch", port, "SmartWatch gRPC client-streaming");

            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            // Printing stack trace and message error
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    // Implementation of client streaming gRPC method
    @Override
    public StreamObserver<StepData> trackSteps(StreamObserver<StepSummary> responseObserver) {
        return new StreamObserver<StepData>() {
            // List to store steps received from the client
            ArrayList<Integer> stepsList = new ArrayList<>();
            // Hour variable for feedback
            int hour = 0;

            @Override
            public void onNext(StepData stepData) {
                // Update hour from latest message
                hour = stepData.getTimeForFeedback();
                // Add steps to list
                stepsList.add(stepData.getSteps());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error receiving steps: " + t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Step data stream completed.");
                // Calculate total steps
                int totalSteps = 0;
                for (int steps : stepsList) {
                    totalSteps += steps;
                }

                // Estimate calories burned
                double calories = totalSteps * 0.1;
                // Estimate active time in minutes
                int activeTime = totalSteps / 100;
                String feedback;

                // Provide feedback based on time (the client set to receive) and total steps by that moment
                if (hour < 21) {
                    if (totalSteps < 5000) {
                        feedback = "Try to walk more, you still have time today! Total steps: " + totalSteps;
                    } else {
                        feedback = "Nice job today! You still have time to break records. Total steps: " + totalSteps;
                    }
                } else {
                    if (totalSteps < 5000) {
                        feedback = "It is late, but try to move a bit more tomorrow! Total steps: " + totalSteps;
                    } else {
                        feedback = "Nice job today! Total steps: " + totalSteps;
                    }
                }

                // Building summary message to send back to the client
                StepSummary summary = StepSummary.newBuilder()
                        .setTotalSteps(totalSteps)
                        .setCaloriesBurned(calories)
                        .setTotalActiveTime(activeTime)
                        .setFeedback(feedback)
                        .build();

                System.out.println("Sending summary response at " + LocalTime.now());

                // Sending summary to the client
                responseObserver.onNext(summary);
                // Finishing call
                responseObserver.onCompleted();
            }
        };
    }
}
