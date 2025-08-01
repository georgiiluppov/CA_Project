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
    comments = "Source: smartcarUnary.proto")
public final class SmartCarServiceGrpc {

  private SmartCarServiceGrpc() {}

  public static final String SERVICE_NAME = "smartCarUnary.SmartCarService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.generated.grpc.AccidentAlertRequest,
      com.generated.grpc.AccidentAlertResponse> getSendAccidentAlertMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendAccidentAlert",
      requestType = com.generated.grpc.AccidentAlertRequest.class,
      responseType = com.generated.grpc.AccidentAlertResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.generated.grpc.AccidentAlertRequest,
      com.generated.grpc.AccidentAlertResponse> getSendAccidentAlertMethod() {
    io.grpc.MethodDescriptor<com.generated.grpc.AccidentAlertRequest, com.generated.grpc.AccidentAlertResponse> getSendAccidentAlertMethod;
    if ((getSendAccidentAlertMethod = SmartCarServiceGrpc.getSendAccidentAlertMethod) == null) {
      synchronized (SmartCarServiceGrpc.class) {
        if ((getSendAccidentAlertMethod = SmartCarServiceGrpc.getSendAccidentAlertMethod) == null) {
          SmartCarServiceGrpc.getSendAccidentAlertMethod = getSendAccidentAlertMethod = 
              io.grpc.MethodDescriptor.<com.generated.grpc.AccidentAlertRequest, com.generated.grpc.AccidentAlertResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "smartCarUnary.SmartCarService", "sendAccidentAlert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.generated.grpc.AccidentAlertRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.generated.grpc.AccidentAlertResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new SmartCarServiceMethodDescriptorSupplier("sendAccidentAlert"))
                  .build();
          }
        }
     }
     return getSendAccidentAlertMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SmartCarServiceStub newStub(io.grpc.Channel channel) {
    return new SmartCarServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SmartCarServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SmartCarServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SmartCarServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SmartCarServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SmartCarServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendAccidentAlert(com.generated.grpc.AccidentAlertRequest request,
        io.grpc.stub.StreamObserver<com.generated.grpc.AccidentAlertResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSendAccidentAlertMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendAccidentAlertMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.generated.grpc.AccidentAlertRequest,
                com.generated.grpc.AccidentAlertResponse>(
                  this, METHODID_SEND_ACCIDENT_ALERT)))
          .build();
    }
  }

  /**
   */
  public static final class SmartCarServiceStub extends io.grpc.stub.AbstractStub<SmartCarServiceStub> {
    private SmartCarServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartCarServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartCarServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartCarServiceStub(channel, callOptions);
    }

    /**
     */
    public void sendAccidentAlert(com.generated.grpc.AccidentAlertRequest request,
        io.grpc.stub.StreamObserver<com.generated.grpc.AccidentAlertResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendAccidentAlertMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SmartCarServiceBlockingStub extends io.grpc.stub.AbstractStub<SmartCarServiceBlockingStub> {
    private SmartCarServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartCarServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartCarServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartCarServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.generated.grpc.AccidentAlertResponse sendAccidentAlert(com.generated.grpc.AccidentAlertRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendAccidentAlertMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SmartCarServiceFutureStub extends io.grpc.stub.AbstractStub<SmartCarServiceFutureStub> {
    private SmartCarServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SmartCarServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SmartCarServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SmartCarServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.generated.grpc.AccidentAlertResponse> sendAccidentAlert(
        com.generated.grpc.AccidentAlertRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendAccidentAlertMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_ACCIDENT_ALERT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SmartCarServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SmartCarServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_ACCIDENT_ALERT:
          serviceImpl.sendAccidentAlert((com.generated.grpc.AccidentAlertRequest) request,
              (io.grpc.stub.StreamObserver<com.generated.grpc.AccidentAlertResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SmartCarServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SmartCarServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.generated.grpc.SmartcarUnary.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SmartCarService");
    }
  }

  private static final class SmartCarServiceFileDescriptorSupplier
      extends SmartCarServiceBaseDescriptorSupplier {
    SmartCarServiceFileDescriptorSupplier() {}
  }

  private static final class SmartCarServiceMethodDescriptorSupplier
      extends SmartCarServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SmartCarServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SmartCarServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SmartCarServiceFileDescriptorSupplier())
              .addMethod(getSendAccidentAlertMethod())
              .build();
        }
      }
    }
    return result;
  }
}
