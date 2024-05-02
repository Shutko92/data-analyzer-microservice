package com.example.dataanalyzermicroservice.service;

import com.example.dataanalyzermicroservice.model.Data;
import com.example.grpccommon.DataServerGrpc;

import com.example.grpccommon.GRPCData;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GRPCDataService extends DataServerGrpc.DataServerImplBase {
    private final DataService dataService;

    @Override
    public void addData(GRPCData request, StreamObserver<Empty> responseObserver) {
        Data data = new Data(request);
        dataService.handle(data, "Grpc");
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<GRPCData> addStreamOfData(StreamObserver<Empty> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(GRPCData grpcData) {
                Data data = new Data(grpcData);
                dataService.handle(data, "Grpc");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }
}
