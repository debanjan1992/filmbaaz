package com.filmbaaz.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filmbaaz.model.Role;
import com.filmbaaz.model.User;
import com.filmbaaz.repository.UserRepository;

@Service("userService")
public class UserService {

	@Autowired
	UserRepository userRepository;

	public boolean authenticateUser(User user) throws ParseException {

		User dbUser = userRepository.findUserByEmail(user.getEmail());
		JSONParser parser = new JSONParser();

		JSONObject params = (JSONObject) parser.parse(dbUser.getParams());
		if (dbUser.getPassword().equals(user.getPassword())) {
			if (params.get("verified") == "true")
				return true;
			else
				return false;
		} else
			return false;
	}

	@SuppressWarnings("unchecked")
	public boolean addUser(User user) {
		/*Set<Role> roles = new HashSet<>();
		roles.add(new Role("USER"));
		user.setRoles(roles);*/
		JSONObject params = new JSONObject();
		params.put("verified", "false");
		params.put("verificationCode", "12345");
		user.setParams(params.toString());
		userRepository.save(user);
		return true;
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public User getUser(String email) {
		return userRepository.findUserByEmail(email);
	}

	public boolean sendVerificationMail(User user) throws ParseException {
		User dbUser = userRepository.findUserByEmail(user.getEmail());
		JSONParser parser = new JSONParser();
		JSONObject paramsObj = (JSONObject) parser.parse(dbUser.getParams());
		if(paramsObj.get("verified").equals("false"))
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	public boolean verifyUser(User user, String code) throws ParseException {

		User dbUser = userRepository.findUserByEmail(user.getEmail());
		JSONParser parser = new JSONParser();
		System.out.println("INPUT------------------------------");
		JSONObject paramsObj = (JSONObject) parser.parse(dbUser.getParams());
		if (paramsObj.get("verificationCode").equals(code.trim())) {
			paramsObj.put("verified", "true");
			paramsObj.remove("verificationCode");
			dbUser.setParams(paramsObj.toString());
			userRepository.save(dbUser);
			return true;
		}
		System.out.println(code);
		return false;
	}
}
