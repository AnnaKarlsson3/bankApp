package app.Entities;


import app.annotations.Column;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    @Column
    private long id;
    @Column("fromaccountnumber")
    private String fromAccountNumber;
    @Column("account_id")
    private long toAccountId;
    @Column ("toaccountnumber")
    private String toAccountNumber;
    @Column
    private String message;
    @Column
    private double amount;

    @Column("time")
    private java.sql.Timestamp date;

    public String getDate() {
        return getDateAsZonedDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace('T', ' ');
    }

    public ZonedDateTime  getDateAsZonedDateTime(){
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of("Europe/Berlin"));
    }

    public String getMessage() { return message; }
    public double getAmount() { return amount; }
    public String getFromAccountNumber() {
        return fromAccountNumber;
    }
    public long getToAccountId() {
        return toAccountId;
    }
    public String getToAccountNumber(){
        return toAccountNumber;
    }

}
