package com.project.opportunities.controller.quest;

import com.project.opportunities.domain.dto.user.response.UserResponseGeneralInfoDto;
import com.project.opportunities.service.core.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/users")
@RequiredArgsConstructor
@Tag(
        name = "Public User Management",
        description = "Endpoints for managing public user information"
)
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "Get all team members",
            description = "Retrieve a paginated list of team members with general information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved active team members"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input parameter"
                    )
            }
    )
    public Page<UserResponseGeneralInfoDto> getAllUsers(
            @PageableDefault @ParameterObject Pageable pageable) {
        return userService.getAllUsers(pageable);
    }
}
