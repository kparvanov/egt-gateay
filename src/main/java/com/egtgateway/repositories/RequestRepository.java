package com.egtgateway.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.egtgateway.entities.Request;

@Repository
public interface RequestRepository extends CrudRepository<Request, String> {

    boolean existsByRequestId(String requestId);
}
