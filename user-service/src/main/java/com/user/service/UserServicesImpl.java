package com.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.data.constant.Constant;
import com.data.entity.Department;
import com.data.entity.User;
import com.data.exception.CustomException;
import com.user.model.UserModel;
import com.user.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

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

    private Map<String, Object> getDepartmentByMicroservice(Optional<User> user, Map<String, Object> data) {

        UserModel userModel = mapper.map(user.get(), UserModel.class);
        Department department = webClient.build().get()
                .uri("http://DEPARTMENT-SERVICE/department/get/" + user.get().getDepartmentId()).retrieve()
                .bodyToMono(Department.class).block();
        if (null == department) {
            data.put("department", null);
        } else {
            data.put("department", department);
        }

        data.put("user", userModel);
        return data;

    }

}
