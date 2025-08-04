package heartMonitorBidirectionalStream;

import RegistryDiscovery.ServiceRegistration;
import com.generated.grpc.HeartRate;
import com.generated.grpc.HeartRateFeedback;
import com.generated.grpc.HeartRateServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Logger;

public class HeartRateServer extends HeartRateServiceGrpc.HeartRateServiceImplBase {
    private static final Logger logger = Logger.getLogger(HeartRateServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50054;
        HeartRateServer heartRateServer = new HeartRateServer();

        try {
            // Building the server
            Server server = ServerBuilder
                    .forPort(port)
                    .addService(heartRateServer)
                    .build();

            System.out.println("Starting HeartMonitor gRPC server on port " + port);
            // Start the server
            server.start();

            // Register service with discovery system
            ServiceRegistration
                    .getInstance()
                    .registerService("_heartMonitor._tcp.local.", "HeartMonitor", 50054, "gRPC HeartMonitor accident service");
            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            // Printing stack trace and error message
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public StreamObserver<HeartRate> monitorHeartRate(StreamObserver<HeartRateFeedback> responseObserver) {
        // Return a new StreamObserver to handle incoming HeartRate messages from client
        return new StreamObserver<HeartRate>() {

            // Called each time the client sends a new HeartRate message
            @Override
            public void onNext(HeartRate heartRate) {
                int bpm = heartRate.getBpm();
                String feedback;

                // Determine feedback message based on bpm ranges
                if (bpm >= 120 && bpm < 200) {
                    feedback = "Heart rate is too high. Time to slow down";
                } else if(bpm >= 30 && bpm < 60){
                    feedback = "Heart rate is too low. Time to wake up";
                } else if (bpm >= 60 && bpm < 120){
                    feedback = "Heart rate is normal";
                } else {
                    feedback = "Dangerous zone. Please inform us if we need to call emergency services";
                }

                // Building a HeartRateFeedback message with the bpm and feedback text
                HeartRateFeedback response = HeartRateFeedback.newBuilder()
                        .setMessage("Current BPM: " + bpm + " " + feedback)
                        .build();

                // Sending feedback to the client
                System.out.println("Sending response at " + LocalTime.now());
                responseObserver.onNext(response);
            }

            // Called if there is an error in the stream
            @Override
            public void onError(Throwable t) {
                // If the client cancelled the stream
                if (Status.fromThrowable(t).getCode() == Status.Code.CANCELLED) {
                    System.out.println("Stream cancelled by client.");
                    logger.info("Stream cancelled by client.");
                } else {
                    // Log any other errors
                    System.err.println("Error: " + t.getMessage());
                    logger.warning("Error: " + t.getMessage());
                }
            }

            // Client has finished sending all HeartRate messages
            @Override
            public void onCompleted() {
                logger.info("Client has finished sending heart rates");
                System.out.println("Client has finished sending heart rates");
                // Finishing call
                responseObserver.onCompleted();
            }
        };
    }
}
