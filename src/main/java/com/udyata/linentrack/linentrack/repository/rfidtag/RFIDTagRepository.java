package com.udyata.linentrack.linentrack.repository.rfidtag;

import com.udyata.linentrack.linentrack.entity.rfidtag.RFIDTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RFIDTagRepository extends JpaRepository<RFIDTag,Long> {
    Optional<RFIDTag> findByTagCode(String tagCode);

    @Query("SELECT r FROM RFIDTag r WHERE r.id NOT IN (SELECT i.rfidTag.id FROM ItemMaster i WHERE i.rfidTag IS NOT NULL)")
    List<RFIDTag> findRFIDTagsNotAssignedToItemMaster();
}
