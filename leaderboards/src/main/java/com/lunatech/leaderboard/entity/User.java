package com.lunatech.leaderboard.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "app_user")
public class User extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name="app_user_id_seq", sequenceName="app_user_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="app_user_id_seq")
    @Column(name = "id", updatable=false)
    public Long id;

    @Column(unique = true)
    public String email;

    @Column(name = "displayname", unique = true)
    public String displayName;

    @Column(name = "profilepicurl")
    public String profilePicUrl;

    public static Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
