package com.project.opportunities.controller.quest;

import com.project.opportunities.domain.dto.donation.request.CreateDonateDto;
import com.project.opportunities.service.core.interfaces.DonateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Public Donation Management",
        description = "Endpoints for managing donations by public users"
)
@RestController
@RequestMapping(value = "/public/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonateService donateService;

    @Operation(
            summary = "Generate donation form",
            description = "Generates a payment form for donation based on the provided details",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully generated donation form",
                            content = @Content(
                                    schema = @Schema(
                                            type = "string",
                                            description = "HTML form for payment"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    )
            }
    )
    @PostMapping
    public String generateDonationForm(@RequestBody @Valid CreateDonateDto donationDto) {
        return donateService.generateDonationForm(donationDto);
    }
}
