package com.arnaldopiccinelli.schoolregistrationsystem.base;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import lombok.NonNull;

public interface BaseMapper<E, D> {
	E toEntity(@NonNull final D dto);
	E toEntity(@NonNull final D dto, @MappingTarget @NonNull final E course);
	D toDto(@NonNull final E entity);
}
