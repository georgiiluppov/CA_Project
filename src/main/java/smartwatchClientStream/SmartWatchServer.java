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
            Server server = ServerBuilder.forPort(port)
                    .addService(smartWatchServer)
                    .build()
                    .start();

            logger.info("Server started, listening on " + port);
            System.out.println("Server started, listening on " + port);

            ServiceRegistration
                    .getInstance()
                    .registerService("_smartWatch._tcp.local.", "SmartWatch", port, "SmartWatch gRPC client-streaming");

            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public StreamObserver<StepData> trackSteps(StreamObserver<StepSummary> responseObserver) {
        return new StreamObserver<StepData>() {
            ArrayList<Integer> stepsList = new ArrayList<>();
            int hour = 0;

            @Override
            public void onNext(StepData stepData) {
                hour = stepData.getTimeForFeedback();
                stepsList.add(stepData.getSteps());
                System.out.println("Received steps: " + stepData.getSteps() + ". " + LocalTime.now());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error receiving steps: " + t);
            }

            @Override
            public void onCompleted() {
                System.out.println("Step data stream completed.");

                int totalSteps = 0;
                for (int steps : stepsList) {
                    totalSteps += steps;
                }

                double calories = totalSteps * 0.1;
                int activeTime = totalSteps / 100;
                String feedback;

                if (hour < 23) {
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

                StepSummary summary = StepSummary.newBuilder()
                        .setTotalSteps(totalSteps)
                        .setCaloriesBurned(calories)
                        .setTotalActiveTime(activeTime)
                        .setFeedback(feedback)
                        .build();

                System.out.println("Sending summary response at " + LocalTime.now());

                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }
}
