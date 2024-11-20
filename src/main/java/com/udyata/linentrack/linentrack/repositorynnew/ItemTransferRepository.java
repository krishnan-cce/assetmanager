package com.udyata.linentrack.linentrack.repositorynnew;


import com.udyata.linentrack.linentrack.entitynew.itemtransfer.ItemTransfer;
import com.udyata.linentrack.linentrack.utils.TransferStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemTransferRepository extends JpaRepository<ItemTransfer, Long> {
    List<ItemTransfer> findByStatus(TransferStatusEnum status);

    @Query("SELECT t FROM ItemTransfer t WHERE t.sourceLocation.id = :locationId OR t.destinationLocation.id = :locationId")
    List<ItemTransfer> findByLocationId(@Param("locationId") Long locationId);
}
