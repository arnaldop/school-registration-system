package com.arnaldopiccinelli.schoolregistrationsystem.base;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseService<E extends BaseEntity, D extends BaseDto, S extends BaseSearchDto> {
	protected abstract BaseRepository<E> getRepository();

	protected abstract BaseMapper<E, D> getMapper();

	protected abstract Specification<E> getSearchSpecification(
			@NonNull final S searchDto);

	protected abstract Specification<E> getUniqueSpecification(@NonNull final D dto);

	public D createFromDto(@NonNull final D dto) {
		if (dto.getId() != null) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Cannot create resource with non-blank ID.");
		}

		if (findByUniqueFields(dto).isPresent()) {
			throw new DuplicateKeyException(
					"Resource already exists with provided fields.");
		}

		return toDto(create(convertToEntity(dto)));
	}

	public D readDto(final long id) {
		try {
			return toDto(read(id));
		} catch (final EmptyResultDataAccessException e) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND,
					"Resource not found.");
		}
	}

	protected Page<D> readDtos(@NonNull final Pageable pageable) {
		return toDto(getRepository().findAll(pageable));
	}

	protected Page<D> readDtos(
			@NonNull final S searchDto,
			@NonNull final Pageable pageable) {
		return toDto(getSearchResults(searchDto, pageable));
	}

	public D updateFromDto(@NonNull final D dto) {
		// TODO ensure either Optional or nullable OR throws for all repos
		final E entityToUpdate = read(dto.getId());

		Optional<E> entityFromDto = Optional.empty();
		try {
			entityFromDto = findByUniqueFields(dto);
		} catch (final Exception e) {
			log.error("Why are we here???", e);
		}

		if (entityFromDto.isPresent()
				&& !entityToUpdate.getId().equals(entityFromDto.get().getId())) {
			throw new DuplicateKeyException(
					"Resource already exists with provided fields.");
		}

		return toDto(update(updateEntity(dto, entityToUpdate)));
	}

	// BASIC CRUD
	public E create(@NonNull final E entity) {
		return getRepository().save(entity);
	}

	public E read(final long id) {
		return getRepository().getReferenceById(id);
	}

	public List<E> read(@NonNull final List<Long> ids) {
		return getRepository().findAllById(ids);
	}

	public E update(@NonNull final E entity) {
		return getRepository().save(entity);
	}

	public void delete(@NonNull final Collection<Long> ids) {
		getRepository().deleteAllById(ids);
	}

	public Page<E> getSearchResults(@NonNull final S searchDto,
									@NonNull final Pageable pageable) {
		return getRepository().findAll(
				getSearchSpecification(searchDto),
				pageable);
	}

	// ADDITIONAL OPERATIONS
	public Optional<E> findByUniqueFields(@NonNull final D dto) {
		return getRepository().findAll(
				getUniqueSpecification(dto),
				Pageable.unpaged()).stream().findFirst();
	}

	// CONVERSION SERVICES
	public E convertToEntity(@NonNull final D dto) {
		return getMapper().toEntity(dto);
	}

	public E updateEntity(@NonNull final D dto, @NonNull final E entity) {
		return getMapper().toEntity(dto, entity);
	}

	public D toDto(@NonNull final E entity) {
		return getMapper().toDto(entity);
	}

	public List<D> toDto(@NonNull final List<E> entities) {
		return entities.stream()
				.map(this::toDto)
				.toList();
	}

	public Page<D> toDto(@NonNull final Page<E> entities) {
		return entities.map(this::toDto);
	}
}
