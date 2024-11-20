package com.udyata.linentrack.linentrack.repositorynnew;

import com.udyata.linentrack.linentrack.entitynew.itemmaster.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ItemMasterRepository extends JpaRepository<ItemMaster,Long> {

    @Query(value = "CALL GetItemMasterList(:itemTypeId, :roomId);", nativeQuery = true)
    List<Map<String, Object>> getItemMasterList(
            @Param("itemTypeId") Integer itemTypeId,
            @Param("roomId") Long roomId
    );

    @Query(value = "CALL GetDashBoardData();", nativeQuery = true)
    List<Map<String, Object>> getDashBoardData();

    @Query("SELECT im FROM ItemMaster im WHERE im.rfidTag.tagCode = :tagCode")
    Optional<ItemMaster> findByRfidTagCode(@Param("tagCode") String tagCode);

    @Query("SELECT c FROM ItemMaster c WHERE c.roomMaster.id = :roomMasterId")
    List<ItemMaster> findByRoomMasterId(@Param("roomMasterId") Long roomMasterId);

}
