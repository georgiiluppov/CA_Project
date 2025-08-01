package mobileAppServerStream;

import com.generated.grpc.HydrationNotification;
import com.generated.grpc.HydrationRequest;
import com.generated.grpc.MobileAppServiceGrpc.MobileAppServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Logger;

public class MobileAppServer extends MobileAppServiceImplBase {
    private static final Logger logger = Logger.getLogger(MobileAppServer.class.getName());

    public static void main(String[] args) {
        int port = 50051;
        MobileAppServer mobileAppServer = new MobileAppServer();

        try {
            Server server = ServerBuilder
                    .forPort(port)
                    .addService(mobileAppServer)
                    .build()
                    .start();

            logger.info("Server started, listening on " + port);
            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void hydrationReminder(HydrationRequest request, StreamObserver<HydrationNotification> responseObserver) {
        double intervalMinutes = request.getIntervalMinutes();
        boolean run = true;
        try {
            while (run) {
                HydrationNotification notification = HydrationNotification.newBuilder()
                        .setMessage("Time to drink water! " + LocalTime.now())
                        .build();

                System.out.println("Sending reminder " + LocalTime.now());
                responseObserver.onNext(notification);
                Thread.sleep((long) (intervalMinutes * 100000));
            }
        } catch (InterruptedException e) {
            logger.info("Closing stream");
            responseObserver.onCompleted();
        }
    }
}
