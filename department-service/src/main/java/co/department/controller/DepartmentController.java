package co.department.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.data.constant.Constant;

import co.department.model.DepartmentModel;
import co.department.model.ResponseModel;
import co.department.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "department")
@Api(value = "department")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server Error"),
        @ApiResponse(code = 404, message = "Service not found"), @ApiResponse(code = 200, message = "Success") })
@Slf4j
public class DepartmentController {

    @Autowired
    public DepartmentService departmentService;

    @Autowired
    private Environment environment;

    /**
     * Save department.
     * 
     * @author Mindbowser | suraj.jaiswal@mindbowser.com
     */

    @PostMapping("/save")
    @ApiOperation(value = "Save department of user.", notes = "This API used to save department.")
    public ResponseEntity<ResponseModel> saveDepartment(@RequestBody DepartmentModel department) {
        log.info("------------ Save Department [web service] --------------");
        ResponseModel response = ResponseModel.getInstance();
        response.setData(departmentService.saveDepartment(department));
        response.setMessage(environment.getProperty(Constant.SUCCESSFULL_SAVE_DEPARTMENT));
        response.setStatusCode(HttpStatus.SC_OK);
        return new ResponseEntity<>(response, org.springframework.http.HttpStatus.OK);

    }

    /**
     * Find department by id.
     * 
     * @author Mindbowser | suraj.jaiswal@mindbowser.com
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "find department by unique department id.", notes = "This API used to get department.")
    public DepartmentModel findDepartmentById(@PathVariable("id") int id) {
        log.info("------------ Get Department [web service] --------------");
        return departmentService.findDepartmentById(id);

    }

}
