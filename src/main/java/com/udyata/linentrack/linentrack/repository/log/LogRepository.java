package com.udyata.linentrack.linentrack.repository.log;

import com.udyata.linentrack.linentrack.entity.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {
}
