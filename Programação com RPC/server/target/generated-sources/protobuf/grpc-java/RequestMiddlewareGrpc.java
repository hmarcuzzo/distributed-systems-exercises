import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: database.proto")
public final class RequestMiddlewareGrpc {

  private RequestMiddlewareGrpc() {}

  public static final String SERVICE_NAME = "RequestMiddleware";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<Request,
      Response> METHOD_COMUNICATION =
      io.grpc.MethodDescriptor.<Request, Response>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "RequestMiddleware", "Comunication"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              Request.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              Response.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RequestMiddlewareStub newStub(io.grpc.Channel channel) {
    return new RequestMiddlewareStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RequestMiddlewareBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RequestMiddlewareBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RequestMiddlewareFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RequestMiddlewareFutureStub(channel);
  }

  /**
   */
  public static abstract class RequestMiddlewareImplBase implements io.grpc.BindableService {

    /**
     */
    public void comunication(Request request,
        io.grpc.stub.StreamObserver<Response> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COMUNICATION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_COMUNICATION,
            asyncUnaryCall(
              new MethodHandlers<
                Request,
                Response>(
                  this, METHODID_COMUNICATION)))
          .build();
    }
  }

  /**
   */
  public static final class RequestMiddlewareStub extends io.grpc.stub.AbstractStub<RequestMiddlewareStub> {
    private RequestMiddlewareStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RequestMiddlewareStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestMiddlewareStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RequestMiddlewareStub(channel, callOptions);
    }

    /**
     */
    public void comunication(Request request,
        io.grpc.stub.StreamObserver<Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COMUNICATION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RequestMiddlewareBlockingStub extends io.grpc.stub.AbstractStub<RequestMiddlewareBlockingStub> {
    private RequestMiddlewareBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RequestMiddlewareBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestMiddlewareBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RequestMiddlewareBlockingStub(channel, callOptions);
    }

    /**
     */
    public Response comunication(Request request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COMUNICATION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RequestMiddlewareFutureStub extends io.grpc.stub.AbstractStub<RequestMiddlewareFutureStub> {
    private RequestMiddlewareFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RequestMiddlewareFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RequestMiddlewareFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RequestMiddlewareFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Response> comunication(
        Request request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COMUNICATION, getCallOptions()), request);
    }
  }

  private static final int METHODID_COMUNICATION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RequestMiddlewareImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RequestMiddlewareImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COMUNICATION:
          serviceImpl.comunication((Request) request,
              (io.grpc.stub.StreamObserver<Response>) responseObserver);
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

  private static final class RequestMiddlewareDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Database.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RequestMiddlewareGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RequestMiddlewareDescriptorSupplier())
              .addMethod(METHOD_COMUNICATION)
              .build();
        }
      }
    }
    return result;
  }
}
