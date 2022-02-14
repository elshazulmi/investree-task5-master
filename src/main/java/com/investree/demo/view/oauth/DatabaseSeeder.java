package com.investree.demo.view.oauth;

import com.investree.demo.model.oauth.Client;
import com.investree.demo.model.oauth.Role;
import com.investree.demo.model.oauth.RolePath;
import com.investree.demo.model.oauth.User;
import com.investree.demo.repository.oauth.ClientRepository;
import com.investree.demo.repository.oauth.RolePathRepository;
import com.investree.demo.repository.oauth.RoleRepository;
import com.investree.demo.repository.oauth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {

    //private static final String TAG = "DatabaseSeeder {}";

    private Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolePathRepository rolePathRepository;

    private String defaultPassword = "password";

    private String[] users = new String[]{
            "admin@mail.com:ROLE_SUPERUSER ROLE_USER ROLE_ADMIN",
            "user@mail.com:ROLE_USER"
    };

    private String[] clients = new String[]{
            "my-client-apps:ROLE_READ ROLE_WRITE", // mobile
            "my-client-web:ROLE_READ ROLE_WRITE" // web
    };

    private String[] roles = new String[] {
            "ROLE_SUPERUSER:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_ADMIN:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_USER:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_READ:oauth_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_WRITE:oauth_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS"
    };

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        String password = encoder.encode(defaultPassword);

        this.insertRoles();
        this.insertClients(password);
        this.insertUser(password);

    }

    @Transactional
    public void insertRoles(){
        for (String role: roles){
            String[] str = role.split(":");
            String name = str[0];
            String type = str[1];
            String pattern = str[2];
            String[] methods = str[3].split("\\|");

            Role oldRole = roleRepository.findOneByName(name);

            if(oldRole == null){
                oldRole = new Role();
                oldRole.setName(name);
                oldRole.setType(type);
                oldRole.setRolePaths(new ArrayList<>());
                for (String m: methods){
                    String rolePathName = name.toLowerCase()+"_"+m.toLowerCase();
                    RolePath rolePath = rolePathRepository.findOneByName(rolePathName);
                    if (rolePath == null){
                        rolePath = new RolePath();
                        rolePath.setName(rolePathName);
                        rolePath.setMethod(m.toUpperCase());
                        rolePath.setPattern(pattern);
                        rolePath.setRole(oldRole);
                        rolePathRepository.save(rolePath);
                        oldRole.getRolePaths().add(rolePath);
                    }
                }
            }
            roleRepository.save(oldRole);
        }
    }

    public void insertClients(String password){

        for(String c: clients){
            String[] s = c.split(":");
            String clientName = s[0];
            String[] clientRoles = s[1].split("\\s");
            Client oldClient = clientRepository.findOneByClientId(clientName);
            if(oldClient == null){
                oldClient = new Client();
                oldClient.setClientId(clientName);
                oldClient.setAccessTokenValiditySeconds(28800);
                oldClient.setRefreshTokenValiditySeconds(7257600);
                oldClient.setGrantTypes("password refresh_token authorization_code");
                oldClient.setClientSecret(password);
                oldClient.setApproved(true);
                oldClient.setRedirectUris("");
                oldClient.setScopes("read write");
                List<Role> listRole = roleRepository.findByNameIn(clientRoles);
                if(listRole.size() > 0){
                    oldClient.getAuthorities().addAll(listRole);

                }
            }
            clientRepository.save(oldClient);
        }
    }

    public void insertUser(String password){

        for (String userNames: users){
            String[] str = userNames.split(":");
            String users = str[0];
            String[] roleNames = str[1].split("\\s");

            User olduser = userRepository.findOneByUsername(users);
            if(olduser == null){
                olduser = new User();
                olduser.setUsername(users);
                olduser.setPassword(password);
                List<Role> r = roleRepository.findByNameIn(roleNames);
                olduser.setRoles(r);
            }
            userRepository.save(olduser);
        }
    }
}
