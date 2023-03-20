package com.hack2023.zkp_service.adapter.inbound.challenge;

import com.hack2023.zkp_service.domain.login.ChallengeRequest;
import com.hack2023.zkp_service.domain.login.ChallengeResponse;
import com.hack2023.zkp_service.domain.login.ChallengeService;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class ChallengeEndpoint {
    private ChallengeService challengeService;

    public ChallengeEndpoint(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/challenge")
    public ChallengeResponse verify(@RequestBody ChallengeRequest request) throws ParseException, JOSEException {
        return challengeService.verifyAnswer(request);
    }
}
