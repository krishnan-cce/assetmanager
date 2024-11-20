package com.udyata.linentrack.linentrack.entity.configvalue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ConfigValue")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String configKey;

    @Column(nullable = false)
    private String value;

    private String usedModule;
}