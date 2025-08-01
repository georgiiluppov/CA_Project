package smartCarUnary;

import com.generated.grpc.AccidentAlertRequest;
import com.generated.grpc.AccidentAlertResponse;
import com.generated.grpc.SmartCarServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmartCarServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Server server = ServerBuilder.forPort(9090)
                    .addService(new SmartCarServiceImpl())
                    .build();

            System.out.println("Starting SmartCar gRPC server on port 9090");
            server.start();
            server.awaitTermination();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public static class SmartCarServiceImpl extends SmartCarServiceGrpc.SmartCarServiceImplBase {
        @Override
        public void sendAccidentAlert(AccidentAlertRequest request, StreamObserver<AccidentAlertResponse> responseObserver) {
            // Convert timestamp (seconds) to formatted date-time string
            long timestampSeconds = request.getTimestamp();
            Date date = new Date(timestampSeconds * 1000L); // Convert to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTime = sdf.format(date);

            String responseMsg = "Alert: [" + formattedTime + "] at " + request.getLocation()
                    + " with status: " + request.getStatus()
                    + ". Sending details to emergency services.";

            AccidentAlertResponse response = AccidentAlertResponse.newBuilder()
                    .setMessage(responseMsg)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
