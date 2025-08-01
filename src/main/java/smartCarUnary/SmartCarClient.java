package smartCarUnary;

import com.generated.grpc.AccidentAlertRequest;
import com.generated.grpc.AccidentAlertResponse;
import com.generated.grpc.SmartCarServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SmartCarClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        SmartCarServiceGrpc.SmartCarServiceBlockingStub stub = SmartCarServiceGrpc.newBlockingStub(channel);

        AccidentAlertRequest request = AccidentAlertRequest.newBuilder()
                .setTimestamp((int) (System.currentTimeMillis() / 1000))
                .setLocation("53.34919551832582, -6.241966724377")
                .setStatus("Serious")
                .build();

        AccidentAlertResponse response = stub.sendAccidentAlert(request);
        System.out.println(response.getMessage());

        channel.shutdown();
    }
}
