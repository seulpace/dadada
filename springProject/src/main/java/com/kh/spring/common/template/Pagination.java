package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {

	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {

		// * maxPage : 마지막 페이지 수 (listCount, boardLimit에 영향을 받는)
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		// * startPage : 현재 요청한 페이지 하단에 보여질 페이징 바의 시작 수 (currentPage, pageLimit에 영향을 받는 
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// * endPage : 현재 요청한 페이지 하단에 보여질 페이징 바의 끝 수(startPage, pageLimit, maxPage에 영향을 받는)
		int endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		return new PageInfo(listCount, currentPage, startPage, endPage, maxPage, pageLimit, boardLimit);
	}
}
