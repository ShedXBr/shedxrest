package com.shedx.shedxrest.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Catalog")
public class Catalog {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String descprod;
    private double price;
    private Long companyId;
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescProduct(){
        return descprod;
    }
    public void setDescProduct(String descprod){
        this.descprod = descprod;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public Long getCompanyId(){
        return companyId;
    }

    public void setCompanyId(Long companyId){ this.companyId = companyId; }
}
//example of a catalog of any company
