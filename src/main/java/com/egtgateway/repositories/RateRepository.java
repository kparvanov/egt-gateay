package com.egtgateway.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.egtgateway.entities.Rate;

@Repository
public interface RateRepository extends CrudRepository<Rate, UUID> {

    Optional<Rate> findFirstByCurrencyOrderByTimestampDesc(String currency);

    List<Rate> findByCurrencyAndTimestampBetween(String currency, LocalDateTime from, LocalDateTime to);
}
