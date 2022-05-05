package co.department.service;

import co.department.model.DepartmentModel;

public interface DepartmentService {

    Object saveDepartment(DepartmentModel department);

    DepartmentModel findDepartmentById(int id);

}
