package com.udyata.linentrack.linentrack.entitynew.itemmaster;

import com.udyata.linentrack.linentrack.entity.locationmaster.LocationMaster;
import com.udyata.linentrack.linentrack.entity.rfidtag.RFIDTag;
import com.udyata.linentrack.linentrack.utils.ItemStatusEnum;
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
@Table(name = "ItemMaster")
public class ItemMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrentLocationId")
    private LocationMaster currentLocation;

    @Enumerated(EnumType.STRING)
    private ItemStatusEnum status; // AVAILABLE, ASSIGNED, IN_TRANSIT, DAMAGED

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "RFIDTagId", unique = true)
    private RFIDTag rfidTag;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;

    private Long addedBy;
}

