package com.organization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class OrganizationTest {

	@Test()
	void test_getInstance() {
		Organization organization = Organization.getInstance();
		assertNotNull(organization);
	}

	@Test()
	void test_sameInstance() {
		assertEquals(Organization.getInstance(), Organization.getInstance());
	}

}
