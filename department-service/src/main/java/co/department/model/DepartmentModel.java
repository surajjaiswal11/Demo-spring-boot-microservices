package co.department.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class DepartmentModel extends BaseModel {

    private String departmentName;

    private String departmentAddress;

    private String departmentCode;
}
