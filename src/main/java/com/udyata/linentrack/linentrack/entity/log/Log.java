package com.udyata.linentrack.linentrack.entity.log;
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
@Table(name = "Logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false, columnDefinition = "DATETIME")
    private Date addDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "entity", nullable = false)
    private String entity;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "description", nullable = false)
    private String description;

}
