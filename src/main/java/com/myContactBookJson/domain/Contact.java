package com.myContactBookJson.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.totallylazy.Maps;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

import static com.googlecode.totallylazy.Pair.pair;

@Document(collection = "contacts")
//@JsonIgnoreProperties({"partialObject", "_id"})
public class Contact {
    @JsonProperty
    @Indexed
    private String name;

    @JsonProperty
    @Indexed
    private int mobileNumber;

    @JsonProperty
    private String street1;

    @JsonProperty
    private String street2;

    @JsonProperty
    private String city;

    @JsonProperty
    @Id
    private String emailId;

    public Contact() {
    }



    public Contact(String name, int mobileNumber, String street1,String city, String emailId) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.street1 = street1;
        this.city = city;
        this.emailId = emailId;
    }

    public Contact(String name, int mobileNumber, String street1, String street2,String city, String emailId) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.emailId = emailId;
    }

    public Map<String, Object> toMap() {
        return Maps.<String, Object>
                map(pair("name", name),
                pair("mobileNumber", mobileNumber),
                pair("street1", street1),
                pair("street2", street2),
                pair("emailId", emailId)
        );
    }

    public String getName() {
        return name;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getEmailId() {
        return emailId;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
