package com.hack2023.zkp_service.domain.login;

public class ChallengeRequest extends LoginRequest {
    private String token;
    private String challengeAnswer;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChallengeAnswer() {
        return challengeAnswer;
    }

    public void setChallengeAnswer(String challengeAnswer) {
        this.challengeAnswer = challengeAnswer;
    }
}
