package com.example.petAdoption.service;

import com.example.petAdoption.model.UserPrincipal;
import com.example.petAdoption.model.Users;
import com.example.petAdoption.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username);

        if(user == null){
//            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

//        System.out.println("User is not null");
//        System.out.println(user);

        UserDetails rtn = new UserPrincipal(user);

//        System.out.println("Here <-> Here");
//        System.out.println(rtn.getUsername());
//        System.out.println(rtn.getPassword());

        return rtn;
    }
}
