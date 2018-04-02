package com.filmbaaz.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	public Role(String name)
	{
		this.name = name;
	}
	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "role_name")
	@NotBlank
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
