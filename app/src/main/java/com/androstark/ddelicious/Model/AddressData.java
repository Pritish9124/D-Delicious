package com.androstark.ddelicious.Model;

/**
 * Created by SALIM on 4/29/2018.
 */

public class AddressData
{
    public String id;
    public String customername;
    public String customersreetaddress;
    public String shippping_adress2;
    public String customercity;
    public String customerstate;
    public String customerpostalcode;
    public String shippping_phone;
    public String shippping_country;



    public AddressData() {

    }

    public AddressData(String id, String customername, String customersreetaddress, String shippping_adress2, String customercity, String customerstate, String customerpostalcode, String shippping_phone, String shippping_country) {
        this.id = id;
        this.customername = customername;
        this.customersreetaddress = customersreetaddress;
        this.shippping_adress2 = shippping_adress2;
        this.customercity = customercity;
        this.customerstate = customerstate;
        this.customerpostalcode = customerpostalcode;
        this.shippping_phone = shippping_phone;
        this.shippping_country = shippping_country;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomersreetaddress() {
        return customersreetaddress;
    }

    public void setCustomersreetaddress(String customersreetaddress) {
        this.customersreetaddress = customersreetaddress;
    }

    public String getCustomercity() {
        return customercity;
    }

    public void setCustomercity(String customercity) {
        this.customercity = customercity;
    }

    public String getCustomerstate() {
        return customerstate;
    }

    public void setCustomerstate(String customerstate) {
        this.customerstate = customerstate;
    }

    public String getCustomerpostalcode() {
        return customerpostalcode;
    }

    public void setCustomerpostalcode(String customerpostalcode) {
        this.customerpostalcode = customerpostalcode;
    }

    public String getShippping_adress2() {
        return shippping_adress2;
    }

    public void setShippping_adress2(String shippping_adress2) {
        this.shippping_adress2 = shippping_adress2;
    }

    public String getShippping_country() {
        return shippping_country;
    }

    public void setShippping_country(String shippping_country) {
        this.shippping_country = shippping_country;
    }

    public String getShippping_phone() {
        return shippping_phone;
    }

    public void setShippping_phone(String shippping_phone) {
        this.shippping_phone = shippping_phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
