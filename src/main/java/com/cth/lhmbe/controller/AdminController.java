package com.cth.lhmbe.controller;

import com.cth.lhmbe.dto.AdminDto;
import com.cth.lhmbe.dto.SignUpAdmin;
import com.cth.lhmbe.dto.StoreDto;
import com.cth.lhmbe.service.MemberService;
import com.cth.lhmbe.service.StoreMemberService;
import com.cth.lhmbe.service.StoreService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
	private final StoreMemberService storeMemberService;

	public AdminController(StoreMemberService storeMemberService) {
		this.storeMemberService = storeMemberService;
	}

	@PostMapping(value= "/admin/signup")
	public SignUpAdmin.Response signUp (@RequestBody @Valid SignUpAdmin.Request request){
		return SignUpAdmin
			.Response
			.from(storeMemberService.createStoreAdmin(request));
	}

}
