package com.investree.demo.view.oauth;

import com.investree.demo.model.oauth.User;
import com.investree.demo.repository.oauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(String.format("Username %s is not found", username));
        }
        return user;
    }

    @CacheEvict("oauth_username")
    public void clearCache(String s) {

    }

}
