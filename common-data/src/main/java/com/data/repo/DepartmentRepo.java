package com.data.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.data.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {

	Optional<Department> findByIdAndIsDeleted(int id, boolean b);

}
