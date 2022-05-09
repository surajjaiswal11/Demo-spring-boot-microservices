package com.user.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.data.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	User findByIdAndIsDeleted(Integer id, boolean b);

	Object countByIsDeleted(boolean b);

	List<User> findByIsDeleted(boolean b, Pageable page);

     

}
