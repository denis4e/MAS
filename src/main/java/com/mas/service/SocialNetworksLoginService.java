package com.mas.service;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class SocialNetworksLoginService {

    public JSONObject fbLogin(Authentication auth) throws Exception {
        JSONObject json = null;
        if (auth != null && auth.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
            String url = "https://graph.facebook.com/me?method=get&fields=email,name,id,first_name,last_name,location,hometown&access_token=" + details.getTokenValue();
            StringBuilder data = HttpService.sendingGetRequest(url);
            json = new JSONObject(data.toString());
        }
        return json;
    }

    public JSONObject googleLogin(Authentication auth) throws Exception {
        JSONObject userData = null;
        if (auth != null && auth.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
            String url = "https://www.googleapis.com/plus/v1/people/me?access_token=" + details.getTokenValue();
            StringBuilder data = HttpService.sendingGetRequest(url);
            userData = new JSONObject(data.toString());
        }
        return userData;
    }
}
