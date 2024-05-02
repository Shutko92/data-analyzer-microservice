package com.example.dataanalyzermicroservice.repository;

import com.example.dataanalyzermicroservice.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Long> {
    @Query(value = """
    SELECT * FROM data
    offset (SELECT current_offset FROM offset LIMIT 1)
    LIMIT :batchSize
    """, nativeQuery = true)
    List<Data> findAllWithOffset(@Param("batchSize") long batchSize);

    @Modifying
    @Query(value = """
    UPDATE offset SET current_offset = current_offset + :batchSize
    """, nativeQuery = true)
    void incrementOffset(@Param("batchSize") long batchSize);
}
