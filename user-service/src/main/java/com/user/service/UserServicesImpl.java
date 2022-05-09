package com.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.data.constant.Constant;
import com.data.entity.Department;
import com.data.entity.User;
import com.data.exception.CustomException;
import com.user.model.UserModel;
import com.user.repo.UserRepo;

import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServicesImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Mapper mapper;

	@Autowired
	WebClient.Builder webClient;

	@Autowired
	private Environment environment;

	@Override
	public Object saveUser(UserModel userModel) {

		User user = mapper.map(userModel, User.class);

		return userRepo.save(user);
	}

	@Override
	public Object getUserWithDepartment(int id) throws CustomException {

		Map<String, Object> data = new HashMap<>();

		Optional<User> user = userRepo.findById(id);

		if (user.isPresent()) {

			try {

				getDepartmentByMicroservice(user, data);

			} catch (Exception e) {
				log.error("context :: " + e.getMessage());
			}
		} else {

			log.error(environment.getProperty(Constant.EXC_USER_NOT_FOUND));
			throw new CustomException(environment.getProperty(Constant.EXC_USER_NOT_FOUND));

		}

		return data;
	}

	/**
	 * calling department microservices
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 *
	 * 
	 */

	private Map<String, Object> getDepartmentByMicroservice(Optional<User> user, Map<String, Object> data) {

		UserModel userModel = mapper.map(user.get(), UserModel.class);
		Department department = webClient.build().get()
				.uri("http://DEPARTMENT-SERVICE/department/get/" + user.get().getDepartmentId()).retrieve()
				.bodyToMono(Department.class).block();
		if (null == department) {
			data.put(Constant.DEPARTMENT, null);
		} else {
			data.put(Constant.DEPARTMENT, department);
		}

		data.put(Constant.USER, userModel);
		return data;

	}

	@Override
	public Object updateUser(UserModel usersModel, HttpServletRequest request) throws CustomException {
		checkUpdateUserObject(usersModel);
		Map<String, Object> data = new HashMap<>();
		User oldData = userRepo.findByIdAndIsDeleted(usersModel.getId(), false);
		if (null != oldData) {
			setUpdatedValueInUser(oldData, usersModel);

			User user = userRepo.save(oldData);
			data.put(Constant.UPDATE_USER, user);
		} else {

			log.error(environment.getProperty(Constant.EXC_USER_NOT_FOUND));
			throw new CustomException(environment.getProperty(Constant.EXC_USER_NOT_FOUND));

		}

		return data;
	}

	/**
	 * Set updated value in user
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 * @throws CustomException
	 */
	private User setUpdatedValueInUser(User oldData, UserModel usersModel) throws CustomException {

		/**
		 * Here we have one more option to update user like we use mapper and trasnfer
		 * data b/w user moder to user and we save the user entity.
		 */

		oldData.setDeleted(usersModel.isDeleted());
		oldData.setEmail(usersModel.getEmail());
		oldData.setFirstName(usersModel.getFirstName());
		oldData.setLastName(usersModel.getLastName());
		if (oldData.getDepartmentId() != usersModel.getDepartmentId()) {
			log.error(environment.getProperty(Constant.DEPARTMENT_ID_CAN_NOT_CHANGE));
			throw new CustomException(environment.getProperty(Constant.DEPARTMENT_ID_CAN_NOT_CHANGE));

		}
		if (usersModel.isDeleted()) {
			softDeleteDepartment(oldData.getDepartmentId());
		}
		return oldData;

	}

	/**
	 * Deleted (soft) department by id
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 * @param departmentId
	 * @return
	 * @throws CustomException
	 */
	private void softDeleteDepartment(int departmentId) {
		log.info("hrer");
		webClient.build().delete().uri("http://DEPARTMENT-SERVICE/department/delete/" + departmentId).retrieve()
				.bodyToMono(Department.class).block();

	}

	/**
	 * Check update user object
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 * @param usersModel
	 * @throws CustomException
	 */
	public void checkUpdateUserObject(UserModel usersModel) throws CustomException {
		if (null == usersModel.getId()) {
			throwEmptyFieldError();
		}

	}

	/**
	 * Common method to throw field empty error.
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 *
	 * @throws CustomException
	 */
	public void throwEmptyFieldError() throws CustomException {
		log.error(environment.getProperty(Constant.EXC_MISSING_PARAMETERS));
		throw new CustomException(environment.getProperty(Constant.EXC_MISSING_PARAMETERS));
	}

	@Override
	public Object getAllUser(Integer currentPage, Integer totalPerPage) throws CustomException {
		Map<String, Object> data = new HashMap<>();
		Page<User> page = null;
		try {
			Pageable paging = PageRequest.of(currentPage - 1, totalPerPage);

			/// user =
			page = userRepo.findAll(paging);

			if (!page.getContent().isEmpty()) {
				data.put(Constant.USER_LIST,
						com.data.common.util.CustomDozerHelper.map(mapper, page.getContent(), User.class));
				data.put(Constant.USER_COUNT, userRepo.countByIsDeleted(false));
			} else {
				data.put(Constant.USER_LIST, new ArrayList<>());
				data.put(Constant.USER_COUNT, 0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error("context", e);
			throw new CustomException(environment.getProperty(Constant.EXC_SOMETHING_WENT_WRONG));
		}
		return data;

	}
}
