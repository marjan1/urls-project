package com.eadvocate.rest.controller;

import com.eadvocate.persistence.dao.RoleRepository;
import com.eadvocate.rest.dto.LoginUser;
import com.eadvocate.rest.dto.RoleDto;
import com.eadvocate.rest.dto.UserDto;
import com.eadvocate.util.ConversionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@Ignore
public class SecurityIT {

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

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void shouldAddNewUserLoginAndGetUsersWithSameRole() throws Exception {

        RoleDto portalAdminRole = conversionUtil.convertToDto(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"));

        UserDto userDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("m1@company1.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .roleDto(portalAdminRole)
                .build();

        MvcResult result = mvc.perform(post("/api/signup").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk()).andReturn();

        LoginUser loginUser = LoginUser.builder().email(userDto.getEmail()).password(userDto.getPassword()).build();

        MvcResult loginResult = mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk()).andReturn();

        String content = loginResult.getResponse().getContentAsString();
        mvc
                .perform(get("/api/portaladmins")
                        .header(AUTHORIZATION_HEADER, content))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldAddNewUserLoginAndGetUsersWithSamePriviliges() throws Exception {

        RoleDto portalAdminRole = conversionUtil.convertToDto(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"));

        UserDto userDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("m78@company1.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .roleDto(portalAdminRole)
                .build();

        MvcResult result = mvc.perform(post("/api/signup").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk()).andReturn();

        LoginUser loginUser = LoginUser.builder().email(userDto.getEmail()).password(userDto.getPassword()).build();

        MvcResult loginResult = mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk()).andReturn();

        String content = loginResult.getResponse().getContentAsString();
        mvc
                .perform(get("/api/companyadmins")
                        .header(AUTHORIZATION_HEADER, content))
                .andExpect(status().isForbidden());

    }

    @Test
    public void shouldAddNewUserLoginAndGetUnAutorzed() throws Exception {

        RoleDto portalAdminRole = conversionUtil.convertToDto(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"));

        UserDto userDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("m4@company1.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .roleDto(portalAdminRole)
                .build();

        MvcResult result = mvc.perform(post("/api/signup").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk()).andReturn();

        LoginUser loginUser = LoginUser.builder().email(userDto.getEmail()).password(userDto.getPassword()).build();

        MvcResult loginResult = mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk()).andReturn();

        String content = loginResult.getResponse().getContentAsString();
        mvc
                .perform(get("/api/advocates")
                        .header(AUTHORIZATION_HEADER, content))
                .andExpect(status().isForbidden());

    }


    @Test
    public void shouldAddNewUserSuccessfully() throws Exception {

        RoleDto portalAdminRole = conversionUtil.convertToDto(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"));

        UserDto userDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("u3@company1.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .roleDto(portalAdminRole)
                .build();

        MvcResult result = mvc.perform(post("/api/signup").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(result);

    }

    @Test
    public void shouldLoginAfterRegistrationSuccessfully() throws Exception {
        RoleDto portalAdminRole = conversionUtil.convertToDto(
                roleRepository.getByName("ROLE_PORTAL_ADMINISTRATOR"));

        UserDto userDto = UserDto.builder()
                .name("name1")
                .surname("surname1")
                .email("c7@company1.com")
                .password("passMarjan1!")
                .matchingPassword("passMarjan1!")
                .roleDto(portalAdminRole)
                .build();

        MvcResult result = mvc.perform(post("/api/signup").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(result);
        UserDto resultDto = objectMapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        LoginUser loginUser = LoginUser.builder().email(resultDto.getEmail()).password(userDto.getPassword()).build();

        testLoginUser(loginUser);
    }



    private void testLoginUser(LoginUser loginUser) throws Exception {

        MvcResult result = mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        mvc
                .perform(get("/api/size")
                        .header(AUTHORIZATION_HEADER, content))
                .andExpect(status().isOk());
    }



    // @WithMockUser(value = "at@t.com")
    @Test
    public void notLoggedUser_UnauthorizedResponse() throws Exception {
        mvc
                .perform(get("/api/size"))
                .andExpect(status().isUnauthorized());
    }




    @Test
    public void shouldReturn_Unauthorized_whenBadPasswordIsSend() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail("t@t.com");
        loginUser.setPassword("pass2");

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(loginUser)))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldReturn_Unauthorized_whenBadEmailIsSend() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail("2t@t.com");
        loginUser.setPassword("pass");

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(loginUser)))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void shouldReturn_Unauthorized_whenBadEmailAndPasswordIsSend() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail("2t@t.com");
        loginUser.setPassword("2pass");

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(loginUser)))
                .andExpect(status().isUnauthorized());

    }


}