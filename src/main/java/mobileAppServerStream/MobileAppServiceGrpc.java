package mobileAppServerStream;

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
    comments = "Source: mobileAppServerStream.proto")
public final class MobileAppServiceGrpc {

  private MobileAppServiceGrpc() {}

  public static final String SERVICE_NAME = "mobileAppServerStream.MobileAppService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mobileAppServerStream.MobileAppServerStream.HydrationRequest,
      mobileAppServerStream.MobileAppServerStream.HydrationNotification> getHydrationReminderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hydrationReminder",
      requestType = mobileAppServerStream.MobileAppServerStream.HydrationRequest.class,
      responseType = mobileAppServerStream.MobileAppServerStream.HydrationNotification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mobileAppServerStream.MobileAppServerStream.HydrationRequest,
      mobileAppServerStream.MobileAppServerStream.HydrationNotification> getHydrationReminderMethod() {
    io.grpc.MethodDescriptor<mobileAppServerStream.MobileAppServerStream.HydrationRequest, mobileAppServerStream.MobileAppServerStream.HydrationNotification> getHydrationReminderMethod;
    if ((getHydrationReminderMethod = MobileAppServiceGrpc.getHydrationReminderMethod) == null) {
      synchronized (MobileAppServiceGrpc.class) {
        if ((getHydrationReminderMethod = MobileAppServiceGrpc.getHydrationReminderMethod) == null) {
          MobileAppServiceGrpc.getHydrationReminderMethod = getHydrationReminderMethod = 
              io.grpc.MethodDescriptor.<mobileAppServerStream.MobileAppServerStream.HydrationRequest, mobileAppServerStream.MobileAppServerStream.HydrationNotification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "mobileAppServerStream.MobileAppService", "hydrationReminder"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mobileAppServerStream.MobileAppServerStream.HydrationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mobileAppServerStream.MobileAppServerStream.HydrationNotification.getDefaultInstance()))
                  .setSchemaDescriptor(new MobileAppServiceMethodDescriptorSupplier("hydrationReminder"))
                  .build();
          }
        }
     }
     return getHydrationReminderMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MobileAppServiceStub newStub(io.grpc.Channel channel) {
    return new MobileAppServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MobileAppServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MobileAppServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MobileAppServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MobileAppServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MobileAppServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void hydrationReminder(mobileAppServerStream.MobileAppServerStream.HydrationRequest request,
        io.grpc.stub.StreamObserver<mobileAppServerStream.MobileAppServerStream.HydrationNotification> responseObserver) {
      asyncUnimplementedUnaryCall(getHydrationReminderMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHydrationReminderMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                mobileAppServerStream.MobileAppServerStream.HydrationRequest,
                mobileAppServerStream.MobileAppServerStream.HydrationNotification>(
                  this, METHODID_HYDRATION_REMINDER)))
          .build();
    }
  }

  /**
   */
  public static final class MobileAppServiceStub extends io.grpc.stub.AbstractStub<MobileAppServiceStub> {
    private MobileAppServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MobileAppServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MobileAppServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MobileAppServiceStub(channel, callOptions);
    }

    /**
     */
    public void hydrationReminder(mobileAppServerStream.MobileAppServerStream.HydrationRequest request,
        io.grpc.stub.StreamObserver<mobileAppServerStream.MobileAppServerStream.HydrationNotification> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getHydrationReminderMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MobileAppServiceBlockingStub extends io.grpc.stub.AbstractStub<MobileAppServiceBlockingStub> {
    private MobileAppServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MobileAppServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MobileAppServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MobileAppServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<mobileAppServerStream.MobileAppServerStream.HydrationNotification> hydrationReminder(
        mobileAppServerStream.MobileAppServerStream.HydrationRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getHydrationReminderMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MobileAppServiceFutureStub extends io.grpc.stub.AbstractStub<MobileAppServiceFutureStub> {
    private MobileAppServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MobileAppServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MobileAppServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MobileAppServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_HYDRATION_REMINDER = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MobileAppServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MobileAppServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HYDRATION_REMINDER:
          serviceImpl.hydrationReminder((mobileAppServerStream.MobileAppServerStream.HydrationRequest) request,
              (io.grpc.stub.StreamObserver<mobileAppServerStream.MobileAppServerStream.HydrationNotification>) responseObserver);
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

  private static abstract class MobileAppServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MobileAppServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mobileAppServerStream.MobileAppServerStream.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MobileAppService");
    }
  }

  private static final class MobileAppServiceFileDescriptorSupplier
      extends MobileAppServiceBaseDescriptorSupplier {
    MobileAppServiceFileDescriptorSupplier() {}
  }

  private static final class MobileAppServiceMethodDescriptorSupplier
      extends MobileAppServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MobileAppServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MobileAppServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MobileAppServiceFileDescriptorSupplier())
              .addMethod(getHydrationReminderMethod())
              .build();
        }
      }
    }
    return result;
  }
}
