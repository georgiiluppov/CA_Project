package mobileAppServerStream;

import com.generated.grpc.HydrationNotification;
import com.generated.grpc.HydrationRequest;
import com.generated.grpc.MobileAppServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class MobileAppClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 50051;

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        MobileAppServiceGrpc.MobileAppServiceStub asyncStub = MobileAppServiceGrpc.newStub(channel);

        try {
            HydrationRequest request = HydrationRequest.newBuilder()
                    .setIntervalMinutes(0.1)
                    .build();

            StreamObserver<HydrationNotification> responseObserver = new StreamObserver<HydrationNotification>() {
                @Override
                public void onNext(HydrationNotification notification) {
                    System.out.println("Client received reminder at "
                            + LocalTime.now() + ": " + notification.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Finishing stream");
                }
            };

            asyncStub.hydrationReminder(request, responseObserver);
            System.out.println(LocalTime.now() + " Client has sent hydration reminder request.");
            Thread.sleep(100000);
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
