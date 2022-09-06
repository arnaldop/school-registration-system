package com.arnaldopiccinelli.schoolregistrationsystem.base;

import java.util.Set;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController<E extends BaseEntity, D extends BaseDto, S extends BaseSearchDto> {
	protected abstract BaseService<E, D, S> getService();

	@PostMapping
	@Operation(description = "Creates single resource")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successful operation."),
			@ApiResponse(responseCode = "409",
					description = "Conflict with existing resource.")})
	protected D create(@NonNull @Valid @RequestBody final D dto) {
		if (dto.getId() != null) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Cannot create resource with non-blank ID.");
		}

		return getService().createFromDto(dto);
	}

	@GetMapping("{id}")
	@Operation(description = "Returns single resource")
	protected D read(@PathVariable("id") final long id) {
		return getService().readDto(id);
	}

	@GetMapping
	@Operation(description = "Returns list of resources")
	@PageableAsQueryParam
	protected Page<D> read(@NonNull @ParameterObject final Pageable pageable) {
		return getService().readDtos(pageable);
	}

	@PostMapping("search")
	@Operation(description = "Returns list of resources")
	@PageableAsQueryParam
	protected Page<D> read(
			@NonNull @RequestBody final S searchDto,
			@NonNull @ParameterObject final Pageable pageable) {
		return getService().readDtos(searchDto, pageable);
	}

	@PutMapping
	@Operation(description = "Updates single and returns updated resource")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successful operation."),
			@ApiResponse(responseCode = "409",
					description = "Conflict with existing resource.")})
	protected D update(@NonNull @Valid @RequestBody final D dto) {
		if (dto.getId() == null) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST,
					"Missing resource ID in DTO.");
		}

		return getService().updateFromDto(dto);
	}

	@DeleteMapping
	@Operation(description = "Deletes by list of resource ids")
	protected void delete(@NonNull @RequestParam("ids") final Set<Long> ids) {
		getService().delete(ids);
	}
}
