package app.Entities;

import app.annotations.Column;

import java.util.List;

public class Bankaccount {
    @Column
    private long id;
    @Column
    private String accountnumber;
    @Column
    private String accountname;
    @Column
    private double amount;
    @Column
    private String type;

    public long getId(){return id;}
    public String getAccountnumber() { return accountnumber; }
    public String getAccountname(){return accountname;}
    public double getAmount() { return amount; }
    public String getType() { return type; }


}
