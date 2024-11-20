package com.udyata.linentrack.linentrack.entity.locationmaster;

import com.udyata.linentrack.linentrack.utils.ItemStatusEnum;
import com.udyata.linentrack.linentrack.utils.LocationTypeEnum;
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
@Table(name = "LocationMaster")
public class LocationMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private LocationTypeEnum type;

    @Column(nullable = false)
    private String code;

    private String description;

    private String address;

    private String mobileNumber;

    private Long addedBy;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;
}


