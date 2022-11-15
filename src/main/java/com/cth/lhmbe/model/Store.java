package com.cth.lhmbe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long storeId;

	private String storeName;

	private String storeNumber;

	private String storeLocation;

	private boolean isDelete;
}
