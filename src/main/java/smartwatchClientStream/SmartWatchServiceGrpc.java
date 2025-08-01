package smartwatchClientStream;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: smartwatchClientStream.proto")
public final class SmartWatchServiceGrpc {

  private SmartWatchServiceGrpc() {}

  public static final String SERVICE_NAME = "smartwatchClientStream.SmartWatchService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<smartwatchClientStream.SmartwatchClientStream.StepData,
      smartwatchClientStream.SmartwatchClientStream.StepSummary> getTrackStepsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "trackSteps",
      requestType = smartwatchClientStream.SmartwatchClientStream.StepData.class,
      responseType = smartwatchClientStream.SmartwatchClientStream.StepSummary.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<smartwatchClientStream.SmartwatchClientStream.StepData,
      smartwatchClientStream.SmartwatchClientStream.StepSummary> getTrackStepsMethod() {
    io.grpc.MethodDescriptor<smartwatchClientStream.SmartwatchClientStream.StepData, smartwatchClientStream.SmartwatchClientStream.StepSummary> getTrackStepsMethod;
    if ((getTrackStepsMethod = SmartWatchServiceGrpc.getTrackStepsMethod) == null) {
      synchronized (SmartWatchServiceGrpc.class) {
        if ((getTrackStepsMethod = SmartWatchServiceGrpc.getTrackStepsMethod) == null) {
          SmartWatchServiceGrpc.getTrackStepsMethod = getTrackStepsMethod = 
              io.grpc.MethodDescriptor.<smartwatchClientStream.SmartwatchClientStream.StepData, smartwatchClientStream.SmartwatchClientStream.StepSummary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "smartwatchClientStream.SmartWatchService", "trackSteps"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  smartwatchClientStream.SmartwatchClientStream.StepData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  smartwatchClientStream.SmartwatchClientStream.StepSummary.getDefaultInstance()))
                  .setSchemaDescriptor(new SmartWatchServiceMethodDescriptorSupplier("trackSteps"))
                  .build();
          }
        }
     }
     return getTrackStepsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SmartWatchServiceStub newStub(io.grpc.Channel channel) {
    return new SmartWatchServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SmartWatchServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SmartWatchServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SmartWatchServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SmartWatchServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SmartWatchServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<smartwatchClientStream.SmartwatchClientStream.StepData> trackSteps(
        io.grpc.stub.StreamObserver<smartwatchClientStream.SmartwatchClientStream.StepSummary> responseObserver) {
      return asyncUnimplementedStreamingCall(getTrackStepsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTrackStepsMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                smartwatchClientStream.SmartwatchClientStream.StepData,
                smartwatchClientStream.SmartwatchClientStream.StepSummary>(
                  this, METHODID_TRACK_STEPS)))
          .build();
    }
  }

  /**
   */
  public static final class SmartWatchServiceStub extends io.grpc.stub.AbstractStub<SmartWatchServiceStub> {
    private SmartWatchServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartWatchServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartWatchServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartWatchServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<smartwatchClientStream.SmartwatchClientStream.StepData> trackSteps(
        io.grpc.stub.StreamObserver<smartwatchClientStream.SmartwatchClientStream.StepSummary> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getTrackStepsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class SmartWatchServiceBlockingStub extends io.grpc.stub.AbstractStub<SmartWatchServiceBlockingStub> {
    private SmartWatchServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartWatchServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartWatchServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartWatchServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class SmartWatchServiceFutureStub extends io.grpc.stub.AbstractStub<SmartWatchServiceFutureStub> {
    private SmartWatchServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartWatchServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartWatchServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartWatchServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_TRACK_STEPS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SmartWatchServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SmartWatchServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TRACK_STEPS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.trackSteps(
              (io.grpc.stub.StreamObserver<smartwatchClientStream.SmartwatchClientStream.StepSummary>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SmartWatchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SmartWatchServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return smartwatchClientStream.SmartwatchClientStream.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SmartWatchService");
    }
  }

  private static final class SmartWatchServiceFileDescriptorSupplier
      extends SmartWatchServiceBaseDescriptorSupplier {
    SmartWatchServiceFileDescriptorSupplier() {}
  }

  private static final class SmartWatchServiceMethodDescriptorSupplier
      extends SmartWatchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SmartWatchServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SmartWatchServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SmartWatchServiceFileDescriptorSupplier())
              .addMethod(getTrackStepsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
