package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // 해당 클래스를 Controller 역할을 하는 빈으로 등록하는 어노테이션
public class MemberController {
	
	// DI (Dependency Injection)
	@Autowired
	private MemberService mService;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	// 요청 시 전달되는 값 처리하는 방법
	
	/*
	 * 1. HttpServletRequest를 통해 전송 받기 (기존 jsp/servlet 때 방식)
	 * 	    메소드의 매개변수로 HttpServletRequest 작성하면 해당 메소드 실행 시
	 *    스프링 컨테이너가 자동으로 객체를 주입해줌
	 */
	
	/*
	@RequestMapping("login.me") // 해당 url 요청 시 이 메소드 실행되게 HandlerMapping으로 등록하는 어노테이션
	public String loginMember(HttpServletRequest request) {
		
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		// 리턴되는 문자열은 servlet-context.xml 파일에 기록된 ViewResolver에 의해 사용자가 보게 될 뷰가 지정		
		return "main";
	}
	*/
	
	/*
	 * 2. @RequestParam 어노테이션 방식
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(@RequestParam("id") String userId, 
							  @RequestParam("pwd") String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * 3. @RequestParam 어노테이션 생략 가능한 방법
	 * 
	 * 위의 어노테이션을 생략해도 전달값을 변수에 저장 가능
	 * (단, input요소의 name값을 매개변수명과 동일할 경우 자동으로 주입됨)
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(String userId, String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * 4. @ModelAttribute를 이용한 방법
	 * 
	 * 	    커맨드 객체 방식이라고도 함
	 * 	    스프링 컨테이너가 기본생성자로 생성 후 setter로 값 주입
	 * 	  (단, 반드시 name 속성값과 vo클래스의 필드명이 동일해야 됨) 
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(@ModelAttribute Member m) {
		// 내부적으로 매개변수에 다음과 같이 vo를 작성해놓으면
		// 기본생성자로 생성 후 setter 통해서 값이 주입
		// 즉, vo클래스에는 setter 반드시 있어야만 함
		
		System.out.println("ID : " + m.getUserId());
		System.out.println("PWD : " + m.getUserPwd());
		return "main";
	}
	*/
	
	/*
	 * 5. @ModelAttribute 어노테이션 생략하는 방법
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, HttpSession session) {
		
		// 이제 본격적으로 로그인 서비스 요청
		
		//MemberService mService = new MemberServiceImpl();
		
		
		 * 위와 같이 직접적으로 객체 생성 시 문제점 발생
		 * 
		 * 1. MemberServiceImpl 클래스명이 바뀌었을 경우? --> 에러 발생 --> 다 찾아서 변경해야 됨
		 * 2. 주소값 출력 시 매번 주소값 달라짐 == 계속 새로이 객체 생성
		 * 
		 * 왜? 위의 이유는 개발자가 직접 객체생성(결합도 높음)을 해서 발생하는 문제
		 * 
		 * 1. 클래스명 변경해도 코드 변경할 필요 없음
		 * 2. 매 요청 시마다 같은 주소값 확인 가능(즉, 한 개의 객체만 생성해놓고 재사용 개념 == 싱글톤 개념/JDBCTemplete때 얘기)
		 
		//System.out.println(mService);

		Member loginUser = mService.loginMember(m);
		
		if(loginUser != null) { // 로그인 성공

			// 세션 객체에 해당 loginUser 담기
			// HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			//return "main";
			
			// /WEB-INF/views/	main .jsp
			
			return "redirect:/"; // response.sendRedirect 방식
			
		} else { // 로그인 실패
			
			// 에러페이지 보여주게 (메세지 담아서)
			return "common/errorPage";
			
			
		}
		
	}
	*/
	
	// 응답페이지에 응답할 데이터가 있을 경우에 대한 방법
	/*
	 * 1. Model이라는 객체를 사용하는 방ㅂ버
	 * 
	 * Model이라는 객체에 응답할 뷰에 전달하고자 하는 데이터를 맵 형식(key, value)로 담을 수 있음
	 * scope는 request이다.
	 * 단, setAttribute가 아닌 addAttribute로 담음
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, HttpSession session, Model model) {
		Member loginUser = mService.loginMember(m);
		
		if(loginUser != null) { // 로그인 성공
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";
		} else { // 로그인 실패
			model.addAttribute("msg", "로그인 실패");
			return "common/errorPage";
		}
	}
	*/
	
	/*
	 * 2. ModelAndView 객체를 사용하는 방법
	 * 
	 * Model은 응답할 데이터를 맵형식으로 담는 공간이라고 한다면
	 * View라는 포워딩 할 뷰 페이지에 대한 정보를 담는 공간
	 * 
	 * ModelAndView는 위의 두 객체를 합친 객체
	 * 
	 */
	/*
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		Member loginUser = mService.loginMember(m);
		
		if(loginUser != null) { // 로그인 성공
			session.setAttribute("loginUser", loginUser);
			//return "redirect:/";
			mv.setViewName("redirect:/");
		} else { // 로그인 실패
			//model.addAttribute("msg", "로그인 실패");
			//return "common/errorPage";
			
			mv.addObject("msg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
	}
	*/
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		return "member/memberEnrollForm";
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		//System.out.println(m);
		
		// 1. 한글 부분 깨짐 --> encodingFilter 달아야 됨 --> web.xml에 스프링에서 제공하는 EncodingFilter 등록
		// 2. int 나이 같은 경우 빈 문자열로 들어왔을 때 int형으로 파싱할 때 문제 생김 --> int age => String age
		// 3. 사용자가 입력한 비밀번호 평문 그대로 --> 암호화 진행 --> 암호문
		
		/*
		 * * 기존에 SHA-512 (해쉬알고리즘) 보안에 취약
		 * > 사용자가 입력한 평문이 매번 똑같은 암호문으로 나옴
		 *  => 다양한 샘플이 확보되면 암호문을 통해 평문을 유추할 수 있음
		 *  
		 * * 스프링 시큐리티에서 제공하는 BCrypt 방식(salting기법)을 이용한 암호화
		 * 
		 * > 솔팅기법 (salting)
		 *   평문 + salt값(랜덤값) --> 암호문 
		 *   
		 * --> 똑같은 평문을 입력해도 매번 암호문이 달라짐
		 * 
		 * * Bcrypt 방식을 하고자 한다면 라이브러리 추가해야 됨 (스프링 시큐리티 관련)
		 */
//		System.out.println("암호호 ㅏ전 : " + m.getUserPwd());

		// 암호화 작업
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		m.setUserPwd(encPwd);
//		System.out.println("암호화 후 : " + encPwd);
		
		int result = mService.insertMember(m);
		
		if(result > 0) { // 회원가입 성공
			session.setAttribute("msg", "회원가입성공");
			return "redirect:/";
		} else { // 회원가입 실패
			model.addAttribute("msg", "회원가입실패");
			return "common/errorPage";
		}
	}
	
	// 암호화 처리 후 로그인
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		Member loginUser = mService.loginMember(m); // loginUser.userPwd에 DB에 저장된 암호문 담겨있음 
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 로그인 성공
			
			// matches(평문, 암호문)
			// 내부적으로 암호문을 복호화해서 평문+salt 값으로 변환됨
			// 복호화된 평문+salt 값에서 salt 값을 뺀 평문값과 matches("평문")값을 비교 수행
			
			session.setAttribute("loginUser", loginUser);
			//return "redirect:/";
			mv.setViewName("redirect:/");
		} else { // 로그인 실패
			//model.addAttribute("msg", "로그인 실패");
			//return "common/errorPage";
			
			mv.addObject("msg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
		// /WEB-INF/views/member/myPage.jsp
	}
	
	@RequestMapping("update.me")
	public String updateMember(Member m, Model model, HttpSession session) {
		
		// 수정하고자 하는 자식의 정보가 m에 담겨서 dao로 전달
		int result = mService.updateMember(m);
		
		if(result > 0) { // 정보수정 성공
			session.setAttribute("loginUser", mService.loginMember(m));
			session.setAttribute("msg", "회원정보 수정 성공");
			return "redirect:myPage.me";
		} else { // 정보수정 실패
			model.addAttribute("msg", "회원정보 수정 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("delete.me")
	public String deleteMember(HttpSession session, Model model) {
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		
		int result = mService.deleteMember(userId);
		
		if(result > 0) { // 회원탈퇴 성공 --> 세션을 무효화 시킨 후 메인페이지
			return "redirect:logout.me";
		} else { // 회원탈퇴 실패
			model.addAttribute("msg", "회원탈퇴 실패");
			return "common/errorPage";			
		}
	}
	
	@ResponseBody
	@RequestMapping("idCheck.me")
	public String idCheck(String userId) {
		int count = mService.idCheck(userId);
		
		// 결과를 스트링으로 변환해서 넘기기
		return String.valueOf(count);
	}
}
