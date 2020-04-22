//package com.github.siberianintegrationsystems.restApp.controller;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.junit.Assert.*;
//
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestDrivenApplication.class})
//@WebAppConfiguration
//public class ProductDiscountControllerTest {
//
//    @Autowired
//    WebApplicationContext wac;
//    MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.
//                webAppContextSetup(this.wac)
//                .dispatchOptions(true).build();
//    }
//
//    @Test
//    public void getDiscount() throws Exception {
//        mockMvc.perform( MockMvcRequestBuilders
//                .get("/api/discount/1?clientId=2")
//                .accept(MediaType.ALL))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
