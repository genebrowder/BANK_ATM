package edu.umsl.final3.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by genebrowder on 3/20/16.
 */
@Entity
@Table(name="USER")
public class User implements Serializable {

    private static final long serialVersionUID = 336542947443965225L;
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="USER_ID")
    @Id
    private long id;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="CITY")
    private String city;

    @Column(name="STATE")
    private String state;

    @Column(name="ZIP_CODE")
    private String zipCode;

    @Column(name="PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user",fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    public User(){

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public List<Account> getAccount() {
        return this.accounts;
    }

    public void setAccount(List<Account> accounts) {
        this.accounts = accounts;
    }
}
