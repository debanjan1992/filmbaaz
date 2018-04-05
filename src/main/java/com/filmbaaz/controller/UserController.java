package com.filmbaaz.controller;

import java.net.UnknownHostException;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	@CrossOrigin
	public JSONObject authenticateUser(@Valid @RequestBody User user) throws ParseException
	{
		return userService.authenticateUser(user);
	}
	
	@PostMapping("/public/register")
	@CrossOrigin
	public JSONObject addUser(@Valid @RequestBody User user) throws ParseException, UnknownHostException
	{
		return userService.addUser(user);
	}
	
	@GetMapping("/public/getUser/{email}")
	@CrossOrigin
	public JSONObject getUsers(@PathVariable String email)
	{
		return userService.getUser(email);
	}
	
	@GetMapping("/public/getAllUsers")
	@CrossOrigin
	public JSONObject getAllUsers()
	{
		return userService.getAllUsers();
	}
	
	@PostMapping("/public/sendVerificationMail")
	@CrossOrigin
	public JSONObject sendVerificationMail(@Valid @RequestBody User user) throws ParseException, UnknownHostException
	{
		return userService.sendVerificationMail(user);
	}
	
	@GetMapping("/public/verifyUser/{code}/{email}")
	@CrossOrigin
	public JSONObject verifyUser(@PathVariable String code, @PathVariable String email) throws ParseException
	{
		return userService.verifyUser(code, email);
	}
	
	@PostMapping("/public/delete")
	@CrossOrigin 
	public JSONObject verifyUser(@Valid @RequestBody User user) throws ParseException
	{
		return userService.deleteUser(user);
	}

}
