package app.Entities;


import app.annotations.Column;

import javax.print.attribute.DateTimeSyntax;

public class User {
    @Column("id")
    private long id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;

    public long getId(){return id;}
    public String getFirstname(){return firstname;}
    public String getLastname(){return lastname;}

    @Override
    public String toString(){
        return String.format("User: { id: %d, username: %s }", id, username);
    }
}
