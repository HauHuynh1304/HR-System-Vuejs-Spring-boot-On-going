package com.company.hrsystem.serviceInterface;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.request.AuthenRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.IsEmailInDbRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.request.UpdateAccountRequest;
import com.company.hrsystem.response.ResponseTemplate;

public interface AuthenticationServiceInterface {

	public ResponseTemplate handleLogin(AuthenRequest req, HttpServletRequest servletRequest);

	public ResponseTemplate handleLogOut(HttpServletRequest request);

	public ResponseTemplate handleSignUp(SignUpRequest request, HttpServletRequest servletRequest);

	public ResponseTemplate handleChangePassword(HttpServletRequest servletRequest,
			ChangePasswordRequest ChangePwRequest);

	public ResponseTemplate updateAccount(UpdateAccountRequest accountRequest, HttpServletRequest servletRequest);

	public ResponseTemplate findAccounts(HttpServletRequest servletRequest);
	
	public ResponseTemplate isEmailInDb(IsEmailInDbRequest request);

}
