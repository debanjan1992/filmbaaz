package com.filmbaaz.service;

import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filmbaaz.model.User;
import com.filmbaaz.repository.UserRepository;

@Service("userService")
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CommonService commonService;

	@SuppressWarnings("unchecked")
	public JSONObject authenticateUser(User user) throws ParseException {

		JSONObject response = new JSONObject();
		Optional<User> dbUser = userRepository.findById(user.getEmail());
		if (dbUser != null) {
			if (dbUser.get().getPassword().equals(commonService.cryptWithMD5(user.getPassword()))) {
				JSONParser parser = new JSONParser();
				JSONObject params = (JSONObject) parser.parse(dbUser.get().getParams());
				if (params.get("verified").equals("true")) {
					response.put("success", true);
					response.put("message", "success");
				} else {
					response.put("success", false);
					response.put("message", "user is not verified");
				}
			} else {
				response.put("success", false);
				response.put("message", "invalid password");
			}
		} else {
			response.put("success", false);
			response.put("message", "user does not exist");
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public JSONObject addUser(User user) {
		JSONObject response = new JSONObject();
		JSONObject params = new JSONObject();
		params.put("verified", "false");
		params.put("verificationCode", commonService.getSaltString());
		user.setIsAdmin(false);
		user.setPassword(commonService.cryptWithMD5(user.getPassword()));
		user.setParams(params.toString());
		userRepository.save(user);
		response.put("success", true);
		response.put("message", "success");
		return response;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getAllUsers() {
		JSONObject response = new JSONObject();
		List<User> list = userRepository.findAll();
		list.forEach(u -> u.setPassword(null));
		response.put("success", true);
		response.put("message", "success");
		response.put("users", list);
		return response;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getUser(String email) {
		JSONObject response = new JSONObject();
		response.put("success", true);
		response.put("message", "success");
		response.put("users", userRepository.findById(email));
		return response;
	}

	@SuppressWarnings("unchecked")
	public JSONObject sendVerificationMail(User user) throws ParseException {
		JSONObject response = new JSONObject();
		response.put("success", true);
		response.put("message", "success");
		Optional<User> dbUser = userRepository.findById(user.getEmail());
		if (dbUser != null) {
			JSONParser parser = new JSONParser();
			JSONObject paramsObj = (JSONObject) parser.parse(dbUser.get().getParams());
			if (paramsObj.get("verified").equals("false")) {
				response.put("success", true);
				response.put("message", "verification mail sent");
			} else {
				response.put("success", false);
				response.put("message", "user is already verified");
			}
		} else {
			response.put("success", false);
			response.put("message", "user does not exist");
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	public JSONObject verifyUser(User user, String code) throws ParseException {

		JSONObject response = new JSONObject();
		User dbUser = userRepository.findUserByEmail(user.getEmail());
		JSONParser parser = new JSONParser();
		JSONObject paramsObj = (JSONObject) parser.parse(dbUser.getParams());
		if (paramsObj.get("verified").equals("false")) {
			if (paramsObj.get("verificationCode").equals(code.trim())) {
				paramsObj.put("verified", "true");
				paramsObj.remove("verificationCode");
				dbUser.setParams(paramsObj.toString());
				userRepository.save(dbUser);
				response.put("success", true);
				response.put("message", "user verified");
			} else {
				response.put("success", false);
				response.put("message", "invalid verification code");
			}
		} else {
			response.put("success", false);
			response.put("message", "user already verified");
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	public JSONObject deleteUser(User user) {
		JSONObject response = new JSONObject();
		if (userRepository.findById(user.getEmail()) != null) {
			userRepository.delete(user);
			response.put("success", true);
			response.put("message", "user deleted");
		} else {
			response.put("success", false);
			response.put("message", "user does not exist");
		}
		return response;
	}
}
