package com.udyata.linentrack.linentrack.serviceimpl.log;

import com.udyata.linentrack.linentrack.entity.log.Log;
import com.udyata.linentrack.linentrack.repository.log.LogRepository;
import com.udyata.linentrack.linentrack.service.log.LogService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private static final int MAX_DESCRIPTION_LENGTH = 255;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    @Override
    @Transactional
    public void logOperation(Long userId, String operation, String entity, Long entityId, String description) {
        Log log = new Log();
        log.setUserId(userId);
        log.setAddDate(new Date());
        log.setOperation(operation);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setDescription(truncateMessage(description));

        logRepository.save(log);
    }
    private String truncateMessage(String message) {
        if (message != null && message.length() > MAX_DESCRIPTION_LENGTH) {
            return message.substring(0, MAX_DESCRIPTION_LENGTH);
        }
        return message;
    }

}
