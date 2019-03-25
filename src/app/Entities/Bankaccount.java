package app.Entities;

import app.annotations.Column;

import java.time.LocalDate;

public class Bankaccount {
    @Column
    private long id;
    @Column
    private String accountnumber;
    @Column
    private double amount;
    @Column
    private String type;

    public long getId(){return id;}
    public String getAccountnumber() { return accountnumber; }
    public double getAmount() { return amount; }
    public String getType() { return type; }


}
