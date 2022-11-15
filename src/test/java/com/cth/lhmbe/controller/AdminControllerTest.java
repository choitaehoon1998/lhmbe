package com.cth.lhmbe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cth.lhmbe.dto.SignUpAdmin;
import com.cth.lhmbe.dto.StoreAdminDto;
import com.cth.lhmbe.service.MemberService;
import com.cth.lhmbe.service.StoreMemberService;
import com.cth.lhmbe.service.StoreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

	@MockBean
	private MemberService memberService;

	@MockBean
	private StoreService storeService;

	@MockBean
	private StoreMemberService storeMemberService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp(@Autowired WebApplicationContext applicationContext) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
			.alwaysDo(print())
			.build();
	}

	@Test
	@DisplayName("가게사장_회원가입_성공")
	@WithAnonymousUser
	void successCreateStoreAdmin() throws Exception {
		StoreAdminDto storeAdminDto = StoreAdminDto.builder()
			.adminName("userName")
			.adminEmail("email")
			.storeName("storeName")
			.storeNumber("storeNumber")
			.storeLocation("storeLocation")
			.build();

		given(storeMemberService.createStoreAdmin(any(SignUpAdmin.Request.class)))
			.willReturn(storeAdminDto);

		mockMvc.perform(post("/admin/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(
					new SignUpAdmin.Request("userName", "email",
						"password", "storeName",
						"storeNumber", "storeLocation")
			)).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.adminName").value("userName"))
			.andExpect(jsonPath("$.adminEmail").value("email"))
			.andExpect(jsonPath("$.storeName").value("storeName"))
			.andExpect(jsonPath("$.storeNumber").value("storeNumber"))
			.andExpect(jsonPath("$.storeLocation").value("storeLocation"));
	}

	@Test
	@DisplayName("가게사장_회원가입_실패")
	void failCreateStoreAdmin(){

	}

}