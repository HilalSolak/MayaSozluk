package com.hilalsolak.mayasozluk.model.repository;

import com.hilalsolak.mayasozluk.model.entities.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoggerRepository extends JpaRepository<Logger, UUID> {
}
