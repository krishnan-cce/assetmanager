package com.udyata.linentrack.linentrack.servicenew;

import com.udyata.linentrack.linentrack.entity.itemtransferdetails.ItemTransferDetails;
import com.udyata.linentrack.linentrack.utils.TransferItemStatusEnum;

import java.util.Date;
import java.util.List;

public interface ItemTransferService {
    String createItemTransfer(Long sourceLocationId, Long destinationLocationId, List<Long> itemIds, Long userId, Date expectedDeliveryDate);
    void updateItemStatus(Long transferId, Long itemId, TransferItemStatusEnum newStatus, Long userId);
    List<ItemTransferDetails> getTransferDetails(Long transferId); // New method

}
