package com.hack2023.zkp_service.domain.token;


import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.ECDHDecrypter;
import com.nimbusds.jose.crypto.ECDHEncrypter;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;

public class TokenService {
    private String ecKey;

    public TokenService(String ecKey) {
        this.ecKey = ecKey;
    }

    public String generateChallengeToken(String email, List<Integer> indicesToHash, int totalRounds) throws ParseException, JOSEException {
        return generateChallengeToken(email, indicesToHash, totalRounds, 0);
    }

    public String generateChallengeToken(String email, List<Integer> indicesToHash, int totalRounds, int round) throws ParseException, JOSEException {
        SignedJWT signedJWT = generateSignedJWT(email, indicesToHash, totalRounds, round);
        JWEObject jweObject = generateJWE(signedJWT);
        // Serialise to JWE compact form
        String jweString = jweObject.serialize();
        return jweString;
    }

    public String generateLoginToken(String email) throws ParseException, JOSEException {
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(key().getKeyID()).build(),
                new JWTClaimsSet.Builder()
                        .subject(email)
                        .issueTime(Date.from(now()))
                        .expirationTime(Date.from(now().plusSeconds(120)))
                        .issuer("https://ms.com")
                        .claim("zkp-challenge", "success")
                        .build());
        signedJWT.sign(new ECDSASigner(key()));
        JWEObject jweObject = generateJWE(signedJWT);
        return jweObject.serialize();
    }

    public JWTClaimsSet consumeJweToken(String token) {
        try {
            // Parse the JWE string
            JWEObject jweObject = JWEObject.parse(token);

            // Decrypt with private key
            jweObject.decrypt(new ECDHDecrypter(key()));

            // Extract payload
            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

            // Check the signature
            assertThat(signedJWT.verify(new ECDSAVerifier(key().toECPublicKey()))).isTrue();

            // Retrieve the JWT claims...
            return signedJWT.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private JWEObject generateJWE(SignedJWT signedJWT) throws JOSEException, ParseException {
        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.ECDH_ES_A256KW, EncryptionMethod.A256GCM)
                        .contentType("JWT") // required to indicate nested JWT
                        .build(),
                new Payload(signedJWT));

        // Encrypt with the recipient's public key
        jweObject.encrypt(new ECDHEncrypter(key().toECPublicKey()));

        return jweObject;
    }

    private SignedJWT generateSignedJWT(String email, List<Integer> indicesToHash, int totalRounds, int round) throws JOSEException, ParseException {
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(key().getKeyID()).build(),
                new JWTClaimsSet.Builder()
                        .subject(email)
                        .issueTime(Date.from(now()))
                        .expirationTime(Date.from(now().plusSeconds(120)))
                        .issuer("https://ms.com")
                        .claim("indicesToHash", indicesToHash)
                        .claim("round", round)
                        .claim("totalRounds", totalRounds)
                        .build());
        signedJWT.sign(new ECDSASigner(key()));

        return signedJWT;
    }
    private ECKey key() throws ParseException {
        return ECKey.parse(ecKey);
    }
}
