package br.com.softtek.softtek.application.service;

import br.com.softtek.softtek.adapters.outbound.jpa.repositories.JpaUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final JpaUserRepository jpaUserRepository;

    public AuthorizationService(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) jpaUserRepository.findByEmail(username);
    }
}
