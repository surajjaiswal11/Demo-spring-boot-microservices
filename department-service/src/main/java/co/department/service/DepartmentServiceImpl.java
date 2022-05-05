package co.department.service;

import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.entity.Department;
import com.data.repo.DepartmentRepo;

import co.department.model.DepartmentModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired(required = true)
    public DepartmentRepo departmentRepo;

    @Autowired
    private Mapper mapper;

    @Override
    public Object saveDepartment(DepartmentModel departmentModel) {
        // need to mapper
        Department department = mapper.map(departmentModel, Department.class);
        return departmentRepo.save(department);

    }

    @Override
    public DepartmentModel findDepartmentById(int id) {
        log.info("department id : " +id);
        Optional<Department> dep = departmentRepo.findById(id);
       
        if (dep.isPresent()) {
            log.info("department : " + dep.get());
            return mapper.map(dep.get(), DepartmentModel.class);

        }
        return null;
    }

}
