package com.ing.controllers;

import com.ing.dto.MortgageCheckRequest;
import com.ing.dto.MortgageCheckResponse;
import com.ing.exceptions.ApiErrorResponse;
import com.ing.services.MortgageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "mortgages", description = "MortgageController")
public class MortgageController {

    private final MortgageService mortgageService;

    @PostMapping(value = "/mortgage-check", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(schema = @Schema(implementation = MortgageCheckResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(schema = @Schema(implementation = MortgageCheckResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @Operation(summary = "Check Mortgage Feasibility", description = "Checks the feasibility of a mortgage loan based on " +
            "the provided loan details. It validates the loan value and the loan-to-income ratio. " +
            "If the loan value exceeds the maximum allowed based on the borrower's income or if the loan value is " +
            "greater than the home value, the response will indicate that the mortgage is not feasible. Otherwise, " +
            "it calculates the Equated Monthly Installment (EMI) for the mortgage loan and returns t" +
            "he feasibility check response.", tags = {"mortgages"})
    public ResponseEntity<MortgageCheckResponse> checkMortgageFeasibility(@RequestBody MortgageCheckRequest request){
        MortgageCheckResponse reponse = mortgageService.performMortgageFeasibilityCheck(request);
        if(reponse.getIsFeasible()){
            return ResponseEntity.ok(reponse);
        }else{
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(reponse);
        }
    }

}
