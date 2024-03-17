package org.ickoxii;

import com.auth0.jwt.exceptions.JWTCreationException;
import java.io.*;
import java.security.*;
import java.util.Base64;


public class Main
{
    public static void main( String[] args )
    {
        PublicKey rsaPublicKey;
        PrivateKey rsaPrivateKey;
        try {
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        } catch(JWTCreationException exception) {
            // Invalid Signing configuration / Couldn't convert Claims.
        }
    }
}
