package com.github.conradovera.xmlmapping;

import java.util.Date;

public class LugarRamses {
	private Long id;
	private String nombre;
	private String ciudad;
	
	public LugarRamses () {
		//constructor por defecto
	}
	
	public LugarRamses(String nombre, String ciudad) { //constructor sobrecargado
		this.ciudad = ciudad;
		this.nombre=nombre;
	}
	 
//Encapsulados
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
}
