package com.hack2023.zkp_service.domain.login;

import com.hack2023.zkp_service.domain.token.TokenService;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.hack2023.zkp_service.domain.login.ChallengeState.CONTINUE;
import static com.hack2023.zkp_service.domain.login.ChallengeState.FAILURE;
import static com.hack2023.zkp_service.domain.login.ChallengeState.SUCCESS;
import static com.hack2023.zkp_service.domain.login.ChallengeType.SHA_256;
import static org.assertj.core.api.Assertions.assertThat;
public class ChallengeService {
    private UserRepositoryPort userRepository;
    private TokenService tokenService;

    public ChallengeService(UserRepositoryPort userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public ChallengeResponse verifyAnswer(ChallengeRequest request) throws ParseException, JOSEException {
        ChallengeResponse response = new ChallengeResponse();
        ChallengeData challengeData = toChallengeData(request);
        String sha3Hex = new DigestUtils("SHA3-256").digestAsHex(challengeData.getDataFromIndices());
        if(! sha3Hex.equals(request.getChallengeAnswer())) {
            response.setChallengeState(FAILURE);
            return response;
        }

        if(challengeData.getTotalRounds() == challengeData.getRound()+1) {
            response.setChallengeState(SUCCESS);
            response.setToken(tokenService.generateLoginToken(request.getEmail()));
            return response;
        }

        return toChallengeResponse(challengeData);
    }

    private ChallengeResponse toChallengeResponse(ChallengeData challengeData) throws ParseException, JOSEException {
        ChallengeResponse response = new ChallengeResponse();
        ChallengeBody body = new ChallengeBody();
        List<Integer> indicesToHash = challengeData.getIndicesAsList();
        body.setToken(tokenService.generateChallengeToken(challengeData.getEmail(), indicesToHash, challengeData.getTotalRounds(), challengeData.getRound()+1));
        Challenge challenge = new Challenge();
        challenge.setType(SHA_256);
        challenge.setLink("/challenge");
        challenge.setIndicesToHash(indicesToHash);
        challenge.setBody(body);
        response.setEmail(challengeData.getEmail());
        response.setChallenge(challenge);
        response.setChallengeState(CONTINUE);
        return response;
    }

    private ChallengeData toChallengeData(ChallengeRequest request) throws ParseException {
        ChallengeData challengeData = new ChallengeData();
        JWTClaimsSet claimsSet = tokenService.consumeJweToken(request.getToken());
        assertThat(claimsSet.getSubject()).isEqualTo(request.getEmail());
        //assertThat(claimsSet.getExpirationTime()).isAfter(Instant.now());
        challengeData.setEmail(request.getEmail());
        challengeData.setRound(claimsSet.getIntegerClaim("round"));
        challengeData.setTotalRounds(claimsSet.getIntegerClaim("totalRounds"));
        String hash = userRepository
                .findUserByEmail(request.getEmail())
                .map(u -> u.getSomehash())
                .orElseThrow(() -> new RuntimeException("User not found"));
        challengeData.setHashLength(hash.length());
        challengeData.setDataFromIndices(dataFromIndices(hash, (List<Long>) claimsSet.getClaim("indicesToHash")));
        return challengeData;
    }

    private String dataFromIndices(String hash, List<Long> indicesToHash) {
        return indicesToHash
                .stream()
                .map(l -> hash.charAt(Long.valueOf(l).intValue()))
                .map(c -> c.toString())
                .collect(Collectors.joining());
    }
}
