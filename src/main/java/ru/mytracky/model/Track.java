package ru.mytracky.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static ru.mytracky.model.EntityConstrainConstants.NOT_EMPTY;

@Entity
@Data
@Table(name = "tracks")
public class Track extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Track name " + NOT_EMPTY)
    private String name;

    @Column(name = "personal")
    private boolean personal;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
