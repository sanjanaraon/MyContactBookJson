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
        List<Contact> contacts = mongoOperations.findAll(Contact.class);
        return contacts;
    }

    public void addContact(Contact contact) {
        mongoOperations.insert(contact);
    }

    public Contact updateContact(Contact contact) throws UnknownHostException {

        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getId())), Update.update("name", contact.getName()),Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getId())), Update.update("mobileNumber", contact.getMobileNumber()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getId())), Update.update("street1", contact.getStreet1()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getId())), Update.update("street2", contact.getStreet2()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getId())), Update.update("city", contact.getCity()), Contact.class);
        mongoOperations.findAndModify(Query.query(Criteria.where("_id").is(contact.getId())), Update.update("emailId", contact.getEmailId()),Contact.class);
        mongoOperations.save(contact);
        return getContactById(contact.getId());
    }

//    public Contact getContactByEmailId(String emailId) {
//            List<Contact> contacts = mongoOperations.find(Query.query(Criteria.where("_id").is(emailId)), Contact.class);
//        if(contacts.size()==0) {
//            return null;
//        }
//            return contacts.get(0);
//    }

    public void deleteContact(String id) {
        Contact contact = getContactById(id);
        mongoOperations.count(Query.query(Criteria.where("_id").is(id)), Contact.class);
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

    public Contact getContactById(String id) {
        return mongoOperations.findOne(Query.query(Criteria.where("_id").is(id)),Contact.class);
    }
}
