package com.company.hrsystem.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.company.hrsystem.request.AuthenRequest;
import com.company.hrsystem.request.ChangePasswordRequest;
import com.company.hrsystem.request.IsEmailInDbRequest;
import com.company.hrsystem.request.SignUpRequest;
import com.company.hrsystem.request.UpdateAccountRequest;
import com.company.hrsystem.response.ResponseData;

public interface IAuthenticationService {

	public ResponseData handleLogin(AuthenRequest req, HttpServletRequest servletRequest);

	public ResponseData handleLogOut(HttpServletRequest request);

	public ResponseData handleSignUp(SignUpRequest request, HttpServletRequest servletRequest);

	public ResponseData handleChangePassword(ChangePasswordRequest changePwRequest,
			HttpServletRequest servletRequest);

	public ResponseData updateAccount(UpdateAccountRequest accountRequest, HttpServletRequest servletRequest);

	public ResponseData findAccounts(HttpServletRequest servletRequest);

	public ResponseData isEmailInDb(IsEmailInDbRequest request);

}
