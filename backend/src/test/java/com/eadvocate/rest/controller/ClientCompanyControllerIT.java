package com.eadvocate.rest.controller;

import com.eadvocate.persistence.repo.RoleRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.rest.dto.*;
import com.eadvocate.util.CompanyType;
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
public class ClientCompanyControllerIT {

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
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();


    }

    @Test
    public void shouldAddNewClientCompanySuccessfully() throws Exception {

        String authTokenContent = registrateAndLoginUser(1);

        StatusDto activeStatus = conversionUtil.convertObjectTo(statusRepository.getByName("Active"), StatusDto.class);
        AdvocateCompanyDto advocateCompanyDto = AdvocateCompanyDto.builder()
                .name("ACompany")
                .edbs("edbs")
                .embs("embs")
                .email("admin@company.com")
                .address("1Street 34N")
                .status(activeStatus)
                .type(CompanyType.OFFICE)
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

        ClientCompanyDto clientCompanyDto = new ClientCompanyDto();
        clientCompanyDto.setName("clientCom");
        clientCompanyDto.setEmail("test@email.com");
        clientCompanyDto.setAccountNumber("aCCn324");
        clientCompanyDto.setAdvocateCompany(savedCompany);
        clientCompanyDto.setEmbs("emb");


        MvcResult addedClientCompany = mvc.perform(post("/api/clientcompany/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(clientCompanyDto)))
                .andExpect(status().isOk()).andReturn();

        String savedClientCompanyAsString = addedClientCompany.getResponse().getContentAsString();

        assertNotNull(savedClientCompanyAsString);

        ClientCompanyDto savedClientCompany = objectMapper.readValue(savedClientCompanyAsString, ClientCompanyDto.class);

        assertNotNull(savedClientCompany);

        assertEquals(clientCompanyDto.getEmail(), savedClientCompany.getEmail());
        assertEquals(clientCompanyDto.getEmbs(), savedClientCompany.getEmbs());
        assertEquals(clientCompanyDto.getName(), savedClientCompany.getName());
        assertEquals(clientCompanyDto.getAccountNumber(), savedClientCompany.getAccountNumber());
        assertEquals(clientCompanyDto.getAdvocateCompany().getId(), savedClientCompany.getAdvocateCompany().getId());


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
                .roleDto(portalAdminRole)
                .statusDto(statusDto)
                .accountGroupLevel((short) 1)
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