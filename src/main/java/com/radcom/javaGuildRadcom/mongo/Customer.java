package com.radcom.javaGuildRadcom.mongo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Customer {
    @Id
    private String id;

    private String email;

    private Integer rating;

    public Customer() {
    }

    public Customer(String email, Integer rating) {
        this.email = email;
        this.rating = rating;
    }

    public Customer(String id, String email, Integer rating) {
        this.id = id;
        this.email = email;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(email, customer.email) && Objects.equals(rating, customer.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, rating);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", rating=" + rating +
                '}';
    }
}
