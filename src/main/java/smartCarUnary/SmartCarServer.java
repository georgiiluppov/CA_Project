package smartCarUnary;

import RegistryDiscovery.ServiceRegistration;
import com.generated.grpc.AccidentAlertRequest;
import com.generated.grpc.AccidentAlertResponse;
import com.generated.grpc.SmartCarServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmartCarServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        SmartCarServiceImpl heartRateServer = new SmartCarServiceImpl();

        try {
            // Building the server
            Server server = ServerBuilder
                    .forPort(port)
                    .addService(heartRateServer)
                    .build();

            System.out.println("Starting SmartCar gRPC server on port " + port);
            // Start the server
            server.start();

            // Register service with discovery system
            ServiceRegistration
                    .getInstance()
                    .registerService("_smartCar._tcp.local.", "SmartCarService", 50051, "gRPC SmartCar accident service");
            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            // Printing stack trace and error message
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static class SmartCarServiceImpl extends SmartCarServiceGrpc.SmartCarServiceImplBase {
        @Override
        public void sendAccidentAlert(AccidentAlertRequest request, StreamObserver<AccidentAlertResponse> responseObserver) {
            try {
                // Remote error handling in case if error status if empty or null
                if (request.getStatus() == null || request.getStatus().isEmpty()) {
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT
                                    .withDescription("Status cannot be empty")
                                    .asRuntimeException()
                    );
                    return;
                }

                // Get timestamp from request
                long timestampSeconds = request.getTimestamp();
                // Convert to Date object (milliseconds)
                Date date = new Date(timestampSeconds * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // Format date as a string
                String formattedTime = sdf.format(date);

                System.out.println("Received request: " + formattedTime);

                // Assign message string depending on severity (random num from the client class)
                String message = " ";
                if (request.getStatus().equalsIgnoreCase("Serious")) {
                    message = "Status received: " + request.getStatus() + ". Sending details to emergency services.";
                } else if (request.getStatus().equalsIgnoreCase("Minor")) {
                    message = "Status received: " + request.getStatus() + ". Please confirm if you require emergency services.";
                }

                System.out.println("Sending response: " + formattedTime);

                String responseMsg = "Alert: [" + formattedTime + "] at " + request.getLocation()
                        + ". " + message;

                // Building response object
                AccidentAlertResponse response = AccidentAlertResponse.newBuilder()
                        .setMessage(responseMsg)
                        .build();

                // Sending the response to the client
                responseObserver.onNext(response);
                // Complete call
                responseObserver.onCompleted();
            } catch (Exception e){
                // Sending internal server error status
                responseObserver.onError(
                        Status.INTERNAL
                                .withDescription("Server error")
                                .withCause(e)
                                .asRuntimeException()
                );

                // Printing stack trace and error message
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
