package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

// import javax.transaction.Transactional;

import com.example.xmlScientificPublicationEditor.service.PersonService;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private PersonService personService;
	
	@Override
	// @Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		TPerson user = null;
		try{
			user = personService.findOne(email);
		}catch(Exception e)
		{
			throw new UsernameNotFoundException("Error while retriving user from DB");
		}
		// if(!user.isVerified()) {
		// 	throw new UsernameNotFoundException("You need to verify your email.");
		// }
		// treba sad da se pretvori u objekat..
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		// grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().toString()));
		return new org.springframework.security.core.userdetails.User(
	    		user.getEmail(),
	    		"password",
	    		grantedAuthorities);
	}

}
