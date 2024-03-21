package com.migzus.api.student_overview.security.services;

import com.migzus.api.student_overview.models.Teacher;
import com.migzus.api.student_overview.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    TeacherRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Teacher user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Teacher not found with email " + email));
        return UserDetailsImpl.build(user);
    }
}