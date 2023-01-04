package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game extends PanacheEntity {
    public String name;

    @Column(name = "image_url")
    public String imageUrl;

    @OneToMany(mappedBy = "game")
    public List<GameMode> gameModes = new ArrayList<>();
}
