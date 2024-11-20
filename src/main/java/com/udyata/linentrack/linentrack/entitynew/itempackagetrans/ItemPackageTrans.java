package com.udyata.linentrack.linentrack.entitynew.itempackagetrans;

import com.udyata.linentrack.linentrack.entitynew.itemmaster.ItemMaster;
import com.udyata.linentrack.linentrack.entitynew.itempackage.ItemPackage;
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
@Table(name = "ItemPackageTrans")
public class ItemPackageTrans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packageNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemMasterId")
    private ItemMaster itemMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemPackageId")
    private ItemPackage itemPackage;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;

    @CreationTimestamp
    @Column(columnDefinition = "DATETIME")
    private Date lastUpdatedOn;


    private Boolean isWashed;

}
