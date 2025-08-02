package com.vzv.shop.picture;

import com.vzv.shop.configuration.ApplicationConfig;
import com.vzv.shop.controller.rest.PictureController;
import com.vzv.shop.service.PictureService;
import mockeduser.WithMockAll;
import mockeduser.WithMockStaff;
import mockeduser.WithMockSysadmin;
import mockeduser.WithUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(PictureController.class)
@Import(ApplicationConfig.class)
class PictureControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private PictureService pictureService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithUser
    void testDeleteImageWithUser() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm/testId1"))

                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testDeleteImageWithMockStaff() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm/testId1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockSysadmin
    void testDeleteImageWithWithSysadmin() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm/testId1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockAll
    void testDeleteImageWithMockStaffWithAllRoles() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm/testId1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUser
    void testDeleteImageByNameWithUser() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm-by-name/TestName"))
                .andExpectAll(MockMvcResultMatchers.status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockStaff
    void testDeleteImageByNameWithMockStaff() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm-by-name/TestName"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockSysadmin
    void testDeleteImageByNameWithSysadmin() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm-by-name/TestName"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @WithMockAll
    void testDeleteImageByNameWithMockAll() throws Exception {
        when(pictureService.deletePicture(anyString())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/pictures/rm-by-name/TestName"))
                .andExpectAll(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}