package heartMonitorBidirectionalStream;

import com.generated.grpc.HeartRate;
import com.generated.grpc.HeartRateFeedback;
import com.generated.grpc.HeartRateServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class HeartRateClient {
    public static void main(String[] args) throws InterruptedException {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            Thread.sleep(3000);
            ServiceInfo serviceInfo = jmdns.getServiceInfo("_heartMonitor._tcp.local.", "HeartMonitor");
            jmdns.close();

            if (serviceInfo == null) {
                System.err.println("HeartMonitor gRPC service not found.");
                return;
            }

            String host = serviceInfo.getHostAddresses()[0];
            int port = serviceInfo.getPort();
            System.out.println("Discovered HeartMonitor service at " + host + " Port: " + port);

            ManagedChannel channel = ManagedChannelBuilder
                    .forAddress(host, port)
                    .usePlaintext()
                    .build();

            HeartRateServiceGrpc.HeartRateServiceStub asyncStub = HeartRateServiceGrpc.newStub(channel);

            StreamObserver<HeartRate> requestObserver = asyncStub.monitorHeartRate(new StreamObserver<HeartRateFeedback>() {
                @Override
                public void onNext(HeartRateFeedback feedback) {
                    System.out.println("Server feedback: " + feedback.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Server finished sending feedback.");
                }
            });

            for (int i = 0; i < 10; i++) {
                int bpm = 10 + (int) (Math.random() * 241);
                System.out.println("Sending BPM: " + bpm + " at: " + LocalTime.now());
                HeartRate heartRate = HeartRate.newBuilder().setBpm(bpm).build();
                requestObserver.onNext(heartRate);

                if (i < 9) {
                    Thread.sleep(10000);
                }
            }

            requestObserver.onCompleted();
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
    }
}
