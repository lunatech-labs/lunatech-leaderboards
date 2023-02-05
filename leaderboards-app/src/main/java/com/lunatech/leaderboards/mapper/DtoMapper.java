package com.lunatech.leaderboards.mapper;

import java.util.Collection;

public interface DtoMapper<ENTITY, REQUEST_DTO, RESPONSE_DTO> {
    ENTITY toEntity(REQUEST_DTO dto);
    RESPONSE_DTO toDto(ENTITY entity);

    default Collection<ENTITY> toEntities(Collection<REQUEST_DTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    default Collection<RESPONSE_DTO> toDtos(Collection<ENTITY> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
