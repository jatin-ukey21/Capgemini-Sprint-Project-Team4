package com.example.DemoCheck.projection;

import com.example.DemoCheck.entity.Employee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "employeeView", types = Employee.class)
public interface EmployeeProjection {

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();   // calls entity method

    String getExtension();
    String getEmail();
    String getReportsTo();
    String getJobTitle();
}
