package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.TestConfigurationUtil;
import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.service.interfaces.ProductsListService;
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
@WebMvcTest(ProductsListController.class)
class ProductsListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsListService mockProductsListService;

    @Test
    void testGetAllProductsList() throws Exception {
        // Setup
        // Configure ProductsListService.getAllProductsLists(...).
        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDto(1L);
        final ProductsListDto productsListDto2 = TestConfigurationUtil.createSingleProductsListDto(2L);

        final List<ProductsListDto> productsListDtos = List.of(productsListDto, productsListDto2);

        final String expectedStringResult = "[" +
                "{\"id\":1,\"name\":\"List 1\",\"description\":\"Description of list 1\"}," +
                "{\"id\":2,\"name\":\"List 2\",\"description\":\"Description of list 2\"}" +
                "]";
        when(mockProductsListService.getAllProductsLists()).thenReturn(productsListDtos);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(get("/v1/api/products-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testGetAllProductsList_ProductsListServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockProductsListService.getAllProductsLists()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/v1/api/products-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetProductsListById() throws Exception {
        // Setup
        final String expectedStringResult = "{\"id\":1,\"name\":\"List 1\",\"description\":\"Description of list 1\"}";
        // Configure ProductsListService.getProductsList(...).
        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDto(1L);
        when(mockProductsListService.getProductsList(1L)).thenReturn(productsListDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(get("/v1/api/products-list/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testCreateProductsList() throws Exception {
        // Setup
        // Configure ProductsListService.createProductsList(...).
        final String expectedStringResult = "{\"id\":1,\"name\":\"List 1\",\"description\":\"Description of list 1\"}";

        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDtoNoId(1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(productsListDto);

        productsListDto.setId(1L);
        when(mockProductsListService.createProductsList(any())).thenReturn(productsListDto);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(post("/v1/api/products-list")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testUpdateProductsList() throws Exception {
        // Setup
        // Configure ProductsListService.updateProductsList(...).
        final ProductsListDto productsListDtoReq = TestConfigurationUtil.createSingleProductsListDtoNoId(2L);
        final String expectedStringResult = "{\"id\":2,\"name\":\"List 2\",\"description\":\"Description of list 2\"}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(productsListDtoReq);

        final ProductsListDto productsListDtoNew = TestConfigurationUtil.createSingleProductsListDto(2L);
        when(mockProductsListService.updateProductsList(1L, productsListDtoReq)).thenReturn(productsListDtoNew);

        // Run the test
        final MockHttpServletResponse response = mockMvc
                .perform(put("/v1/api/products-list/{id}", 1L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedStringResult);
    }

    @Test
    void testDeleteProductsList() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/v1/api/products-list/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEqualTo("");
        verify(mockProductsListService).deleteProductsList(1L);
    }
}
