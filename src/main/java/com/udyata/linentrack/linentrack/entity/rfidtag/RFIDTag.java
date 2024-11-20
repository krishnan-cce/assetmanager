package com.udyata.linentrack.linentrack.entity.rfidtag;

import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import com.udyata.linentrack.linentrack.entitynew.itemtypemaster.ItemTypeMaster;
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
@Table(name = "RFIDTag")
public class RFIDTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tagCode;

    @Column(nullable = false, unique = true)
    private String qrCode;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;

    private Long addedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemCategoryMasterId", nullable = false)
    private ItemCategoryMaster itemCategoryMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemTypeMasterId", nullable = false)
    private ItemTypeMaster itemTypeMaster;

}
