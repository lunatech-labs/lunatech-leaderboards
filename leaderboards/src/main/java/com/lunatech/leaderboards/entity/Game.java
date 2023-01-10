package com.lunatech.leaderboards.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name="game_id_seq", sequenceName="game_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="game_id_seq")
    @Column(name = "id", updatable=false)
    public Long id;

    @Column(unique = true)
    public String name;

    @Column(name = "image_url")
    public String imageUrl;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    public List<GameMode> gameModes = new ArrayList<>();

    public static Game findByIdWithGameModes(Long id) {
        return find("""
                select distinct obj from Game obj 
                left join fetch obj.gameModes
                where obj.id = ?1
                """, id).firstResult();
    }
}
