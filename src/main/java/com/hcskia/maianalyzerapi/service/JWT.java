package com.hcskia.maianalyzerapi.service;

import org.jose4j.jwa.*;
import org.jose4j.jwe.*;
import org.jose4j.keys.*;
import org.jose4j.lang.*;

import java.security.Key;

public class JWT {
    public static String CreatJWT(String PayLoad) throws JoseException {
        Key key = new AesKey("huancaiskiamaise".getBytes());//ByteUtil.randomBytes(16)
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(PayLoad);
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setKey(key);
        return jwe.getCompactSerialization();
    }

    public static String getJWTPayLoad(String JWT) throws JoseException {
        Key key = new AesKey("huancaiskiamaise".getBytes());
        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        jwe.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                KeyManagementAlgorithmIdentifiers.A128KW));
        jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT,
                ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
        jwe.setKey(key);
        jwe.setCompactSerialization(JWT);
        return jwe.getPayload();
    }
}
