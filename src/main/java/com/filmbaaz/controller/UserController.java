package com.filmbaaz.controller;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filmbaaz.model.User;
import com.filmbaaz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/public/login")
	public JSONObject authenticateUser(@Valid @RequestBody User user) throws ParseException
	{
		return userService.authenticateUser(user);
	}
	
	@PostMapping("/public/register")
	public JSONObject addUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}
	
	@GetMapping("/public/getUsers/{email}")
	public JSONObject getUsers(@PathVariable String email)
	{
		return userService.getUser(email);
	}
	
	@GetMapping("/public/getAllUsers")
	public JSONObject getAllUsers()
	{
		return userService.getAllUsers();
	}
	
	@PostMapping("/public/sendVerificationMail")
	public JSONObject sendVerificationMail(@RequestBody User user) throws ParseException
	{
		return userService.sendVerificationMail(user);
	}
	
	@PostMapping("/public/verifyUser/{code}")
	public JSONObject verifyUser(@RequestBody User user, @PathVariable String code) throws ParseException
	{
		return userService.verifyUser(user, code);
	}
	
	@PostMapping("/private/delete")
	public JSONObject verifyUser(@RequestBody User user) throws ParseException
	{
		return userService.deleteUser(user);
	}

}
