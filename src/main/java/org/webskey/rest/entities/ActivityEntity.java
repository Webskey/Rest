package org.webskey.rest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "activityentity")
@AllArgsConstructor
@NoArgsConstructor
public class ActivityEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;

	@Column
	private String name;

	@Column
	private String des;

	@Column
	private int num;
}