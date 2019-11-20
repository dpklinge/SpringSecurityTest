package com.fdmgroup.user;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.dataaccess.SpringUserDao;

/**
 * This class overrides Spring's UserDetailsService to utilize our DAO and UserDetailsWrapper
 * @author FDM Group
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private SpringUserDao dao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.trace("UserDetailService checking if user exists in database.");
		Optional<User> user = dao.findById(username);
		if (!user.isPresent()) {
			logger.warn("User "+username+" not found!");
            throw new UsernameNotFoundException("User not found.");
        }
		logger.debug("Returning user information: "+user.get());
		UserDetails details = user.get();
		return details;
	}

}
