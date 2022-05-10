package usta.com.jwtdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import usta.com.jwtdemo.entity.UsersEntity;
import usta.com.jwtdemo.repository.UsersRepository;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(@RequestBody String username) throws UsernameNotFoundException {

        UsersEntity us = usersRepository.findByUsername(username);
        String user = us.getUsername();
        String password = us.getPassword();
        //Aqui se agrega la logica para obtener el usuario de la bd...
        return new User(user, password, new ArrayList<>());
    }
}
