package com.cth.lhmbe.model;

import com.cth.lhmbe.Enum.RoleEnum;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class StoreMember extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long storeMemberId;

	@ManyToOne
	private Member member;

	@ManyToOne
	private Store store;

	private RoleEnum roleType;

	private boolean isDelete;

}
