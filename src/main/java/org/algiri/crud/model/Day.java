package org.algiri.crud.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "days")
@Accessors(chain = true)
@Data
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String date;




    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "days_users",
            joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
