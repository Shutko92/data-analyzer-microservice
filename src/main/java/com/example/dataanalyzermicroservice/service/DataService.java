package com.example.dataanalyzermicroservice.service;

import com.example.dataanalyzermicroservice.model.Data;

import java.util.List;

public interface DataService {
    void handle(Data data, String type);

    List<Data> getWithBatch(long batchSize);
}
