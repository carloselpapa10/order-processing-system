package org.ordersample.apigateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(
		stubsMode = StubRunnerProperties.StubsMode.LOCAL,
		ids = "org.ordersample:order-service:+:stubs:9091")
public class ApiGatewayIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldVerifyExistingOrder() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/OrderService/123456"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.id").value("123456"))
				.andExpect(jsonPath("$.customerId").value("1010"));
	}

	@Test
	public void shouldFailToFindMissingOrder() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/OrderService/321"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}

}
