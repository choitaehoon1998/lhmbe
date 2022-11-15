package com.cth.lhmbe.model;

import com.cth.lhmbe.type.RoleEnum;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
