package com.udyata.linentrack.linentrack.entitynew.itemcategorymaster;

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
@Table(name = "ItemCategoryMaster")
public class ItemCategoryMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long addedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemTypeMasterId", nullable = false)
    private ItemTypeMaster itemTypeMaster;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;
}

