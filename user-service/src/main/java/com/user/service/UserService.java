package com.user.service;

import javax.servlet.http.HttpServletRequest;

import com.data.exception.CustomException;
import com.user.model.UserModel;

public interface UserService {

	Object saveUser(UserModel userModel);

	Object getUserWithDepartment(int id) throws CustomException;

	Object updateUser(UserModel usersModel, HttpServletRequest request) throws CustomException;

	Object getAllUser(Integer id, Integer currentPage) throws CustomException;

}
