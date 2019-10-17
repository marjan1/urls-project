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
public class JudgeControllerIT {

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
    public void shouldAddNewJudgeSuccessfully() throws Exception {

        String authTokenContent = registrateAndLoginUser(1);

        CourtDto courtDto1 = CourtDto.builder()
                .address("add")
                .archiveEmail("test@email.com")
                .archivePhone("3234")
                .name("court")
                .build();

        MvcResult addCourtMvcResult = mvc.perform(post("/api/court/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(courtDto1)))
                .andExpect(status().isOk()).andReturn();

        String addCourtResultAsString = addCourtMvcResult.getResponse().getContentAsString();

        assertNotNull(addCourtResultAsString);

        CourtDto courtDto = objectMapper.readValue(addCourtResultAsString, CourtDto.class);

        JudgeDto requestDto = JudgeDto.builder()
                .court(courtDto)
                .name("court")
                .surname("surnamee")
                .judgeId("jId")
                .build();

        MvcResult mvcResult = mvc.perform(post("/api/judge/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk()).andReturn();

        String resultAsString = mvcResult.getResponse().getContentAsString();

        assertNotNull(resultAsString);

        JudgeDto resultObject = objectMapper.readValue(resultAsString, JudgeDto.class);

        assertNotNull(resultObject);
        assertNotNull(resultObject.getId());

        assertEquals(requestDto.getCourt().getId(), resultObject.getCourt().getId());
        assertEquals(requestDto.getJudgeId(), resultObject.getJudgeId());
        assertEquals(requestDto.getSurname(), resultObject.getSurname());
        assertEquals(requestDto.getName(), resultObject.getName());

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