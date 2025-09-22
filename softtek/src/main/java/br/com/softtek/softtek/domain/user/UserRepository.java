package br.com.softtek.softtek.domain.user;

public interface UserRepository {

    User findByEmail(String email);
}
