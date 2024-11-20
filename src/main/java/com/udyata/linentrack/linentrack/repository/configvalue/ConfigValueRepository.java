package com.udyata.linentrack.linentrack.repository.configvalue;

import com.udyata.linentrack.linentrack.entity.configvalue.ConfigValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigValueRepository extends JpaRepository<ConfigValue,Long> {
    Optional<ConfigValue> findByConfigKey(String key);
}
