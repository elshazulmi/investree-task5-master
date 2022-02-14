package com.investree.demo.view.oauth;

import com.investree.demo.repository.oauth.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class Oauth2ClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        ClientDetails client = clientRepository.findOneByClientId(s);
        if(client == null){
            throw new ClientRegistrationException("Client not found");

        }
        return client;
    }

    public void clearCache(String s){
        System.out.println("ini cache  oauth_client_id=  "+s);
    }
}
