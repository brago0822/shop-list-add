package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.TestConfigurationUtil;
import com.brago.app.shoplistapp.dto.ShoppingListDto;
import com.brago.app.shoplistapp.service.interfaces.ShoppingListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingListController.class)
class ShoppingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingListService mockShoppingListService;

    @Test
    void testGetAllShoppingList() throws Exception {
        // Setup
        // Configure ShoppingListService.getAllShoppingLists(...).
        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDto(1L);
        final ShoppingListDto shoppingListDto2 = TestConfigurationUtil.createSingleShoppingListDto(2L);

        final List<ShoppingListDto> shoppingListDtos = List.of(shoppingListDto, shoppingListDto2);

        final String expectedStringResult = "[" +
                "{\"id\":1,\"name\":\"List 1\",\"description\":\"Description of list 1\"}," +
                "{\"id\":2,\"name\":\"List 2\",\"description\":\"Description of list 2\"}" +
                "]";
        when(mockShoppingListService.getAllShoppingLists()).thenReturn(shoppingListDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(get("/v1/api/shopping-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testGetAllShoppingList_ShoppingListServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockShoppingListService.getAllShoppingLists()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/api/shopping-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetShoppingListById() throws Exception {
        // Setup
        final String expectedStringResult = "{\"id\":1,\"name\":\"List 1\",\"description\":\"Description of list 1\"}";
        // Configure ShoppingListService.getShoppingList(...).
        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDto(1L);
        when(mockShoppingListService.getShoppingList(1L)).thenReturn(shoppingListDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(get("/v1/api/shopping-list/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testCreateShoppingList() throws Exception {
        // Setup
        // Configure ShoppingListService.createShoppingList(...).
        final String expectedStringResult = "{\"id\":1,\"name\":\"List 1\",\"description\":\"Description of list 1\"}";

        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDtoNoId(1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingListDto);

        shoppingListDto.setId(1L);
        when(mockShoppingListService.createShoppingList(any())).thenReturn(shoppingListDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(post("/v1/api/shopping-list")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testUpdateShoppingList() throws Exception {
        // Setup
        // Configure ShoppingListService.updateShoppingList(...).
        final ShoppingListDto shoppingListDtoReq = TestConfigurationUtil.createSingleShoppingListDtoNoId(2L);
        final String expectedStringResult = "{\"id\":2,\"name\":\"List 2\",\"description\":\"Description of list 2\"}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingListDtoReq);

        final ShoppingListDto shoppingListDtoNew = TestConfigurationUtil.createSingleShoppingListDto(2L);
        when(mockShoppingListService.updateShoppingList(1L, shoppingListDtoReq)).thenReturn(shoppingListDtoNew);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(put("/v1/api/shopping-list/{id}", 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testDeleteShoppingList() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/v1/api/shopping-list/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEmpty();
        verify(mockShoppingListService).deleteShoppingList(1L);
    }
}
