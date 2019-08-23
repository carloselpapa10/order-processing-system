package org.ordersample.invoiceservice;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.io.StringReader;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.ordersample.invoiceservice.MessagingBase;
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
	public void validate_requestInvoice() throws Exception {
		// given:
			ContractVerifierMessage inputMessage = contractVerifierMessaging.create(
				"{\"invoiceDTO\":{\"invoiceId\":\"1\",\"orderId\":\"111\",\"invoiceComment\":\"Invoice Comment\"}}"
				, headers()
						.header("command_type", "org.ordersample.domaininfo.invoice.api.commands.RequestInvoiceCommand")
						.header("command_saga_type", "org.ordersample.orderservice.saga.createorder.CreateOrderSaga")
						.header("command_saga_id", "cfea15fd6d03fb1b-1f3328ab2cba3110")
						.header("command_reply_to", "org.ordersample.orderservice.saga.createorder.CreateOrderSaga-reply")
			);

		// when:
			contractVerifierMessaging.send(inputMessage, "invoiceservice");

		// then:
			ContractVerifierMessage response = contractVerifierMessaging.receive("org.ordersample.orderservice.saga.createorder.CreateOrderSaga-reply");
			assertThat(response).isNotNull();
			assertThat(response.getHeader("reply_type")).isNotNull();
			assertThat(response.getHeader("reply_type").toString()).isEqualTo("org.ordersample.domaininfo.invoice.api.info.InvoiceDTO");
			assertThat(response.getHeader("reply_outcome-type")).isNotNull();
			assertThat(response.getHeader("reply_outcome-type").toString()).isEqualTo("SUCCESS");
		// and:
			DocumentContext parsedJson = JsonPath.parse(contractVerifierObjectMapper.writeValueAsString(response.getPayload()));
			assertThatJson(parsedJson).field("['orderId']").isEqualTo("111");
			assertThatJson(parsedJson).field("['invoiceId']").isEqualTo("1");
			assertThatJson(parsedJson).field("['invoiceComment']").isEqualTo("Invoice Comment");
	}

}
