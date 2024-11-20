package com.udyata.linentrack.linentrack.controller;


import com.udyata.linentrack.linentrack.dto.*;
import com.udyata.linentrack.linentrack.entity.itemtransferdetails.ItemTransferDetails;
import com.udyata.linentrack.linentrack.servicenew.*;
import com.udyata.linentrack.linentrack.utils.ApiResponse;
import com.udyata.linentrack.linentrack.utils.TransferItemStatusEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/item-transfers")
@RequiredArgsConstructor
public class ItemTransferController {

    private final ItemTransferService itemTransferService;

    /**
     * Create a new ItemTransfer
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createItemTransfer(
            @RequestParam(required = false) Long sourceLocationId,
            @RequestParam Long destinationLocationId,
            @RequestBody List<Long> itemIds,
            @RequestParam Long userId,
            @RequestParam Date expectedDeliveryDate) {
        try {
            String transferNumber = itemTransferService.createItemTransfer(
                    sourceLocationId, destinationLocationId, itemIds, userId, expectedDeliveryDate);

            ApiResponse<String> response = new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    transferNumber,
                    "Item transfer created successfully"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    null,
                    "Error creating item transfer: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Update the status of a specific item in a transfer
     */
    @PatchMapping("/update-status")
    public ResponseEntity<ApiResponse<String>> updateItemStatus(
            @RequestParam Long transferId,
            @RequestParam Long itemId,
            @RequestParam TransferItemStatusEnum newStatus,
            @RequestParam Long userId) {
        try {
            itemTransferService.updateItemStatus(transferId, itemId, newStatus, userId);

            ApiResponse<String> response = new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    null,
                    "Item status updated successfully"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    null,
                    "Error updating item status: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get all items for a specific transfer
     */
    @GetMapping("/{transferId}/details")
    public ResponseEntity<ApiResponse<List<ItemTransferDetails>>> getTransferDetails(
            @PathVariable Long transferId) {
        try {
            List<ItemTransferDetails> details = itemTransferService.getTransferDetails(transferId);

            ApiResponse<List<ItemTransferDetails>> response = new ApiResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    details,
                    "Transfer details fetched successfully"
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<ItemTransferDetails>> errorResponse = new ApiResponse<>(
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    null,
                    "Error fetching transfer details: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
