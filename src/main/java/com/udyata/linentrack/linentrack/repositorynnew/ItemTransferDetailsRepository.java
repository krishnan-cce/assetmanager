package com.udyata.linentrack.linentrack.repositorynnew;


import com.udyata.linentrack.linentrack.entity.itemtransferdetails.ItemTransferDetails;
import com.udyata.linentrack.linentrack.utils.TransferItemStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemTransferDetailsRepository extends JpaRepository<ItemTransferDetails, Long> {
    List<ItemTransferDetails> findByTransferId(Long transferId);

    Optional<ItemTransferDetails> findByTransferIdAndItemId(Long transferId, Long itemId);

    @Query("SELECT d FROM ItemTransferDetails d WHERE d.transfer.id = :transferId AND d.status = :status")
    List<ItemTransferDetails> findByTransferIdAndStatus(@Param("transferId") Long transferId, @Param("status") TransferItemStatusEnum status);
}
