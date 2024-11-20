package com.udyata.linentrack.linentrack.repository.serialnumbermaster;

import com.udyata.linentrack.linentrack.entity.serialnumbermaster.SerialNumberMaster;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SerialNumberMasterRepository extends JpaRepository<SerialNumberMaster,Long> {
    Optional<SerialNumberMaster> findByForm(String form);

    @Modifying
    @Transactional
    @Query("UPDATE SerialNumberMaster sn SET sn.code = :code")
    void updateAllCodes(@Param("code") String code);
}
