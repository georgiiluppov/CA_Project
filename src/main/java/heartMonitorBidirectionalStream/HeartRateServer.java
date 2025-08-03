package heartMonitorBidirectionalStream;

import RegistryDiscovery.ServiceRegistration;
import com.generated.grpc.HeartRateServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import com.generated.grpc.HeartRate;
import com.generated.grpc.HeartRateFeedback;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Logger;

public class HeartRateServer extends HeartRateServiceGrpc.HeartRateServiceImplBase {
    private static final Logger logger = Logger.getLogger(HeartRateServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50054;
        HeartRateServer heartRateServer = new HeartRateServer();

        try {
            Server server = ServerBuilder
                    .forPort(port)
                    .addService(heartRateServer)
                    .build();

            System.out.println("Starting HeartMonitor gRPC server on port " + port);
            server.start();

            ServiceRegistration
                    .getInstance()
                    .registerService("_heartMonitor._tcp.local.", "HeartMonitor", 50054, "gRPC HeartMonitor accident service");
            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public StreamObserver<HeartRate> monitorHeartRate(StreamObserver<HeartRateFeedback> responseObserver) {
        return new StreamObserver<HeartRate>() {
            @Override
            public void onNext(HeartRate heartRate) {
                int bpm = heartRate.getBpm();
                String feedback;

                if (bpm >= 120 && bpm < 200) {
                    feedback = "Heart rate is too high. Time to slow down";
                } else if(bpm >= 30 && bpm < 60){
                    feedback = "Heart rate is too low. Time to wake up";
                } else if (bpm >= 60 && bpm < 120){
                    feedback = "Heart rate is normal";
                } else {
                    feedback = "Dangerous zone. Please inform us if we need to call emergency services";
                }

                HeartRateFeedback response = HeartRateFeedback.newBuilder()
                        .setMessage("Current BPM: " + bpm + " " + feedback)
                        .build();

                System.out.println("Sending response at " + LocalTime.now());
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error: " + t.getMessage());
                logger.warning("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("Client has finished sending heart rates");
                System.out.println("Client has finished sending heart rates");
                responseObserver.onCompleted();
            }
        };
    }
}
