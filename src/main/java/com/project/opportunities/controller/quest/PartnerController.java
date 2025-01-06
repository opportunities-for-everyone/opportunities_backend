package com.project.opportunities.controller.quest;

import com.project.opportunities.dto.partner.CreatePartnerRequestDto;
import com.project.opportunities.dto.partner.PartnerAllInfoDto;
import com.project.opportunities.dto.partner.PartnerGeneralInfoDto;
import com.project.opportunities.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Partner Management",
        description = "Endpoints for managing partner information and applications"
)
@RestController
@RequestMapping(value = "/public/partners")
@RequiredArgsConstructor
public class PartnerController {
    private final PartnerService partnerService;

    @Operation(
            summary = "Get general partner information",
            description = "Fetches a paginated list of general partner information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved partner information"
                    )
            }
    )
    @GetMapping
    public Page<PartnerGeneralInfoDto> getPartnersGeneralInfo(
            @ParameterObject @PageableDefault Pageable pageable) {
        return partnerService.getPartnersGeneralInfo(pageable);
    }

    @Operation(
            summary = "Get detailed partner information",
            description = "Fetches all detailed information about a specific partner by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved detailed partner information"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Partner not found"
                    )
            }
    )
    @GetMapping(value = "/{id}")
    public PartnerAllInfoDto getPartnerAllInfo(@PathVariable Long id) {
        return partnerService.getPartnerAllInfo(id);
    }

    @Operation(
            summary = "Submit partner application",
            description = "Allows submission of a new partner application",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Partner application submitted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    )
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void submitPartnerApplication(
            @ModelAttribute @Valid CreatePartnerRequestDto requestDto) {
        partnerService.submitPartnerApplication(requestDto);
    }
}
