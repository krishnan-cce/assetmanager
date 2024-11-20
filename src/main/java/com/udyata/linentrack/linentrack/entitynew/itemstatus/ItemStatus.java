package com.udyata.linentrack.linentrack.entitynew.itemstatus;

import com.udyata.linentrack.linentrack.entitynew.itemcategorymaster.ItemCategoryMaster;
import com.udyata.linentrack.linentrack.entitynew.itemtypemaster.ItemTypeMaster;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ItemStatus")
public class ItemStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemTypeMasterId", nullable = false)
    private ItemTypeMaster itemTypeMaster;

}

/**
 *
 * 1. IN-ROOM
 * 2. COLLECTION
 * 3. WASHING
 * 4. SORTING
 * 5. DAMAGED
 * 6. LOST
 *
 *
 * */