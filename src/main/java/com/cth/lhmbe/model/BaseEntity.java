package com.cth.lhmbe.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
	@Column(updatable = false)
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;

	@PrePersist
	public void setUp(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.createdBy = "system";
		this.updatedBy = "system";
		if(authentication != null && authentication.getName() != null){
			this.createdBy =  authentication.getName();
			this.updatedBy =  authentication.getName();
		}
	}

	@PreUpdate
	public void beforeUpdate(){
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = "system";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getName() != null){
			this.updatedBy = authentication.getName();
		}
	}
}
