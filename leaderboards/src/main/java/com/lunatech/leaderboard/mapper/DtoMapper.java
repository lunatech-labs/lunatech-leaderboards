package com.lunatech.leaderboard.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

public interface DtoMapper<DTO, ENTITY> {
    ENTITY toEntity(DTO dto);

    default Collection<ENTITY> toEntities(Collection<DTO> dtos, Object... args) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
