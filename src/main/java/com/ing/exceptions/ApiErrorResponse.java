package com.ing.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiErrorResponse {

    @Schema(title = "timestamp", name = "timestamp",
            description = "Error Timestamp set as per Nederland",
            example = "10-08-2023 03:48:58"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    @Schema(title = "calledURI", name = "calledURI", description = "Called URI")
    private String calledURI;

    @Schema(title = "message", name = "message", description = "Error Message")
    private String message;

    @Schema(title = "errors", name = "errors", description = "List of validation errors")
    private List<String> errors;
}