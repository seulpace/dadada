package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Repository
public class BoardDao {

	public int getListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("boardMapper.getListCount");
	}
	
	public ArrayList<Board> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
		
		// * offset : 몇 개의 게시글 건너 뛰고 조회할 건지
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		// 3페이지라면 앞에 10개 게시글 뒤에 10번부터 조회해야 하니까
		// (3-1) * 5
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectList", null, rowBounds);
	}
	
	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.insert("boardMapper.insertBoard", b);
	}
	
	public int increaseCount(SqlSessionTemplate sqlSession, int bno) {
		return sqlSession.update("boardMapper.increaseCount", bno);
	}
	
	public Board selectBoard(SqlSessionTemplate sqlSession, int bno) {
		return sqlSession.selectOne("boardMapper.selectBoard", bno);
	}
	
	public int deleteBoard(SqlSessionTemplate sqlSession, int bno) {
		return sqlSession.update("boardMapper.deleteBoard", bno);
	}
	
	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.update("boardMapper.updateBoard", b);	
	}
	
	public ArrayList<Reply> selectReplyList(SqlSessionTemplate sqlSession, int bno) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectReplyList", bno);
	}
	
	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("boardMapper.insertReply", r);
	}
}
