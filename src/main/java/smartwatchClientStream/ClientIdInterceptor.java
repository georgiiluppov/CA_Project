package smartwatchClientStream;
import io.grpc.*;

public class ClientIdInterceptor implements ServerInterceptor {
    // Interceptor to check client ID from request metadata (in case if it is empty)
    // or for the future work, like sorting users etc
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next
    ) {
        // Getting client-id value
        String clientId = headers.get(Metadata.Key.of("client-id", Metadata.ASCII_STRING_MARSHALLER));
        System.out.println("Received client-id: " + clientId);

        // If clientId is missing or empty, reject the call
        if (clientId == null || clientId.isEmpty()) {
            call.close(Status.PERMISSION_DENIED.withDescription("Invalid client ID"), new Metadata());
            // Return empty listener to stop call processing
            return new ServerCall.Listener<ReqT>() {};
        }

        // Continue processing if clientId is valid
        return next.startCall(call, headers);
    }
}
