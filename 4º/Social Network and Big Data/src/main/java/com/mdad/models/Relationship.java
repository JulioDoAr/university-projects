package com.mdad.models;

public class Relationship {
    private String rid;
    private RelationshipType type;
    private User user1;
    private User user2;

    public Relationship() {
    }

    public Relationship(RelationshipType type, User user1, User user2) {
        this.type = type;
        this.user1 = user1;
        this.user2 = user2;
    }

    public RelationshipType getType() {
        return type;
    }

    public void setType(RelationshipType type) {
        this.type = type;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
