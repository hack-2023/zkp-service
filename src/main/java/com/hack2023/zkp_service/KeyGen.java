package com.hack2023.zkp_service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;

import static java.util.UUID.randomUUID;

public class KeyGen {
    public static void main(String[] args) throws JOSEException {
        // Generate EC key pair on the secp256k1 curve
        ECKey ecJWK = new ECKeyGenerator(Curve.P_256)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(randomUUID().toString())
                .generate();

        // Get the public EC key, for recipients to validate the signatures
        System.out.println(ecJWK);
        System.out.println(ecJWK.toPublicJWK());
    }
}
