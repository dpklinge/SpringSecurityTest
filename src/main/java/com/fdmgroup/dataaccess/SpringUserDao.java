package com.fdmgroup.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.user.User;

/**
 * Specific Spring Data JPA DAO for storing users.
 * @author FDM Group
 *
 */
public interface SpringUserDao extends JpaRepository<User, String> {

}
