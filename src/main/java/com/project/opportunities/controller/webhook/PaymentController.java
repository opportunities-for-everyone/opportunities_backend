package com.project.opportunities.controller.webhook;

import com.project.opportunities.service.integration.payment.interfaces.PaymentCallbackService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "LiqPay API payment controller",
        description = "Public API for interacting with the LiqPay payment service"
)
@Hidden
@RestController
@RequestMapping("/public/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentCallbackService paymentCallbackService;

    @Operation(
            summary = "Process payment callback from LiqPay",
            description = """
                    This endpoint processes the payment result callback sent by LiqPay,
                     validates the signature, and updates the payment status.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment callback processed successfully."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters or signature mismatch",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @PostMapping("/callback")
    public void getDonationStatus(
            @RequestParam("data") String data,
            @RequestParam("signature") String signature) throws Exception {
        paymentCallbackService.processDonationResult(data, signature);
    }
}
