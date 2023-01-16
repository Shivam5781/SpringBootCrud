
package com.Shivam.Crud.Project.controllers;

import com.Shivam.Crud.Project.Employee;
import com.Shivam.Crud.Project.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @MockBean
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeController employeeController;


    Employee Employee_1 = new Employee(1L, "Shiva", "Shivamgaur@gmail.com", "Cisco");
    Employee Employee_2 = new Employee(2L, "Rishabh", "Rishabh@gmail.com", "Oracle");

    Employee Employee_3 = new Employee(3L, "Amit", "Amit@gmail.com", "hcl");


    @Test
    public void showEmployees() throws Exception {
        List<Employee> records = new ArrayList<>(Arrays.asList(Employee_1, Employee_2, Employee_3));
        Mockito.when(employeeRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

//                .andExpect(jsonPath("$" ,hasSize(3)))
//                .andExpect(jsonPath("$[2].name",is("Amit")));
    }


    @Test
    public void saveEmployee() throws Exception {
        Employee record = Employee.builder()
                .id(4L)
                .name("Abhishek")
                .email("Abhisheksingh@gmail.com")
                .department("Oracle").build();
        Mockito.when(employeeRepository.save(record)).thenReturn(record);
        String content = objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/saveEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
//                .andExpect(jsonPath("$[3].name",is("Abhishek")));

    }
}




