package com.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data.constant.Constant;
import com.data.exception.CustomException;
import com.user.model.ResponseModel;
import com.user.model.UserModel;
import com.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "user")
@Api(value = "user")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server Error"),
		@ApiResponse(code = 404, message = "Service not found"), @ApiResponse(code = 200, message = "Success") })
@Slf4j
public class UserController {

	@Autowired
	public UserService userService;

	@Autowired
	private Environment environment;

	/**
	 * Save User Information.
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 */
	@PostMapping("/save")
	@ApiOperation(value = "Save user Information.", notes = "This API used to save User.")
	public ResponseEntity<ResponseModel> saveUser(@RequestBody UserModel userModel) {
		log.info("------------ Save User [web service] --------------");
		ResponseModel response = ResponseModel.getInstance();
		response.setData(userService.saveUser(userModel));
		response.setMessage(environment.getProperty(Constant.SUCCESSFULL_SAVE_USER));
		response.setStatusCode(HttpStatus.SC_OK);
		return new ResponseEntity<>(response, org.springframework.http.HttpStatus.OK);

	}

	/**
	 * Get user by unique id
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 * @throws CustomException
	 */
	@GetMapping("/get/{id}")
	@ApiOperation(value = "Get user by unique id", notes = "This API used to Get user by unique id.")
	public ResponseEntity<ResponseModel> getUserWithDepartment(@PathVariable("id") int id) {
		log.info("------------ Save User [web service] --------------");
		ResponseModel response = ResponseModel.getInstance();

		try {
			response.setData(userService.getUserWithDepartment(id));
		} catch (CustomException e) {

			e.printStackTrace();
		}

		response.setMessage(environment.getProperty(Constant.USER_DETAIL_SENT_SUCCESS));
		response.setStatusCode(HttpStatus.SC_OK);
		return new ResponseEntity<>(response, org.springframework.http.HttpStatus.OK);

	}

	/**
	 * update or delete user.
	 * 
	 * @author Mindbowser | suraj.jaiswal@mindbowser.com
	 * @throws CustomException
	 */
	@PostMapping("/update")
	@ApiOperation(value = "Update the user.", notes = "This API used to update user.")
	public ResponseEntity<ResponseModel> updateUser(HttpServletRequest request, @RequestBody UserModel usersModel)
			throws CustomException {
		log.info("------------ In update User [web service] --------------");
		ResponseModel response = ResponseModel.getInstance();
		response.setData(userService.updateUser(usersModel, request));

		if (usersModel.isDeleted()) {
			response.setData(null);
			response.setMessage(environment.getProperty(Constant.MSG_USER_DELETE_SUCCESS));
		} else
			response.setMessage(environment.getProperty(Constant.MSG_USER_UPDATE_SUCCESS));
		response.setStatusCode(HttpStatus.SC_OK);
		return new ResponseEntity<>(response, org.springframework.http.HttpStatus.OK);

	}

	/**
	 * Get Waiting list user.
	 * 
	 * @author Mindbowser | deepak.kumbhar@mindbowser.com
	 * @throws CustomException
	 */

	@GetMapping("/get-all")
	@ApiOperation(value = "get list all user .", notes = "This API used to get all user  list")
	public ResponseEntity<ResponseModel> getEducation(HttpServletRequest request,
			@RequestParam(required = false) Integer currentPage,
			@RequestParam(required = false) Integer totalPerPage) throws CustomException {
		log.info("------------ In get Waiting [web service] --------------");
		ResponseModel response = ResponseModel.getInstance();

		response.setData(userService.getAllUser(currentPage, totalPerPage));
		response.setMessage(environment.getProperty(Constant.USER_LIST_SENT_SUCCESS));
		response.setStatusCode(HttpStatus.SC_OK);

		return new ResponseEntity<>(response, org.springframework.http.HttpStatus.OK);
	}

}
