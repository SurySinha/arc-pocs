package com.arcmind.contact.config;
import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.util.DefaultScopes;

import com.arcmind.contact.controller.ContactController;
import com.arcmind.contact.model.ContactRepository;
import com.arcmind.contact.model.GroupRepository;
import com.arcmind.contact.model.TagRepository;
import com.arcmind.contact.validators.ContactValidators;

@Configuration
public class ContactConfig {
    
    @Bean
    public ContactValidators contactValidators() {
        return new ContactValidators();
    }

    @Bean(scope=DefaultScopes.SESSION)  
    public ContactController contactController() {
        ContactController contactController = new ContactController();
        contactController.setContactRepository(contactRepository());
        contactController.setTagRepository(tagRepository());
        contactController.setGroupRepository(groupRepository());
        return contactController;
    }
    
    @Bean
    public ContactRepository contactRepository() {
        return new ContactRepository();
    }

    @Bean
    public GroupRepository groupRepository() {
        return new GroupRepository();
    }

    @Bean
    public TagRepository tagRepository() {
        return new TagRepository();
    }
    
}
