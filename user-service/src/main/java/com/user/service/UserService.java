package com.user.service;

import com.data.exception.CustomException;
import com.user.model.UserModel;

public interface UserService {

    Object saveUser(UserModel userModel);

    Object getUserWithDepartment(int id) throws CustomException;

}
