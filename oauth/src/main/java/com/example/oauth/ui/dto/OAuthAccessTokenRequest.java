package com.example.oauth.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthAccessTokenRequest {

    private String client_id;

    private String client_secret;

    private String code;
}
