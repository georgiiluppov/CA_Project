package smartwatchClientStream;

import com.generated.grpc.SmartWatchServiceGrpc;
import com.generated.grpc.StepData;
import com.generated.grpc.StepSummary;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SmartWatchClient {
    private static SmartWatchServiceGrpc.SmartWatchServiceStub asyncStub;

    public static void main(String[] args) throws InterruptedException {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_smartWatch._tcp.local.", "SmartWatch");
            jmdns.close();

            if (serviceInfo == null) {
                System.err.println("SmartWatch gRPC service not found.");
                return;
            }

            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered SmartWatch service at " + host + " Port: " + port);

            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
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
                    90, 45, 50, 600, 35, 70, 80, 25, 40, 55,
                    100, 120, 90, 30, 20, 75, 65, 500, 60, 40,
                    20, 55, 90, 700, 35, 80, 40, 95, 50, 750
            };

            int timeForFeedback = (int) (Math.random() * 24) + 1;
            System.out.println("Random hour: " + timeForFeedback);

            try {
                for (int steps : stepsArray) {
                    StepData stepData = StepData.newBuilder()
                            .setSteps(steps)
                            .setTimeForFeedback(timeForFeedback)
                            .build();

                    System.out.println("Sending steps: " + steps + " at " + LocalTime.now());
                    requestObserver.onNext(stepData);
                    Thread.sleep(1000);
                }
            } catch (RuntimeException | InterruptedException e) {
                e.printStackTrace();
            }

            requestObserver.onCompleted();

            latch.await(10, TimeUnit.SECONDS);
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
