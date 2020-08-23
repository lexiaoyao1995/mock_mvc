package com.lexiaoyao.mocktest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexiaoyao.mocktest.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Slf4j
class MocktestApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    //在junit5内用BeforeEach代替Before
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGet() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/test/person/1")
                //设置以json方式传输
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //将MvcResult中的response转为对象
        Person person = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Person.class);
        Assert.assertNotNull(person);
        Assert.assertEquals(person.getId(), Integer.valueOf(1));
    }

    @Test
    public void testPost() throws Exception {
        Person person = new Person(10, "name", 2);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/test/person")
                .content(new ObjectMapper().writeValueAsString(person)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Person resultPerson = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Person.class);
        Assert.assertNotNull(person);
        Assert.assertEquals(person.getId(), resultPerson.getId());
    }


}
