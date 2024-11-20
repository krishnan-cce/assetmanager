package com.udyata.linentrack.linentrack.entitynew.itempackage;

import com.udyata.linentrack.linentrack.entitynew.itempackagetrans.ItemPackageTrans;
import com.udyata.linentrack.linentrack.entitynew.itemstatus.ItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ItemPackage")
public class ItemPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageNumber;

    private Boolean isPackageReceived;

    @OneToMany(mappedBy = "itemPackage", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemPackageTrans> itemPackageTrans = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemStatusId")
    private ItemStatus itemStatus;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;

    @CreationTimestamp
    @Column(columnDefinition = "DATETIME")
    private Date lastUpdatedOn;

    private Long addedBy;

    private String floorNumber;

    private Boolean isWashed=false;

    private Boolean isAWBProcessed=false;
}
