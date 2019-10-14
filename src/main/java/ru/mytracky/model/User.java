package ru.mytracky.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.List;

import static ru.mytracky.model.EntityConstrainConstants.*;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(name = "username", nullable = false)
    @NotBlank(message = "Name " + NOT_EMPTY)
    @Size(message = "Username not be more than " + MAX_ACCOUNT_NAME + " characters", max = MAX_ACCOUNT_NAME)
    private String username;

    @Column(name = "firstName", nullable = false)
    @NotBlank(message = "First name " + NOT_EMPTY)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    @NotBlank(message = "Last name " + NOT_EMPTY)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email " + NOT_EMPTY)
    @Email(message = INVALID_EMAIL)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password " + NOT_EMPTY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    })
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Track> tracks;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
