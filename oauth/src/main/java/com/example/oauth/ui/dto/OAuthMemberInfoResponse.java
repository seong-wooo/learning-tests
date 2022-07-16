package com.example.oauth.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OAuthMemberInfoResponse {

    @JsonProperty("id")
    private Long oauthId;

    @JsonProperty("login")
    private String name;

    @JsonProperty("avatar_url")
    private String profile_url;
}

