package com.hack2023.zkp_service.domain.login;

import com.hack2023.zkp_service.domain.token.TokenService;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

import static com.hack2023.zkp_service.domain.login.ChallengeType.SHA_256;

public class LoginService {
    private UserRepositoryPort userRepository;
    private TokenService tokenService;
    private int minChallengeRounds;
    private int maxChallengeRounds;

    public LoginService(UserRepositoryPort userRepository, TokenService tokenService, int minChallengeRounds, int maxChallengeRounds) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.minChallengeRounds = minChallengeRounds;
        this.maxChallengeRounds = maxChallengeRounds;
    }

    public LoginResponse login(LoginRequest request) throws ParseException, JOSEException {
        ChallengeData challengeData = computeChallengeData(request.getEmail());
        return toLoginResponse(challengeData);
    }

    private ChallengeData computeChallengeData(String email) {
        ChallengeData challengeData = new ChallengeData(minChallengeRounds, maxChallengeRounds);
        challengeData.setEmail(email);
        String hash = userRepository
                .findUserByEmail(email)
                .map(u -> u.getSomehash())
                .orElseThrow(() -> new RuntimeException("User not found"));
        challengeData.setHashLength(hash.length());
        challengeData.setTotalRounds(challengeData.computeTotalRounds());
        return challengeData;
    }

    private LoginResponse toLoginResponse(ChallengeData data) throws ParseException, JOSEException {
        LoginResponse response = new LoginResponse();
        ChallengeBody body = new ChallengeBody();
        List<Integer> indicesToHash = data.getIndicesAsList();
        body.setToken(tokenService.generateChallengeToken(data.getEmail(), indicesToHash, data.getTotalRounds()));
        Challenge challenge = new Challenge();
        challenge.setType(SHA_256);
        challenge.setLink("/challenge");
        challenge.setIndicesToHash(indicesToHash);
        challenge.setBody(body);
        response.setEmail(data.getEmail());
        response.setChallenge(challenge);
        return response;
    }
}
