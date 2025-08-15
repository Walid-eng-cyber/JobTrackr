package com.jobtracker.backend.dto;

/**
 * This class represents the response object sent back to the user after a successful
 * login. It contains the JWT access token and the type of token (in this case, "Bearer").
 * 
 * The accessToken field contains the JWT access token, which is a JSON Web Token
 * that is used to authenticate the user on subsequent requests to the API. The
 * token is signed with a secret key, which can be configured in the application.yml
 * file.
 * 
 * The tokenType field is set to "Bearer", which is the standard type for JWT
 * access tokens. This type is used by the client to indicate that it is sending
 * a JWT access token in the Authorization header of the request.
 */


public class JwtAuthenticationResponse {
    /**
     * The accessToken field contains the JWT access token, which is a JSON Web Token
     * that is used to authenticate the user on subsequent requests to the API. The
     * token is signed with a secret key, which can be configured in the application.yml
     * file.
     */
    private String accessToken;
    
    /**
     * The tokenType field is set to "Bearer", which is the standard type for JWT
     * access tokens. This type is used by the client to indicate that it is sending
     * a JWT access token in the Authorization header of the request.
     */
    private String tokenType = "Bearer";
    
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
