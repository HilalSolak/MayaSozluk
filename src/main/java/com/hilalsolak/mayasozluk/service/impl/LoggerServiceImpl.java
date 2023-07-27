package com.hilalsolak.mayasozluk.service.impl;

import com.hilalsolak.mayasozluk.model.entities.Logger;
import com.hilalsolak.mayasozluk.model.repository.LoggerRepository;
import com.hilalsolak.mayasozluk.model.dto.responses.LoggerResponse;
import com.hilalsolak.mayasozluk.converters.ConverterResponse;
import com.hilalsolak.mayasozluk.service.LoggerService;
import com.hilalsolak.mayasozluk.utils.constants.GlobalConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoggerServiceImpl implements LoggerService {

    private final LoggerRepository repository;

    public LoggerServiceImpl(LoggerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LoggerResponse> getAllLoggers() {
        List<LoggerResponse> response = repository.findAll().stream().map(ConverterResponse::convert).toList();

        return response;
    }

    @Override
    public LoggerResponse getLoggerById(UUID id) {
        Logger logger = getLoggerByIdInRepository(id);
        LoggerResponse response = ConverterResponse.convert(logger);

        return response;
    }

    @Override
    public void createLogger(String clientId, String activityType) {
         Logger logger = new Logger();
         logger.setClientId(clientId);
         logger.setActivityType(activityType);
         repository.save(logger);
    }

    @Override
    public void deleteLoggerById(UUID id) {
        Logger logger = getLoggerByIdInRepository(id);
        repository.delete(logger);
    }
    private Logger getLoggerByIdInRepository(UUID id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException(GlobalConstants.LOGGER_NOT_FOUND));

    }
}
