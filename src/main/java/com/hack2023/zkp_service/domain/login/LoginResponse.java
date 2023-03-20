package com.hack2023.zkp_service.domain.login;

import java.util.List;

public class LoginResponse {
    private String email;
    private Challenge challenge;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
}

class Challenge {
    private String link;
    private ChallengeType type;
    private List<Integer> indicesToHash;
    private ChallengeBody body;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ChallengeType getType() {
        return type;
    }

    public void setType(ChallengeType type) {
        this.type = type;
    }

    public List<Integer> getIndicesToHash() {
        return indicesToHash;
    }

    public void setIndicesToHash(List<Integer> indicesToHash) {
        this.indicesToHash = indicesToHash;
    }

    public ChallengeBody getBody() {
        return body;
    }

    public void setBody(ChallengeBody body) {
        this.body = body;
    }
}
class ChallengeBody {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
enum ChallengeType {
    SHA_256("SHA_256");
    public final String type;
    private ChallengeType(String type) {
        this.type = type;
    }
}