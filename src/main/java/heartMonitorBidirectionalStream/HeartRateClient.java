package heartMonitorBidirectionalStream;

import com.generated.grpc.HeartRateServiceGrpc;
import com.generated.grpc.HeartRate;
import com.generated.grpc.HeartRateFeedback;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalTime;

public class HeartRateClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        HeartRateServiceGrpc.HeartRateServiceStub asyncStub = HeartRateServiceGrpc.newStub(channel);

        StreamObserver<HeartRate> requestObserver = asyncStub.monitorHeartRate(new StreamObserver<HeartRateFeedback>() {
            @Override
            public void onNext(HeartRateFeedback feedback) {
                System.out.println("Server feedback: " + feedback.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Server finished sending feedback.");
            }
        });

        boolean run = true;

        while (run) {
            int bpm = 10 + (int) (Math.random() * 241);
            System.out.println("Sending BPM: " + bpm + ". At: " + LocalTime.now());
            HeartRate heartRate = HeartRate.newBuilder().setBpm(bpm).build();
            requestObserver.onNext(heartRate);
            Thread.sleep(10000);
        }

        requestObserver.onCompleted();
        channel.shutdown();
    }
}
