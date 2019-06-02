package org.ordersample.orderservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.io.StringReader;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.ordersample.orderservice.MessagingBase;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static com.toomuchcoding.jsonassert.JsonAssertion.assertThatJson;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.verifier.messaging.util.ContractVerifierMessagingUtil.headers;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.*;
import static org.springframework.cloud.contract.verifier.util.ContractVerifierUtil.fileToBytes;

public class MessagingTest extends MessagingBase {

	@Inject ContractVerifierMessaging contractVerifierMessaging;
	@Inject ContractVerifierObjectMapper contractVerifierObjectMapper;

	@Test
	public void validate_shouldCreateOrderEvent() throws Exception {
		// when:
			orderCreated();

		// then:
			ContractVerifierMessage response = contractVerifierMessaging.receive("org.ordersample.orderservice.model.Order");
			assertThat(response).isNotNull();
			assertThat(response.getHeader("event-aggregate-type")).isNotNull();
			assertThat(response.getHeader("event-aggregate-type").toString()).isEqualTo("org.ordersample.orderservice.model.Order");
			assertThat(response.getHeader("event-type")).isNotNull();
			assertThat(response.getHeader("event-type").toString()).isEqualTo("org.ordersample.domaininfo.order.api.events.OrderCreatedEvent");
			assertThat(response.getHeader("event-aggregate-id")).isNotNull();
			assertThat(response.getHeader("event-aggregate-id").toString()).isEqualTo("111");
		// and:
			DocumentContext parsedJson = JsonPath.parse(contractVerifierObjectMapper.writeValueAsString(response.getPayload()));
			assertThatJson(parsedJson).field("['orderDTO']").field("['id']").isEqualTo("111");
			assertThatJson(parsedJson).field("['orderDTO']").field("['invoiceId']").isEqualTo("123");
			assertThatJson(parsedJson).field("['orderDTO']").field("['customerId']").isEqualTo("1010");
			assertThatJson(parsedJson).field("['orderDTO']").field("['description']").isEqualTo("Order Description");
	}

}
