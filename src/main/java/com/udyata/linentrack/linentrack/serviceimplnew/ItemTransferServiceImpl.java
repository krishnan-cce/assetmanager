package com.udyata.linentrack.linentrack.serviceimplnew;

import com.udyata.linentrack.linentrack.dto.ItemCategoryMasterDTO;
import com.udyata.linentrack.linentrack.entity.itemtransferdetails.ItemTransferDetails;
import com.udyata.linentrack.linentrack.entity.locationmaster.LocationMaster;
import com.udyata.linentrack.linentrack.entity.user.User;
import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import com.udyata.linentrack.linentrack.entitynew.itemmaster.ItemMaster;
import com.udyata.linentrack.linentrack.entitynew.itemtransfer.ItemTransfer;
import com.udyata.linentrack.linentrack.entitynew.itemtypemaster.ItemTypeMaster;
import com.udyata.linentrack.linentrack.exception.LinenTrackApiException;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.repository.locationmaster.LocationMasterRepository;
import com.udyata.linentrack.linentrack.repositorynnew.*;
import com.udyata.linentrack.linentrack.security.SecurityUtils;
import com.udyata.linentrack.linentrack.servicenew.ItemCategoryMasterService;
import com.udyata.linentrack.linentrack.servicenew.ItemTransferService;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import com.udyata.linentrack.linentrack.utils.ItemStatusEnum;
import com.udyata.linentrack.linentrack.utils.TransferItemStatusEnum;
import com.udyata.linentrack.linentrack.utils.TransferStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemTransferServiceImpl implements ItemTransferService {

    private final LocationMasterRepository locationMasterRepository;
    private final ItemMasterRepository itemMasterRepository;
    private final ItemTransferRepository itemTransferRepository;
    private final ItemTransferDetailsRepository itemTransferDetailsRepository;

    @Override
    public String createItemTransfer(Long sourceLocationId, Long destinationLocationId, List<Long> itemIds, Long userId, Date expectedDeliveryDate) {

        LocationMaster sourceLocation = locationMasterRepository.findById(sourceLocationId).orElse(null);
        LocationMaster destinationLocation = locationMasterRepository.findById(destinationLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "ID", destinationLocationId));


        List<ItemMaster> items = itemMasterRepository.findAllById(itemIds);
        for (ItemMaster item : items) {
            if (!item.getStatus().equals(ItemStatusEnum.AVAILABLE)) {
                throw new LinenTrackApiException(HttpStatus.BAD_REQUEST,"Item " + item.getId() + " is not available.");
            }
        }


        ItemTransfer transfer = new ItemTransfer();
        transfer.setSourceLocation(sourceLocation);
        transfer.setDestinationLocation(destinationLocation);
        transfer.setTransferNumber(UUID.randomUUID().toString());
        transfer.setInitiatedBy(userId);
        transfer.setTransferDate(new Date());
        transfer.setExpectedDeliveryDate(expectedDeliveryDate);
        transfer.setStatus(TransferStatusEnum.IN_TRANSIT);
        itemTransferRepository.save(transfer);


        for (ItemMaster item : items) {

            item.setStatus(ItemStatusEnum.IN_TRANSIT);
            item.setCurrentLocation(destinationLocation);
            itemMasterRepository.save(item);


            ItemTransferDetails detail = new ItemTransferDetails();
            detail.setTransfer(transfer);
            detail.setItem(item);
            detail.setStatus(TransferItemStatusEnum.IN_TRANSIT);
            detail.setStatusChangeDate(new Date());
            itemTransferDetailsRepository.save(detail);
        }

        return transfer.getTransferNumber();
    }

    @Override
    public void updateItemStatus(Long transferId, Long itemId, TransferItemStatusEnum newStatus, Long userId) {

        ItemTransfer transfer = itemTransferRepository.findById(transferId)
                .orElseThrow(() -> new ResourceNotFoundException("ItemTransfer", "ID", transferId));
        ItemTransferDetails detail = itemTransferDetailsRepository.findByTransferIdAndItemId(transferId, itemId)
                .orElseThrow(() -> new ResourceNotFoundException("ItemTransferDetails", "ItemId", itemId));

        ItemMaster item = detail.getItem();
        detail.setStatus(newStatus);
        detail.setStatusChangeDate(new Date());
        itemTransferDetailsRepository.save(detail);

        if (newStatus == TransferItemStatusEnum.RECEIVED || newStatus == TransferItemStatusEnum.RETURNED) {
            item.setStatus(ItemStatusEnum.AVAILABLE);
            itemMasterRepository.save(item);
        }
    }

    @Override
    public List<ItemTransferDetails> getTransferDetails(Long transferId) {

        if (!itemTransferRepository.existsById(transferId)) {
            throw new ResourceNotFoundException("ItemTransfer", "ID", transferId);
        }

        return itemTransferDetailsRepository.findByTransferId(transferId);
    }

}
