package com.myContactBookJson.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myContactBookJson.domain.Contact;
import com.myContactBookJson.repository.MyMongoTemplate;
import com.myContactBookJson.services.ContactOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class contactBookController {
    ContactOperations contactOperations=new ContactOperations(MyMongoTemplate.getMongoConnection());
    @RequestMapping(method = RequestMethod.GET,value = "/message-list")
    @ResponseBody
    public String getMessageList(){
        String message="";
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
        stringStringHashMap.put("welcome","this should be rendered using json");
        return "{  \n" +
                "   \"message list\":{  \n" +
                "      \"welcome\":\"good to see you in ember world!\",\n" +
                "      \"wish\":\"all the best in ember world!\"\n" +
                "   }\n" +
                "}";
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getAllContacts() throws JsonProcessingException {
        List<Contact> allContacts = contactOperations.getAllContacts();
        List<String> jsonContacts=new ArrayList<String>();
        ObjectMapper objectMapper=new ObjectMapper();
        for (Contact contact:allContacts){
            jsonContacts.add(objectMapper.writeValueAsString(contact));
        }
        String contacts= String.valueOf(jsonContacts);
        return contacts;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    @ResponseBody
    public String getContact(@PathVariable String id) throws JsonProcessingException {
        Contact contact = contactOperations.getContactById(id);
        ObjectMapper objectMapper=new ObjectMapper();
        String jsonContact = objectMapper.writeValueAsString(contact);
        return "{ \"contact\": "+jsonContact+"}";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public String updateContact(@PathVariable String id, @RequestBody String contactInfo) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        Contact contact = objectMapper.readValue(contactInfo, Contact.class);
        contact.setId(id);
        contactOperations.updateContact(contact);
        Contact contactByName = contactOperations.getContactById(id);
        return objectMapper.writeValueAsString(contactByName);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createContact( @RequestBody String contactInfo) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        Contact contact = objectMapper.readValue(contactInfo, Contact.class);
        contactOperations.addContact(contact);
        return "redirect:contacts";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public String deleteContact(@PathVariable String id){
//        Contact contact = contactOperations.getContactById(id);
        contactOperations.deleteContact(id);
        return "Contact deleted";
    }
}
