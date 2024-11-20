package com.udyata.linentrack.linentrack.entity.serialnumbermaster;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "SerialNumberMaster"
)
public class SerialNumberMaster {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private String form;

    private String code;

    private String prefix;
}
