package org.ordersample.customerservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ordersample.customerservice.BaseTest;
import org.ordersample.customerservice.InstanceTestClassListener;
import org.ordersample.customerservice.SpringInstanceTestClassRunner;
import org.ordersample.customerservice.model.Customer;
import org.ordersample.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringInstanceTestClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceControllerTest extends BaseTest implements InstanceTestClassListener {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String customer = "{\n" +
            "  \"id\": \"3030\",\n" +
            "  \"name\": \"Cris\"\n" +
            "}";

    private static final String customerUpdate = "{\n" +
            "  \"id\": \"2020\",\n" +
            "  \"name\": \"Juana\"\n" +
            "}";

    @Override
    public void beforeClassSetup() {
        customerRepository.save(new Customer("1010", "Carlos Avendano"));
        customerRepository.save(new Customer("2020", "Martha Caro"));
        customerRepository.save(new Customer("4040", "Ricardo"));
    }

    @Test
    public void createCustomerValidation() throws Exception{
        mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customer))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value("3030"));
    }

    @Test
    public void createCustomerShouldReturnNull() throws Exception{
        mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1010\",\"name\": \"someone\"}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void updateCustomerValidation() throws Exception{
        mvc.perform(put("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerUpdate))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value("2020"))
                .andExpect(jsonPath("$.name").value("Juana"));
    }

    @Test
    public void ShouldNotUpdateCustomer() throws Exception{
        mvc.perform(put("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"5050\",\"name\": \"someone\"}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void deleteCustomerValidation() throws Exception{
        mvc.perform(delete("/customer/{customerId}", "4040")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void ShouldNotDeleteCustomer() throws Exception{
        mvc.perform(delete("/customer/{customerId}", "5050")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findAllCustomersValidation() throws Exception{
        mvc.perform(get("/customer")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$[0].id").value("1010"))
            .andExpect(jsonPath("$[1].id").value("2020"));
    }

    @Test
    public void findCustomerValidationShouldBeFine() throws Exception{
        mvc.perform(get("/customer/{customerId}", "1010")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value("1010"))
                .andExpect(jsonPath("$.name").value("Carlos Avendano"));
    }

    @Override
    public void afterClassSetup() {
        customerRepository.deleteAll();
    }
}
