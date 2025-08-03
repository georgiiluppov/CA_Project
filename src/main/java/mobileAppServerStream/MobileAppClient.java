package mobileAppServerStream;

import com.generated.grpc.HydrationNotification;
import com.generated.grpc.HydrationRequest;
import com.generated.grpc.MobileAppServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class MobileAppClient {
    public static void main(String[] args) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            Thread.sleep(3000);
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_mobileApp._tcp.local.", "MobileApp");
            jmdns.close();

            if (serviceInfo == null) {
                System.err.println("MobileApp gRPC service not found.");
                return;
            }

            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered MobileApp service at " + host + " Port: " + port);

            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            MobileAppServiceGrpc.MobileAppServiceStub asyncStub = MobileAppServiceGrpc.newStub(channel);

            try {
                HydrationRequest request = HydrationRequest.newBuilder()
                        .setIntervalMinutes(1)
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
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}