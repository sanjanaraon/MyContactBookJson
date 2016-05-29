package com.myContactBookJson.controllers;

import com.myContactBookJson.domain.Contact;
import com.myContactBookJson.repository.MyMongoTemplate;
import com.myContactBookJson.services.ContactOperations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
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

    @RequestMapping(method = RequestMethod.GET,value = "/getAllContacts")
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
}
