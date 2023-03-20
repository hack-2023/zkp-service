package com.hack2023.zkp_service.domain.login;

import com.hack2023.zkp_service.domain.token.TokenService;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.hack2023.zkp_service.domain.login.ChallengeType.SHA_256;

public class LoginService {
    private UserRepositoryPort userRepository;
    private TokenService tokenService;

    public LoginService(UserRepositoryPort userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) throws ParseException, JOSEException {
        String hash = userRepository
                .findUserByEmail(request.getEmail())
                .map(u -> u.getSomehash())
                .orElseThrow(() -> new RuntimeException("User not found"));
        int length = hash.length();
        int challengeLength = ThreadLocalRandom.current().nextInt(length/2, length);
        int totalRounds = ThreadLocalRandom.current().nextInt(100, 500);
        Set<Integer> set = new Random().ints(0, length)
                .distinct()
                .limit(challengeLength)
                .boxed()
                .collect(Collectors.toSet());
        LoginResponse response = new LoginResponse();
        ChallengeBody body = new ChallengeBody();
        body.setToken(tokenService.generateChallengeToken(request.getEmail(), List.copyOf(set), totalRounds));
        Challenge challenge = new Challenge();
        challenge.setType(SHA_256);
        challenge.setLink("http://localhost:8080/challenge");
        challenge.setIndicesToHash(List.copyOf(set));
        challenge.setBody(body);
        response.setEmail(request.getEmail());
        response.setChallenge(challenge);
        return response;
    }
}
