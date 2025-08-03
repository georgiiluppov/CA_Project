package mobileAppServerStream;

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

public class MobileAppServer extends MobileAppServiceGrpc.MobileAppServiceImplBase {
    private static final Logger logger = Logger.getLogger(MobileAppServer.class.getName());

    public static void main(String[] args) {
        try {
            Server server = ServerBuilder
                    .forPort(50053)
                    .addService(new MobileAppServer())
                    .build();

            System.out.println("Starting MobileApp gRPC server on port 50053");
            server.start();

            ServiceRegistration
                    .getInstance()
                    .registerService("_mobileApp._tcp.local.", "MobileApp", 50053,
                            "gRPC MobileApp hydration reminder service");

            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hydrationReminder(HydrationRequest request, StreamObserver<HydrationNotification> responseObserver) {
        double intervalMinutes = request.getIntervalMinutes();

        try {
            for (int i = 0; i < 10; i++) {
                HydrationNotification notification = HydrationNotification.newBuilder()
                        .setMessage("Time to drink water! " + LocalTime.now())
                        .build();

                System.out.println("Sending reminder " + (i + 1) + " at " + LocalTime.now());
                responseObserver.onNext(notification);

                if (i < 9) {
                    Thread.sleep((long) (intervalMinutes * 10 * 1000));
                }
            }
            responseObserver.onCompleted();
        } catch (InterruptedException e) {
            logger.info("Closing stream");
            System.out.println("Closing stream");
            responseObserver.onCompleted();
        }
    }
}
