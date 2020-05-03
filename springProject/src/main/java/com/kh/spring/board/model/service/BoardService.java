package com.kh.spring.board.model.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {

	// 1. 게시글 리스트 조회용 서비스
	// 1_1. 게시글 총 개수 조회용 서비스
	int getListCount();
	// 1_2. 현재 요청한 페이지에 보여져야 할 리스트 조회용 서비스
	ArrayList<Board> selectList(PageInfo pi);
	
	// 2. 게시글 작성용 서비스
	int insertBoard(Board b);
	
	// 3. 게시글 상세조회용 서비스
	// 3_1. 게시글 조회수 증가용 서비스
	int increaseCount(int bno);
	// 3_2. 게시글 상세조회용 서비스
	Board selectBoard(int bno);
	
	// 4. 게시글 삭제용 서비스
	int deleteBoard(int bno);
	
	// 5. 게시글 수정용 서비스
	int updateBoard(Board b);
	
	// 6. 댓글 리스트 조회용 서비스
	ArrayList<Reply> selectReplyList(int bno);
	
	// 7. 댓글 작성용 서비스
	int insertReply(Reply r);
}
