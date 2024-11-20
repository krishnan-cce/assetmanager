package com.udyata.linentrack.linentrack.entitynew.itemtransfer;


import com.udyata.linentrack.linentrack.entity.locationmaster.LocationMaster;
import com.udyata.linentrack.linentrack.entity.rfidtag.RFIDTag;
import com.udyata.linentrack.linentrack.utils.ItemStatusEnum;
import com.udyata.linentrack.linentrack.utils.TransferStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ItemTransfer")
public class ItemTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceLocationId")
    private LocationMaster sourceLocation; // e.g., Warehouse

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DestinationLocationId", nullable = false)
    private LocationMaster destinationLocation; // e.g., Event Venue

    private String transferNumber; // Unique identifier for this transfer

    private Long initiatedBy; // User who initiated the transfer

    private Date transferDate;

    private Date expectedDeliveryDate;

    private Date completionDate; // When all items are returned

    @Enumerated(EnumType.STRING)
    private TransferStatusEnum status; // IN_TRANSIT, RECEIVED, COMPLETED

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date createdDate;

    private String remarks;
}
