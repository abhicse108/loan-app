package com.rbih.loanApp.controller;

import com.rbih.loanApp.domain.dto.CreateLoanApplicationRequest;
import com.rbih.loanApp.domain.dto.LoanApplicationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rbih.loanApp.service.LoanApplicationService;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService service;

//    public LoanApplicationController(LoanApplicationService service) {
//        this.service = service;
//    }

    @PostMapping
    public ResponseEntity<LoanApplicationResponse> create(
            @Valid @RequestBody CreateLoanApplicationRequest request
    ) {
        return ResponseEntity.ok(service.processApplication(request));
    }
}
