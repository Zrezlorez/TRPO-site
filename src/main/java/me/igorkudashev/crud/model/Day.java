package me.igorkudashev.crud.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "date")
@Data
@Accessors(chain = true)
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "date_id")
    private Long id;

    private String name;
}
