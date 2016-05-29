package com.myContactBookJson.services;

import com.myContactBookJson.domain.Contact;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.net.UnknownHostException;
import java.util.List;

public class ContactOperations {
    MongoOperations mongoOperations;

    public ContactOperations(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    public List<Contact> getAllContacts() {
        //db.contactBook.contacts.find().pretty()
        List<Contact> contacts = mongoOperations.findAll(Contact.class, "contacts");
        return contacts;
    }

    public void addContact(Contact contact) {
        mongoOperations.insert(contact, "contacts");
    }

    public Contact updateContact(Contact contact) throws UnknownHostException {

        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getEmailId())), Update.update("name", contact.getName()),Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getEmailId())), Update.update("mobileNumber", contact.getMobileNumber()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getEmailId())), Update.update("street1", contact.getStreet1()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getEmailId())), Update.update("street2", contact.getStreet2()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getEmailId())), Update.update("city", contact.getCity()), Contact.class);
        mongoOperations.save(contact);
        return getContact(contact.getEmailId());
    }

    public Contact getContact(String emailId) {
            List<Contact> contacts = mongoOperations.find(Query.query(Criteria.where("_id").is(emailId)), Contact.class);
        if(contacts.size()==0) {
            return null;
        }
            return contacts.get(0);
    }

    public void deleteContact(String emailId) {
        Contact contact = getContact(emailId);
        mongoOperations.count(Query.query(Criteria.where("_id")), Contact.class);
        mongoOperations.remove(contact);
    }

    public List<Contact> searchContact(String searchContact) {
            List<Contact> contacts;
        contacts = mongoOperations.find(Query.query(Criteria.where("name").is(searchContact)), Contact.class);
        if(contacts.isEmpty()){
         contacts = mongoOperations.find(Query.query(Criteria.where("mobileNumber").is(searchContact)), Contact.class);
      }
        return contacts;
    }
}
