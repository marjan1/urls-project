package com.eadvocate.rest.controller;

import com.eadvocate.persistence.repo.RoleRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.rest.dto.*;
import com.eadvocate.util.ConversionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static com.eadvocate.util.Constants.AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class AdvocateCompanyControllerIT {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ConversionUtil conversionUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StatusRepository statusRepository;

    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();


    }

    @Test
    public void shouldAddNewAdvocateCompanySuccessfully() throws Exception {

        String authTokenContent = registrateAndLoginUser(1);

        StatusDto status = conversionUtil.convertObjectTo(statusRepository.getByName("Active"), StatusDto.class);
        AdvocateCompanyDto advocateCompanyDto = AdvocateCompanyDto.builder()
                .name("ACompany")
                .edbs("edbs")
                .embs("embs")
                .email("admin@company.com")
                .address("1Street 34N")
                .status(status)
                .phone("12345")
                .build();

        MvcResult addedCompany = mvc.perform(post("/api/advocatecompany/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(advocateCompanyDto)))
                .andExpect(status().isOk()).andReturn();

        String savedCompanyAsString = addedCompany.getResponse().getContentAsString();

        assertNotNull(savedCompanyAsString);

        AdvocateCompanyDto savedCompany = objectMapper.readValue(savedCompanyAsString, AdvocateCompanyDto.class);

        assertNotNull(savedCompany);

        assertEquals(advocateCompanyDto.getEmail(), savedCompany.getEmail());
        assertEquals(advocateCompanyDto.getEdbs(), savedCompany.getEdbs());
        assertEquals(advocateCompanyDto.getPhone(), savedCompany.getPhone());
        assertEquals(advocateCompanyDto.getEmbs(), savedCompany.getEmbs());
        assertEquals(advocateCompanyDto.getName(), savedCompany.getName());
        assertEquals(advocateCompanyDto.getAddress(), savedCompany.getAddress());
        assertEquals(advocateCompanyDto.getStatus().getId(), savedCompany.getStatus().getId());

    }


    @Test
    public void shouldAddNewAdvocateCompanyAndDeactivateAndActivateSuccessfully() throws Exception {

        String authTokenContent = registrateAndLoginUser(2);

        StatusDto activeStatus = conversionUtil.convertObjectTo(statusRepository.getByName("Active"), StatusDto.class);
        AdvocateCompanyDto advocateCompanyDto = AdvocateCompanyDto.builder()
                .name("ACompany")
                .edbs("edbs")
                .embs("embs")
                .email("admin@company.com")
                .address("1Street 34N")
                .status(activeStatus)
                .phone("12345")
                .build();

        MvcResult addedCompany = mvc.perform(post("/api/advocatecompany/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(advocateCompanyDto)))
                .andExpect(status().isOk()).andReturn();

        String savedCompanyAsString = addedCompany.getResponse().getContentAsString();

        assertNotNull(savedCompanyAsString);

        AdvocateCompanyDto savedCompany = objectMapper.readValue(savedCompanyAsString, AdvocateCompanyDto.class);

        assertNotNull(savedCompany);


        MvcResult deactivationResult = mvc.perform(post("/api/advocatecompany/deactivate")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(savedCompany)))
                .andExpect(status().isOk()).andReturn();


        String deactivationResultAsString = deactivationResult.getResponse().getContentAsString();

        assertNotNull(deactivationResultAsString);

        AdvocateCompanyDto deactivatedCompany = objectMapper.readValue(deactivationResultAsString, AdvocateCompanyDto.class);

        assertNotNull(deactivatedCompany);
        StatusDto deletedStatus = conversionUtil.convertObjectTo(statusRepository.getByName("Deleted"), StatusDto.class);
        assertEquals(deletedStatus, deactivatedCompany.getStatus());

        MvcResult activationResult = mvc.perform(post("/api/advocatecompany/activate")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(deactivatedCompany)))
                .andExpect(status().isOk()).andReturn();


        String activationResultAsString = activationResult.getResponse().getContentAsString();

        assertNotNull(activationResultAsString);

        AdvocateCompanyDto activeCompany = objectMapper.readValue(activationResultAsString, AdvocateCompanyDto.class);

        assertNotNull(activeCompany);

        assertEquals(activeStatus, activeCompany.getStatus());

    }

    private String registrateAndLoginUser(int temp) throws Exception {
        RoleDto portalAdminRole = conversionUtil.convertObjectTo(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"), RoleDto.class);

        StatusDto statusDto = conversionUtil.convertObjectTo(statusRepository.getByName("Active"), StatusDto.class);

        UserDto userDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email(temp + "c7@company1.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .build();

        MvcResult result = mvc.perform(post("/api/signup").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(result);
        UserDto resultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        LoginUser loginUser = LoginUser.builder().email(resultDto.getEmail()).password(userDto.getPassword()).build();

        MvcResult loginResult = mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk()).andReturn();

        return loginResult.getResponse().getContentAsString();
    }


}