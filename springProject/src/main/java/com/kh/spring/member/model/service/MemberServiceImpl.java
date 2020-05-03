package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//@Component 			 // 단순한 빈으로 등록하기 위한 어노테이션
@Service("mService")	 // Component보다 구체화 된, 이 클래스는 Service의 의미를 지닌다
public class MemberServiceImpl implements MemberService{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDao mDao;
	
	/**
	 * 1. 로그인용 서비스 
	 */
	@Override
	public Member loginMember(Member m) {
		
		return mDao.loginMember(sqlSession, m);
		
	}

	/**
	 * 2. 회원가입용 서비스
	 */
	@Override
	public int insertMember(Member m) {
		return mDao.insertMember(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		return mDao.updateMember(sqlSession, m);
	}

	@Override
	public int deleteMember(String userId) {
		return mDao.deleteMember(sqlSession, userId);
	}

	@Override
	public int idCheck(String userId) {
		return mDao.idCheck(sqlSession, userId);
	}

}
