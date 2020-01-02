package com.chandler.rest.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.chandler.rest.domain.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Slf4j
public class RestApiControllerTests {
	@Autowired
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		assertNotNull(mvc);
	}
	
	@After
	public void tearDown() {
//		deleteAll();
	}
	
	/**
	 * 객체 저장 테스트
	 */
	@Test
	public void addBookTest() {
		Book book = Book.builder()
								.name("강나나")
								.title("아아아타이틀")
								.price(2500)
								.description("아아아타이틀입니다.")
								.build();
		controllerTest(HttpMethod.POST, "/api/books", null, book, null, status().isCreated());
	}
		
	/**
	 * 객체 조회 테스트
	 */
	@Test
	public void getBookListTest() {
		String res = controllerTest(HttpMethod.GET, "/api/books", null, null, null, status().isOk());
		
		System.out.println("결과::" + res);
	}
	
	@Test
	public void getDetailTest() {
		Long id = 1L;
		String res = controllerTest(HttpMethod.GET, "/api/books" + "/" + id, null, null, null, status().isOk());
		
		System.out.println("결과::" + res);
	}
	
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-	
	public static final String AUTH_HEADER_NAME 	= "Authorization";
	public static final String AUTH_SCHEMA 			= "Bearer";
	/**
	 * controller 테스트..
	 * @param method
	 * @param url
	 * @param params
	 * @param body
	 * @param tokenString
	 * @param resultMatcher
	 * @return
	 */
	private String controllerTest(HttpMethod method, String url, Map<String, String> params, Object body, String tokenString, ResultMatcher resultMatcher) {
		return controllerTest( method, url, params, body, tokenString, resultMatcher, null );
	}
	private String controllerTest(HttpMethod method, String url, Map<String, String> params, Object body, String tokenString, ResultMatcher resultMatcher, MediaType type) {
		MockHttpServletRequestBuilder requestBuilder = null;
		
		if (HttpMethod.GET.equals(method)) {
			requestBuilder = get(url);
		} else if (HttpMethod.POST.equals(method)) {
			requestBuilder = post(url);
		} else if (HttpMethod.PUT.equals(method)) {
			requestBuilder = put(url);
		} else if (HttpMethod.DELETE.equals(method)) {
			requestBuilder = delete(url);
		}
		
		if (type == null ) {		
			requestBuilder.accept(MediaType.APPLICATION_JSON);
			requestBuilder.contentType(MediaType.APPLICATION_JSON_UTF8);		
		} else {
			requestBuilder.accept( type );
			requestBuilder.contentType( type );
		}
		
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
			    requestBuilder.param(entry.getKey(), entry.getValue());
			}
		}
		
		if (body != null) {
			try {
				requestBuilder.content(new ObjectMapper().writeValueAsString(body));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		if (tokenString != null) {
			requestBuilder.header(AUTH_HEADER_NAME, AUTH_SCHEMA + " " + tokenString);
		}
		
		MvcResult result = null;
		try {
			result = mvc.perform(requestBuilder)
											.andExpect(resultMatcher)
											.andDo(print())
											.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if ( result != null ) {
			try {
				
				return result.getResponse().getContentAsString();
				
			} catch (UnsupportedEncodingException e) {			
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private byte[] fileToByteArray() {
		File file = new File("D://ffdshow_rev4532_20140717_clsid.exe");
	     
	    if(!file.exists()) {
	      System.out.println("������ ���������ʽ��ϴ�.");
	      return null;
	    }
	    
	    byte[] byteFile = null;
	    try {
//			byteFile = FileUtils.readFileToByteArray(file);
			byteFile = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return byteFile;
	}

}
