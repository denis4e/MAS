package com.mas.service;

import com.mas.dao.repository.UserRepository;
import com.mas.domain.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
public class SocialNetworksLoginService {
    @Autowired
    private HttpService httpService;

    @Autowired
    private UserRepository userRepository;

    public User fbLogin(Authentication auth) throws Exception {
        User user = null;
        if (auth != null && auth.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
            String url = "https://graph.facebook.com/me?method=get&fields=email,name,id,first_name,last_name,location,hometown&access_token=" + details.getTokenValue();
            StringBuilder data = httpService.sendingGetRequest(url);
            JSONObject json = new JSONObject(data.toString());
            user = userRepository.findUserByfbId(json.get("id").toString());
        }
        return user;
    }
}
