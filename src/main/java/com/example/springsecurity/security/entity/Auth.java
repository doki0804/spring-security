package com.example.springsecurity.security.entity;

import com.example.springsecurity.security.dto.AuthDto;
import lombok.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AUTH")
public class Auth{

	@EmbeddedId
	private AuthId id;

	@OneToOne
	@JoinColumn(name = "menu_seq", insertable = false, updatable = false)
	private Menu menu;
	private String authNm;
	private String authSelect;
	private String authInsert;
	private String authUpdate;
	private String authDelete;
	private String authDownload;
	private String authPrint;
	private String inUserId;
	private LocalDateTime inDt;

	@PrePersist
	public void prePersist(){
		inDt = LocalDateTime.now();
	}
}