package com.user.model;

import java.io.Serializable;

import org.apache.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResponseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String error;
    private Object data;
    private Integer statusCode;
    private String message;

    public static ResponseModel getInstance() {
        ResponseModel response = new ResponseModel();
        response.setStatusCode(HttpStatus.SC_OK);
        return response;
    }

}
