package io.github.ickoxii.interfaces;

public interface JWTAPIController {

    String getSigningAlgorithm(String token);

    String getAPIToken(String file);

}
