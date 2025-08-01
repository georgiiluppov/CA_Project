package smartwatchClientStream;

import com.generated.grpc.SmartWatchServiceGrpc;
import com.generated.grpc.StepData;
import com.generated.grpc.StepSummary;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SmartWatchClient {
    private static SmartWatchServiceGrpc.SmartWatchServiceStub asyncStub;

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        asyncStub = SmartWatchServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<StepSummary> responseObserver = new StreamObserver<StepSummary>() {
            @Override
            public void onNext(StepSummary stepSummary) {
                System.out.println("Summary received:");
                System.out.println("Total Steps: " + stepSummary.getTotalSteps());
                System.out.println("Calories Burned: " + stepSummary.getCaloriesBurned());
                System.out.println("Active Time (minutes): " + stepSummary.getTotalActiveTime());
                System.out.println("Feedback: " + stepSummary.getFeedback());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in receiving summary: " + t);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Server completed sending response");
                latch.countDown();
            }
        };

        StreamObserver<StepData> requestObserver = asyncStub.trackSteps(responseObserver);

        int[] stepsArray = {
                90, 45, 50, 600, 35, 70, 80, 25, 400, 55,
                100, 120, 90, 30, 20, 75, 65, 500, 60, 40,
                20, 55, 900, 700, 35, 80, 40, 95, 50, 750
        };

        try {
            for (int steps : stepsArray) {
                StepData stepData = StepData.newBuilder()
                        .setSteps(steps)
                        .build();
                requestObserver.onNext(stepData);
                Thread.sleep(100);
            }
        } catch (RuntimeException | InterruptedException e) {
            e.printStackTrace();
        }

        requestObserver.onCompleted();

        latch.await(20, TimeUnit.SECONDS);
        channel.shutdown();
    }
}
