package ru.mytracky.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tracks")
public class Track extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "private")
    private boolean isPrivate;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
