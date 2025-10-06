package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//  querying the db
@Repository
public interface TSRepository extends JpaRepository<TSEntity, Long> {

    @Query("SELECT COUNT(DISTINCT t.source) FROM TSEntity t WHERE t.source LIKE :pattern")
    long countDistinctSourcesByPattern(@Param("pattern") String pattern);
    
}
