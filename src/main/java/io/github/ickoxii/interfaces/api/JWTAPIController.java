package io.github.ickoxii.interfaces.api;

public interface JWTAPIController {

    String getSigningAlgorithm(String token);

    String getAPIToken(String file);

}
