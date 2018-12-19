package com.eadvocate.rest.controller;

import com.eadvocate.rest.dto.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static com.eadvocate.util.Constants.AUTHORIZATION_HEADER;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityIT {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // @WithMockUser(value = "at@t.com")
    @Test
    public void notLoggedUser_UnauthorizedResponse() throws Exception {
        mvc
                .perform(get("/api/size"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoggedUser_OK_Response() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail("t@t.com");
        loginUser.setPassword("pass");

        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        mvc
                .perform(get("/api/size")
                        .header(AUTHORIZATION_HEADER, content))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturn_OK_afterSuccessfulLogin() throws Exception {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail("t@t.com");
        loginUser.setPassword("pass");

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/login").contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk());

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