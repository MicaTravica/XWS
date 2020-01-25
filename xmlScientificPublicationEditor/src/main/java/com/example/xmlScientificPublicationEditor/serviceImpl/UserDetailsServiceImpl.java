package com.example.xmlScientificPublicationEditor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

// import javax.transaction.Transactional;

import com.example.xmlScientificPublicationEditor.service.PersonService;
import com.example.xmlScientificPublicationEditor.model.authPerson.TAuthPerson;
import com.example.xmlScientificPublicationEditor.model.authPerson.TRole;
import com.example.xmlScientificPublicationEditor.model.person.TPerson;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
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
		TAuthPerson user = null;
		try{
			user = personService.findOneAuth(email);
		}catch(Exception e)
		{
			throw new UsernameNotFoundException("Error while retriving user from DB");
		}
		// if(!user.isVerified()) {
		// 	throw new UsernameNotFoundException("You need to verify your email.");
		// }
		// treba sad da se pretvori u objekat..
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		user.getRoles().getRole().forEach(r->{
			if(r.toString().equals(TRole.USER.toString())) {
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_REGULAR"));
			}
			if(r.toString().equals(TRole.REDACTOR.toString())) {
				grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
		});
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
	    		user.getPassword(),
	    		grantedAuthorities);
	}

}
