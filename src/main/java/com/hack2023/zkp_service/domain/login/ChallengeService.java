package com.hack2023.zkp_service.domain.login;

import com.hack2023.zkp_service.domain.token.TokenService;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.hack2023.zkp_service.domain.login.ChallengeType.SHA_256;
import static org.assertj.core.api.Assertions.assertThat;
public class ChallengeService {
    private UserRepositoryPort userRepository;
    private TokenService tokenService;

    public ChallengeService(UserRepositoryPort userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public ChallengeResponse verifyAnswer(ChallengeRequest request) throws ParseException {
        ChallengeResponse response = new ChallengeResponse();
        JWTClaimsSet claimsSet = tokenService.consumeJweToken(request.getToken());
        assertThat(claimsSet.getSubject()).isEqualTo(request.getEmail());
        //assertThat(claimsSet.getExpirationTime()).isAfter(Instant.now());
        int rounds = claimsSet.getIntegerClaim("round");
        int totalRounds = claimsSet.getIntegerClaim("totalRounds");

        String hash = userRepository
                .findUserByEmail(request.getEmail())
                .map(u -> u.getSomehash())
                .orElseThrow(() -> new RuntimeException("User not found"));
        int length = hash.length();

        List<Long> indicesToHash = (List<Long>) claimsSet.getClaim("indicesToHash");
        String dataFromIndices = indicesToHash
                .stream()
                .map(l -> hash.charAt(Long.valueOf(l).intValue()))
                .map(c -> c.toString())
                .collect(Collectors.joining());
        String sha3Hex = new DigestUtils("SHA3-256").digestAsHex(dataFromIndices);
        if(! sha3Hex.equals(request.getChallengeAnswer())) {
            response.setChallengeState(ChallengeState.FAILURE);
            return response;
        }

        if(totalRounds == rounds+1) {
            response.setChallengeState(ChallengeState.SUCCESS);
            return response;
        }

        int challengeLength = ThreadLocalRandom.current().nextInt(length/2, length);
        Set<Integer> set = new Random().ints(0, length)
                .distinct()
                .limit(challengeLength)
                .boxed()
                .collect(Collectors.toSet());

        ChallengeBody body = new ChallengeBody();
        body.setToken(tokenService.generateChallengeToken(request.getEmail(), List.copyOf(set), totalRounds, rounds+1));
        Challenge challenge = new Challenge();
        challenge.setType(SHA_256);
        challenge.setLink("http://localhost:8080/challenge");
        challenge.setIndicesToHash(List.copyOf(set));
        challenge.setBody(body);
        response.setEmail(request.getEmail());
        response.setChallenge(challenge);
        response.setChallengeState(ChallengeState.CONTINUE);
        return response;
    }
}
