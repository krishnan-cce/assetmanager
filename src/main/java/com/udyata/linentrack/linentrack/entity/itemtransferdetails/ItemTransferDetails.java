package com.udyata.linentrack.linentrack.entity.itemtransferdetails;

import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import com.udyata.linentrack.linentrack.entitynew.itemmaster.ItemMaster;
import com.udyata.linentrack.linentrack.entitynew.itemtransfer.ItemTransfer;
import com.udyata.linentrack.linentrack.entitynew.itemtypemaster.ItemTypeMaster;
import com.udyata.linentrack.linentrack.utils.TransferItemStatusEnum;
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
@Table(name = "ItemTransferDetails")
public class ItemTransferDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TransferId", nullable = false)
    private ItemTransfer transfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemId", nullable = false)
    private ItemMaster item;

    @Enumerated(EnumType.STRING)
    private TransferItemStatusEnum status; // IN_TRANSIT, RECEIVED, RETURNED, DAMAGED

    private Date statusChangeDate; // When the status was updated
}

