package com.acmetelecom;

import com.acmetelecom.billgenerator.BillGenerator;
import com.acmetelecom.billgenerator.Printer;
import com.acmetelecom.billingsystem.LineItem;
import com.acmetelecom.billingsystem.TelecomBillGenerator;
import com.acmetelecom.billingsystem.TelecomCustomer;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.acmetelecom.TelecomMockFactory.createFakeCall;
import static com.acmetelecom.TelecomMockFactory.createFakeCustomer;
import static org.mockito.Mockito.*;


public class BillGeneratorTests {
	
	private Printer printer;
	
	@BeforeMethod
	public void setupPrinter() {
		this.printer = mock(Printer.class);
	}
	
	@Test
	public void testBillIsGeneratedForOneItem() {
		String name = "TestPerson";
		String phoneNumber = "1";
		String tariff = "normal";
		TelecomCustomer cust = createFakeCustomer(name, phoneNumber, tariff);
		TelecomBillGenerator billGenerator = new BillGenerator(printer);
		DateTime startTime = new DateTime();
		List<LineItem> lineItems = Arrays.asList(
				new LineItem(createFakeCall(name, startTime, startTime.plusMinutes(5)), new BigDecimal("750")));
		billGenerator.send(cust, lineItems);
		verify(printer).printHeading(name, phoneNumber, tariff);
		verify(printer).printItem(any(String.class), eq(name), eq("5:00"), eq("7.50"));
		verify(printer).printTotal("7.50");
	}
	
	@Test
	public void testBillIsGeneratedForMultipleItems() {
		String name = "TestPerson";
		String phoneNumber = "1";
		String tariff = "normal";
		TelecomCustomer cust = createFakeCustomer(name, phoneNumber, tariff);
		TelecomBillGenerator billGenerator = new BillGenerator(printer);
		DateTime startTime = new DateTime();
		List<LineItem> lineItems = Arrays.asList(
				new LineItem(createFakeCall(name, startTime, startTime.plusMinutes(5)), new BigDecimal("750")),
				new LineItem(createFakeCall(name, startTime, startTime.plusMinutes(30)), new BigDecimal("1240"))
				);
	    billGenerator.send(cust, lineItems);
		verify(printer).printHeading(name, phoneNumber, tariff);
		verify(printer).printItem(any(String.class), eq(name), eq("5:00"), eq("7.50"));
		verify(printer).printItem(any(String.class), eq(name), eq("30:00"), eq("12.40"));
		verify(printer).printTotal("19.90");
	}

}
