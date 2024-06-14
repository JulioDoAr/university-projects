package com.mdad.database;

import java.util.ArrayList;
import java.util.List;

import com.mdad.models.User;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.exception.ODatabaseException;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

public class DBConnection {
	private OrientDB orientDB;
	private ODatabaseSession dbSession;

	private static DBConnection instance;

	private DBConnection() {
			connect();
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	public ODatabaseSession connect() {
		String serverUrl = "remote:localhost";
		String dbName = "friendship";
		String username = "root";
		String password = "root";

		try{
			orientDB = new OrientDB(serverUrl, OrientDBConfig.defaultConfig());
			dbSession = orientDB.open(dbName, username, password);
		} catch(ODatabaseException e){
			System.out.println("ERROR: There was an error attempting to connect to the database! Exiting...");
			System.exit(1);
		}
		return dbSession;

	}

	public void createModel() {
		dbSession.createVertexClass("User");
		dbSession.createEdgeClass("Relationship");
		dbSession.createEdgeClass("Block");
	}

	public void disconnect() {
		dbSession.commit();
		dbSession.close();
		orientDB.close();
	}

	public void rollback() {
		dbSession.rollback();
	}

	public boolean deleteAllBlocks() {
		try {
			String query = "DELETE edge Block";
			dbSession.command(query).close();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
			return false; // La operación falló
		}
	}

	public boolean deleteAllRelations() {
		try {
			String query = "DELETE EDGE Relationship";
			dbSession.command(query).close();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
			return false; // La operación falló
		}
	}

	public boolean deleteAllUsers() {
		try {
			String query = "delete vertex User";
			dbSession.command(query).close();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
			return false; // La operación falló
		}
	}

	public boolean createUser(User user) {
		try {
			OVertex userVertex = dbSession.newVertex("User");
			userVertex.setProperty("name", user.getName());
			userVertex.setProperty("email", user.getEmail());
			userVertex.setProperty("password", user.getPassword());
			userVertex.save();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
			return false; // La operación falló
		}
	}

	public boolean deleteUser(String name) {
		String deleteUserQuery = "DELETE VERTEX User WHERE name = '" + name + "'";
		dbSession.command(deleteUserQuery);
		return true;
	}

	public User getUser(String name) {
		try {
			String query = "SELECT FROM User WHERE name = ?";
			OResultSet resultSet = dbSession.query(query, name);
			if (resultSet.hasNext()) {
				return rsToUser(resultSet);
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<User> searchByName(String username, String name) {
		List<User> users = new ArrayList<>();
		try {
			// Select all users that contain the given name and does not have a block edge
			String query = "SELECT FROM User WHERE name LIKE ? and @rid not in (SELECT out('Block') FROM User WHERE name = ?) and not name = ?";
			OResultSet resultSet = dbSession.query(query, "%" + name + "%", username, username);
			while (resultSet.hasNext()) {
				users.add(rsToUser(resultSet));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<User> getFriends(String name) {
		List<User> friends = new ArrayList<>();
		try {
			String query = "SELECT * FROM User where @rid in (SELECT out('Relationship') FROM User WHERE name = ?) AND @rid IN (SELECT in('Relationship') FROM User WHERE name = ?)";
			OResultSet resultSet = dbSession.query(query, name, name);
			while (resultSet.hasNext()) {
				friends.add(rsToUser(resultSet));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friends;
	}

	public List<User> getFriendRequests(String name) {
		List<User> friendRequests = new ArrayList<>();
		try {
			String query = "SELECT * FROM User where @rid not in (SELECT out('Relationship') FROM User WHERE name = ?) AND @rid IN (SELECT in('Relationship') FROM User WHERE name = ?)";
			OResultSet resultSet = dbSession.query(query, name, name);
			while (resultSet.hasNext()) {
				friendRequests.add(rsToUser(resultSet));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friendRequests;
	}

	public boolean sendFriendRequest(String senderName, String receiverName) {
		try {
			String createEdgeQuery = "CREATE EDGE Relationship FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(createEdgeQuery, senderName, receiverName).close();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // La operación falló
	}

	public boolean acceptFriendRequest(String senderName, String receiverName) {
		try {
			String createEdgeQuery = "CREATE EDGE Relationship FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(createEdgeQuery, senderName, receiverName).close();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // La operación falló
	}

	public boolean rejectFriendRequest(String senderName, String receiverName) {
		try {
			String deleteEdgeQuery = "DELETE EDGE Relationship FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(deleteEdgeQuery, receiverName, senderName).close();
			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // La operación falló
	}

	public boolean removeFriend(String senderName, String receiverName) {
		try {
			String deleteEdgeQuery = "DELETE EDGE Relationship FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(deleteEdgeQuery, senderName, receiverName).close();

			deleteEdgeQuery = "DELETE EDGE Relationship FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(deleteEdgeQuery, receiverName, senderName).close();

			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // La operación falló
	}

	private User rsToUser(OResultSet resultSet) {
		OElement friendElement = resultSet.next().getElement().get();
		String friendName = friendElement.getProperty("name");
		String friendEmail = friendElement.getProperty("email");
		String friendPassword = friendElement.getProperty("password");
		return new User(friendName, friendEmail, friendPassword);
	}

	public boolean blockUser(String name, String toBlock) {
		removeFriend(name, toBlock);
		rejectFriendRequest(name, toBlock);
		rejectFriendRequest(toBlock, name);
		try {
			String createEdgeQuery = "CREATE EDGE Block FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(createEdgeQuery, name, toBlock).close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean unblockUser(String name, String toUnblock){
		try {
			// We just remove one side of the relationship, the user has no control over others' blocking decisions
			String deleteEdgeQuery = "DELETE EDGE Block FROM (SELECT FROM User WHERE name = ?) TO (SELECT FROM User WHERE name = ?)";
			dbSession.command(deleteEdgeQuery, name, toUnblock).close();

			return true; // La operación fue exitosa
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false; // La operación falló
	}


	public List<User> getBlockedUsers(String name) {
		List<User> blockedUsers = new ArrayList<>();
		try {
			String query = "SELECT expand(out('Block')) FROM User WHERE name = ?";
			OResultSet resultSet = dbSession.query(query, name);
			while (resultSet.hasNext()) {
				blockedUsers.add(rsToUser(resultSet));
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blockedUsers;
	}

	public boolean checkUser(String username){
		try {
			// Search user
			String query = "SELECT FROM User WHERE name = ?";
			OResultSet resultSet = dbSession.query(query, username);

			resultSet.close();

			if (resultSet.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isBlocked(String name, String username){
		try {
			// Search user
			String query = "SELECT * FROM User where @rid IN (SELECT out('Block') FROM User WHERE name = ?) AND name = ?";
			OResultSet resultSet = dbSession.query(query, name, username);

			resultSet.close();

			if (resultSet.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isBlockedBy(String name, String username){
		try {
			// Search user
			String query = "SELECT * FROM User where @rid IN (SELECT in('Block') FROM User WHERE name = ?) AND name = ?";
			OResultSet resultSet = dbSession.query(query, name, username);

			resultSet.close();

			if (resultSet.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean theresBlocks(String name, String username){
		try {
			// Search user
			String query = "SELECT * FROM User where (@rid IN (SELECT in('Block') FROM User WHERE name = ?) OR @rid IN (SELECT out('Block') FROM User WHERE name = ?)) AND name = ?";
			OResultSet resultSet = dbSession.query(query, name, name, username);

			resultSet.close();

			if (resultSet.hasNext()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
