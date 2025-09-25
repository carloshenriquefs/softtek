package br.com.softtek.softtek.application.service;

import br.com.softtek.softtek.adapters.outbound.jpa.repositories.MongoUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final MongoUserRepository mongoUserRepository;

    public AuthorizationService(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mongoUserRepository.findByEmail(username);
    }
}
