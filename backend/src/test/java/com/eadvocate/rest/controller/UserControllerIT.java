package com.eadvocate.rest.controller;

import com.eadvocate.persistence.repo.RoleRepository;
import com.eadvocate.persistence.repo.StatusRepository;
import com.eadvocate.rest.dto.LoginUser;
import com.eadvocate.rest.dto.RoleDto;
import com.eadvocate.rest.dto.StatusDto;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.util.ConversionUtil;
import com.eadvocate.util.PageImplBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static com.eadvocate.util.Constants.AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class UserControllerIT {

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

    private String authTokenContent;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();


    }

//    @BeforeAll
//    public   void beforeClass()throws Exception{
//        authTokenContent = registrateAndLoginUser();
//    }


    @Test
    public void shouldAddNewUserSuccessfully() throws Exception {

        authTokenContent = registrateAndLoginUser(1);

        RoleDto portalAdminRoleForNewUser = conversionUtil.convertObjectTo(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"), RoleDto.class);


        UserDto newUserDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("novUser2@companyy.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .build();

        MvcResult addedUser = mvc.perform(post("/api/user/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isOk()).andReturn();

        String savedUserAsString = addedUser.getResponse().getContentAsString();

        assertNotNull(savedUserAsString);

        UserDto savedUser = objectMapper.readValue(savedUserAsString, UserDto.class);

        assertNotNull(savedUser);

        assertEquals(newUserDto.getEmail(), savedUser.getEmail());
        assertEquals(newUserDto.getAccountGroupLevel(), savedUser.getAccountGroupLevel());
        assertEquals(newUserDto.getPhone(), savedUser.getPhone());
        assertEquals(newUserDto.getStatusDto(), savedUser.getStatusDto());

    }

    @Test
    public void shouldCheckEmailExistence() throws Exception {

        authTokenContent = registrateAndLoginUser(2);
        MvcResult result = mvc.perform(post("/api/user/emailcheck")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .content("2c7@company1.com"))
                .andExpect(status().isOk()).andReturn();

        boolean emailExist = Boolean.parseBoolean(result.getResponse().getContentAsString());

        assertTrue(emailExist);

        MvcResult result2 = mvc.perform(post("/api/user/emailcheck")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .content("c7@company5.com"))
                .andExpect(status().isOk()).andReturn();

        emailExist = Boolean.parseBoolean(result2.getResponse().getContentAsString());

        assertFalse(emailExist);

    }

    @Test
    public void shouldDeactivateAndActivateUser() throws Exception {
        authTokenContent = registrateAndLoginUser(3);

        RoleDto roleAdvocate = conversionUtil.convertObjectTo(
                roleRepository.getByName("ROLE_ADVOCATE"), RoleDto.class);

        UserDto newUserDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("novUser@companyy.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .build();

        MvcResult addedUser = mvc.perform(post("/api/user/add")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isOk()).andReturn();

        MvcResult result = mvc.perform(post("/api/user/deactivate")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .content("novUser@companyy.com"))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(result);
        UserDto userDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertEquals("Deleted", userDto.getStatusDto().getName());

        MvcResult result2 = mvc.perform(post("/api/user/activate")
                .header(AUTHORIZATION_HEADER, authTokenContent)
                .content("novUser@companyy.com"))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(result);
        userDto = objectMapper.readValue(result2.getResponse().getContentAsString(), UserDto.class);

        assertEquals("Active", userDto.getStatusDto().getName());

    }


    @Test
    @Ignore
    public void shouldAddNewUserSSuccessfullyAndGetPage() throws Exception {

        RoleDto roleAdvocate = conversionUtil.convertObjectTo(
                roleRepository.getByName("ROLE_ADVOCATE"), RoleDto.class);
        int sizeValue = 5;

        for (int i = 2; i < 20; i++) {

            UserDto newUserDto = UserDto.builder()
                    .name("name" + i)
                    .surname("surname" + i)
                    .email(i + "novUser@companyy.com")
                    .password("passMarjan1!" + i)
                    .matchingPassword("passMarjan1!" + i)
                    .phone("phoneN" + i)
                    .build();

            mvc.perform(post("/api/user/add")
                    .header(AUTHORIZATION_HEADER, authTokenContent)
                    .contentType(APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(newUserDto)))
                    .andExpect(status().isOk());
        }

        MvcResult result = mvc.perform(get("/api/user/page?pageNumber=2&size=" + sizeValue)
                .header(AUTHORIZATION_HEADER, authTokenContent))
                .andExpect(status().isOk()).andReturn();

        String usersAsString = result.getResponse().getContentAsString();

        assertNotNull(usersAsString);

        Page<UserDto> userPage = ((PageImplBean<UserDto>) new ObjectMapper()
                .readValue(usersAsString, new TypeReference<PageImplBean<UserDto>>() {
                })).pageImpl();
        //  PageImplBean<UserDto> readValues = new ObjectMapper().readValue(usersAsString, new TypeReference<PageImplBean<UserDto>>() { });

        usersAsString = usersAsString.split(":", 2)[1].trim();
        usersAsString = usersAsString.substring(0, usersAsString.length() - 1);

//        CollectionType javaType = objectMapper.getTypeFactory()
//                .constructCollectionType(Page.class, UserDto.class);
//        Page<UserDto> returnedUsers = objectMapper.readValue(usersAsString, javaType);

//        UserDto[] returnedUsers = objectMapper.readValue(usersAsString, UserDto[].class);
//
//        assertNotNull(returnedUsers);

        //  assertEquals(sizeValue,returnedUsers.size());

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