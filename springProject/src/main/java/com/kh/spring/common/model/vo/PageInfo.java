package com.kh.spring.common.model.vo;

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
public class PageInfo {
	
	private int listCount;		// 총 게시글 갯수
	private int currentPage;	// 현재 페이지 (요청한 페이지)
	private int startPage;		// 현재 페이지에 보여질 페이징바의 시작 수
	private int endPage;		// 현재 페이지에 보여질 페이징바의 끝 수
	private int maxPage;		// 마지막 페이지 수
	private int pageLimit;		// 페이징 바의 최대 수
	private int boardLimit;		// 한 페이지에 보여질 게시글 최대 수
}
