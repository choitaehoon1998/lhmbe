package com.cth.lhmbe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE member SET is_delete = 'Y' WHERE userId = ?")
@Where(clause = "is_delete = 'N'")
@Getter
public class Member extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long memberId;

	private String userName;

	@Column(unique = true)
	private String email;

	@Setter
	private String password;

	@Column(nullable = false, length = 1)
	private boolean isDelete;
}
