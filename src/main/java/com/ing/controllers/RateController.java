package com.ing.controllers;

import com.ing.dto.RateResponseDto;
import com.ing.exceptions.ApiErrorResponse;
import com.ing.mappers.RateResponseDtoMapper;
import com.ing.services.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "rates", description = "RateController")
public class RateController {
    private final RateService rateService;
    private final RateResponseDtoMapper rateResponseDtoMapper;

    @GetMapping("/interest-rates")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = RateResponseDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @Operation(summary = "Get all rates", description = "Retrieves all rates for different product types and rate types. " +
            "Returns a list of rate objects containing the product type, rate type, maturity period, and interest rate.",
            tags = {"rates"})
    public ResponseEntity<List<RateResponseDto>> getAllRates(){
        return ResponseEntity.ok(
                rateService.getAllRates().stream()
                        .map(rateResponseDtoMapper)
                        .collect(Collectors.toList())
        );
    }

}
