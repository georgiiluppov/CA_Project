package com.generated.grpc;

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
    comments = "Source: heartMonitorBidirectionalStream.proto")
public final class HeartRateServiceGrpc {

  private HeartRateServiceGrpc() {}

  public static final String SERVICE_NAME = "heartMonitorBidirectionalStream.HeartRateService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.generated.grpc.HeartRate,
      com.generated.grpc.HeartRateFeedback> getMonitorHeartRateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "monitorHeartRate",
      requestType = com.generated.grpc.HeartRate.class,
      responseType = com.generated.grpc.HeartRateFeedback.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.generated.grpc.HeartRate,
      com.generated.grpc.HeartRateFeedback> getMonitorHeartRateMethod() {
    io.grpc.MethodDescriptor<com.generated.grpc.HeartRate, com.generated.grpc.HeartRateFeedback> getMonitorHeartRateMethod;
    if ((getMonitorHeartRateMethod = HeartRateServiceGrpc.getMonitorHeartRateMethod) == null) {
      synchronized (HeartRateServiceGrpc.class) {
        if ((getMonitorHeartRateMethod = HeartRateServiceGrpc.getMonitorHeartRateMethod) == null) {
          HeartRateServiceGrpc.getMonitorHeartRateMethod = getMonitorHeartRateMethod = 
              io.grpc.MethodDescriptor.<com.generated.grpc.HeartRate, com.generated.grpc.HeartRateFeedback>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "heartMonitorBidirectionalStream.HeartRateService", "monitorHeartRate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.generated.grpc.HeartRate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.generated.grpc.HeartRateFeedback.getDefaultInstance()))
                  .setSchemaDescriptor(new HeartRateServiceMethodDescriptorSupplier("monitorHeartRate"))
                  .build();
          }
        }
     }
     return getMonitorHeartRateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HeartRateServiceStub newStub(io.grpc.Channel channel) {
    return new HeartRateServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HeartRateServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new HeartRateServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HeartRateServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new HeartRateServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class HeartRateServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.generated.grpc.HeartRate> monitorHeartRate(
        io.grpc.stub.StreamObserver<com.generated.grpc.HeartRateFeedback> responseObserver) {
      return asyncUnimplementedStreamingCall(getMonitorHeartRateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMonitorHeartRateMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.generated.grpc.HeartRate,
                com.generated.grpc.HeartRateFeedback>(
                  this, METHODID_MONITOR_HEART_RATE)))
          .build();
    }
  }

  /**
   */
  public static final class HeartRateServiceStub extends io.grpc.stub.AbstractStub<HeartRateServiceStub> {
    private HeartRateServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeartRateServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartRateServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeartRateServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.generated.grpc.HeartRate> monitorHeartRate(
        io.grpc.stub.StreamObserver<com.generated.grpc.HeartRateFeedback> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getMonitorHeartRateMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class HeartRateServiceBlockingStub extends io.grpc.stub.AbstractStub<HeartRateServiceBlockingStub> {
    private HeartRateServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeartRateServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartRateServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeartRateServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class HeartRateServiceFutureStub extends io.grpc.stub.AbstractStub<HeartRateServiceFutureStub> {
    private HeartRateServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private HeartRateServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HeartRateServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new HeartRateServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_MONITOR_HEART_RATE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HeartRateServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HeartRateServiceImplBase serviceImpl, int methodId) {
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
        case METHODID_MONITOR_HEART_RATE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.monitorHeartRate(
              (io.grpc.stub.StreamObserver<com.generated.grpc.HeartRateFeedback>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HeartRateServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HeartRateServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.generated.grpc.HeartMonitorBidirectionalStream.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HeartRateService");
    }
  }

  private static final class HeartRateServiceFileDescriptorSupplier
      extends HeartRateServiceBaseDescriptorSupplier {
    HeartRateServiceFileDescriptorSupplier() {}
  }

  private static final class HeartRateServiceMethodDescriptorSupplier
      extends HeartRateServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HeartRateServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (HeartRateServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HeartRateServiceFileDescriptorSupplier())
              .addMethod(getMonitorHeartRateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
