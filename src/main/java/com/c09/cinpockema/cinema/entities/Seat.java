package com.c09.cinpockema.cinema.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Seat {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Min(value=0)
	@Max(value=1000)
	@Column(nullable=false)
	private Integer row;
	
	@NotNull
	@Min(value=0)
	@Max(value=1000)
	@Column(nullable=false)
	private Integer col;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @JoinColumn(name="hall_id")
	private Hall hall;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Integer getRow() {
		return row;
	}
	
	public void setRow(Integer row) {
		this.row = row;
	}
	
	public Integer getCol() {
		return col;
	}
	
	public void setCol(Integer col) {
		this.col = col;
	}
	
	@JsonBackReference
	public Hall getHall() {
		return hall;
	}
	
	@JsonIgnore
	public void setHall(Hall hall) {
		this.hall = hall;
	}
	
}