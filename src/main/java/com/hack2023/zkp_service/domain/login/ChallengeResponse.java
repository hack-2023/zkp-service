package com.hack2023.zkp_service.domain.login;

public class ChallengeResponse extends LoginResponse{
    private ChallengeState challengeState;

    public ChallengeState getChallengeState() {
        return challengeState;
    }

    public void setChallengeState(ChallengeState challengeState) {
        this.challengeState = challengeState;
    }
}

enum ChallengeState {
    FAILURE("failure"),
    CONTINUE("continue"),
    SUCCESS("success");
    public final String state;
    private ChallengeState(String state) {
        this.state = state;
    }
}