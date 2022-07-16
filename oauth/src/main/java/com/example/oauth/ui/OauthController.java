package com.example.oauth.ui;

import com.example.oauth.ui.dto.OAuthAccessTokenRequest;
import com.example.oauth.ui.dto.OAuthAccessTokenResponse;
import com.example.oauth.ui.dto.OAuthMemberInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OauthController {
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String MEMBER_INFO_URL = "https://api.github.com/user";

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${security.oauth.github.client-id}")
    private String clientId;
    @Value("${security.oauth.github.client-secret}")
    private String clientSecret;


    @GetMapping("/auth/login")
    public ResponseEntity<String> getUserInfo(@RequestParam String code) {
        String accessToken = getAccessToken(code);
        String userName = getUserName(accessToken);
        return ResponseEntity.ok(userName);
    }

    private String getAccessToken(String code) {
        return restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                new OAuthAccessTokenRequest(clientId, clientSecret, code),
                OAuthAccessTokenResponse.class
        ).getAccessToken();
    }

    public String getUserName(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                MEMBER_INFO_URL,
                HttpMethod.GET,
                request,
                OAuthMemberInfoResponse.class
        ).getBody().getName();
    }
}
