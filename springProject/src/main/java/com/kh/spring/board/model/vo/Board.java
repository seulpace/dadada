package com.kh.spring.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {
	
	private int boardNo;			// 게시글 번호
	private String boardTitle;		// 게시글 제목
	private String boardWriter;		// 게시글 작성자 아이디
	private String boardContent;	// 게시글 내용
	private String originName;		// 첨부된 파일의 원본명
	private String changeName;		// 첨부된 파일의 수정명 (실제로 업로드된 파일명)
	private int count;				// 게시글 조회수
	private Date createDate;		// 게시글 작성일
	private String status;			// 게시글 상태
}
