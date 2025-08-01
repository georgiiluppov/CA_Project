package smartwatchClientStream;

import com.generated.grpc.SmartWatchServiceGrpc;
import com.generated.grpc.StepData;
import com.generated.grpc.StepSummary;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SmartWatchServer extends SmartWatchServiceGrpc.SmartWatchServiceImplBase {
    private static final Logger logger = Logger.getLogger(SmartWatchServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        SmartWatchServer smartWatchServer = new SmartWatchServer();
        int port = 50051;

        try {
            Server server = ServerBuilder.forPort(port)
                    .addService(smartWatchServer)
                    .build()
                    .start();

            logger.info("Server started, listening on " + port);
            System.out.println("Server started, listening on " + port);

            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public StreamObserver<StepData> trackSteps(StreamObserver<StepSummary> responseObserver) {
        return new StreamObserver<StepData>() {
            ArrayList<Integer> stepsList = new ArrayList<>();

            @Override
            public void onNext(StepData stepData) {

                stepsList.add(stepData.getSteps());
                System.out.println("Received steps: " + stepData.getSteps());
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

                if (totalSteps < 5000) {
                    feedback = "Try to walk more! Total steps: " + totalSteps;
                } else {
                    feedback = "Good job! Total steps: " + totalSteps;
                }

                StepSummary summary = StepSummary.newBuilder()
                        .setTotalSteps(totalSteps)
                        .setCaloriesBurned(calories)
                        .setTotalActiveTime(activeTime)
                        .setFeedback(feedback)
                        .build();

                responseObserver.onNext(summary);
                responseObserver.onCompleted();
            }
        };
    }
}
