package com.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.data.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = false)

@Data
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_address")
    private String departmentAddress;

    @Column(name = "department_code")
    private String departmentCode;
}
