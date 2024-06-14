package com.doar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mdad.database.DBConnection;
import com.mdad.models.User;

public class UserTest {

	private static DBConnection dbConnection;

	@BeforeAll
	public static void setup() {
		dbConnection = DBConnection.getInstance();

		try {
			dbConnection.createModel();
		} catch (Exception e) {

		}
	}

	@AfterAll
	public static void after() {
		dbConnection.disconnect();
	}

	@BeforeEach
	public void beforeEach() {
		dbConnection.deleteAllBlocks();
		dbConnection.deleteAllRelations();
		dbConnection.deleteAllUsers();
	}

	@AfterEach
	public void afterEach() {
		dbConnection.deleteAllUsers();
	}

	@Test
	public void test_createUser() {
		User user1 = new User("user1", "mail1", "pass1");

		boolean created = dbConnection.createUser(user1);
		assertTrue(created, "ERROR: User not created corectly");

		User user2 = dbConnection.getUser("user1");
		assertNotNull(user2, "ERROR: User loaded is null");

		assertEquals(user1.getName(), user2.getName());
	}

	@Test
	public void test_deleteUser() {
		User us = new User("userToDelete", "mail", "pass");

		boolean created = dbConnection.createUser(us);
		assertTrue(created, "ERROR: User not created corectly");

		dbConnection.deleteUser("ToDelete");
		User tmp1 = dbConnection.getUser(us.getName());
		assertNotNull(tmp1, "Se ha borrado un elemento cuyo nombre no coincide");

		dbConnection.deleteUser(us.getName());
		User tmp2 = dbConnection.getUser(us.getName());
		assertNull(tmp2, "No se ha borrado un elemento cuyo nombre coincide");
	}

	@Test
	public void test_searchByName() {
		dbConnection.createUser(new User("searchByName1", "mail", "pass"));
		dbConnection.createUser(new User("searchByName2", "mail", "pass"));
		dbConnection.createUser(new User("searchByName3", "mail", "pass"));
		dbConnection.createUser(new User("searchByName4", "mail", "pass"));
		dbConnection.createUser(new User("searchByName5", "mail", "pass"));

		List<User> users = dbConnection.searchByName("searchByName1", "searchByName");
		assertEquals(4, users.size());
	}

	@Test
	public void test_sendFriendshipRequest() {
		dbConnection.createUser(new User("u1", "u1", "pass"));
		dbConnection.createUser(new User("u2", "u2", "pass"));

		dbConnection.sendFriendRequest("u1", "u2");

		assertEquals(dbConnection.getFriendRequests("u2").size(), 1);
		assertEquals(dbConnection.getFriends("u1").size(), 0);
		assertEquals(dbConnection.getFriends("u2").size(), 0);
	}

	@Test
	public void test_acceptFriendshipRequest() {
		dbConnection.createUser(new User("u1", "u1", "pass"));
		dbConnection.createUser(new User("u2", "u2", "pass"));

		dbConnection.sendFriendRequest("u1", "u2");
		dbConnection.acceptFriendRequest("u2", "u1");

		assertEquals(dbConnection.getFriendRequests("u2").size(), 0);
		assertEquals(dbConnection.getFriends("u1").size(), 1);
		assertEquals(dbConnection.getFriends("u2").size(), 1);
	}

	@Test
	public void test_blockUser() {
		dbConnection.createUser(new User("u1", "u1", "pass"));
		dbConnection.createUser(new User("u2", "u2", "pass"));
		dbConnection.createUser(new User("u3", "u2", "pass"));

		dbConnection.blockUser("u1", "u2");

		assertEquals(0, dbConnection.searchByName("u1", "u2").size());
		assertEquals(1, dbConnection.searchByName("u1", "u").size());
	}
}
