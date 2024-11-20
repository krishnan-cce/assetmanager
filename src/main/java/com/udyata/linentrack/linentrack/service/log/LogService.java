package com.udyata.linentrack.linentrack.service.log;

public interface LogService {
    void logOperation(Long userId, String operation, String entity, Long entityId, String description);
}
