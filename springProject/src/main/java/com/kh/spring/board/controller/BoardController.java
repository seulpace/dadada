package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;
	
	@RequestMapping("list.bo")
	public String selectList(Model model, @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {

		/*
		 * 값을 처음에 넣어주면 에러가 안 뜨는데 '자유게시판'을 누르면 currentPage값이 넘어가지 않아 500 에러가 뜬다
		 * - required=false --> 필수값으로 받아오는 것이 '아니다'라고 명시하는 것
		 * - defaultValue는 기본값 넣어주는 것! 문자열 안에 넣어도 알아서 인식함
		 * 
		 * 1. int currentPage --> 값이 넘어오지 않았을 때 에러 (해당 키 값으로 넘어오는 값이 없을 경우 주입을 할 수 없기 때문)
		 * 
		 * 2. @RequestParam(value="currentPage", required=false) int currentPage
		 * 	  --> 해당 키값으로 넘어오는 값이 없을 경우 null을 주입하려고 함 (단, int형 변수에는 null 담을 수 없음)
		 * 
		 * 3. @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage
		 *    --> null값이 들어왔을 경우 기본값이 담길 수 있게 지정
		 */
		
		//System.out.println(currentPage);
		
		// 현재 총 게시글 개수
		int listCount = bService.getListCount();
		
		// pageLimit : 10, boardLimit : 5
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		// 요청한 페이지에 보여져야 할 리스트 조회
		ArrayList<Board> list = bService.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		return "board/boardListView";
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, HttpServletRequest request, Model model,
							  @RequestParam(value="uploadFile", required=false) MultipartFile file) {
		//System.out.println(b);
		//System.out.println(file.getOriginalFilename()); // 전달되는 파일이 없을 경우 빈 문자열로 전달
		// 파일업로드를 위한 라이브러리 추가해야 됨 --> root-context.xml에 파일 처리 관련하여 등록
		
		// 전달되는 파일이 있을 경우 --> 서버에 전달된 파일 업로드 처리
		if(!file.getOriginalFilename().equals("")) {
			String changeName = saveFile(file, request); // 수정명
			String originName = file.getOriginalFilename(); // 원본명
			
			b.setOriginName(originName);
			b.setChangeName(changeName);
		}
		
		int result = bService.insertBoard(b);
		
		if(result > 0) { // 게시판 등록 성공
			return "redirect:list.bo";
		} else { // 게시판 등록 실패
			model.addAttribute("msg", "게시판 작성 실패");
			return "common/errorPage";
		}
	}
	
	/*
	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model) {
		
		// 조회수 증가
		int result = bService.increaseCount(bno);
		
		if(result > 0) { // 유효한 게시글일 경우 (=조회수 증가 성공) --> 조회
			
			Board b = bService.selectBoard(bno);
			model.addAttribute("b", b);
			return "board/boardDetailView";
			
		} else { // 유효한 게시글이 아닐 경우 (=조회수 증가 실패) --> 에러 페이지
			
			model.addAttribute("msg", "유효한 게시글 아님");
			return "common/errorPage";
		}
	}
	*/
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno, ModelAndView mv) {
		
		// 조회수 증가
		int result = bService.increaseCount(bno);
		
		if(result > 0) { // 유효한 게시글일 경우 (=조회수 증가 성공) --> 조회
			
			Board b = bService.selectBoard(bno);
			//mv.addObject("b", b);
			//mv.setViewName("board/boardDetailView");
			mv.addObject("b", b)
			  .setViewName("board/boardDetailView");
			
			// ModelAndView는 ModelAndView를 리턴하기 때문에 거기에 이어서 써도 됨
			// 다만 setViewName은 반환형이 void이기 때문에 가장 마지막에 적어둘 것 
			
		} else { // 유효한 게시글이 아닐 경우 (=조회수 증가 실패) --> 에러 페이지
			mv.addObject("msg", "유효한 게시글 아님").setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String fileName, HttpServletRequest request, Model model) {
		// fileName : 첨부파일이 있을 경우 실제 업로드 되어있는 수정명 / 첨부파일이 없을 경우 "" (빈 문자열)
		
		int result = bService.deleteBoard(bno);
		
		if(result > 0) { // 성공적으로 db에 삭제 처리 되었을 경우 --> 서버에 업로드 된 파일이 있으면 파일도 삭제해야 됨
			
			if(!fileName.equals("")) { // 기존에 첨부파일이 있었을 경우
				deleteFile(fileName, request); // 파일 삭제
			}
			
			return "redirect:list.bo";
			
		} else { // 삭제 실패
			model.addAttribute("msg", "게시글 삭제 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("updateForm.bo")
	public ModelAndView updateForm(int bno, ModelAndView mv) {
		
		Board b = bService.selectBoard(bno);
		
		mv.addObject("b", b)
		  .setViewName("board/boardUpdateForm");
		
		return mv;
	}
	
	@RequestMapping("update.bo")
	public ModelAndView updateBoard(Board b, HttpServletRequest request, ModelAndView mv,
									@RequestParam(value="reUploadFile", required=false) MultipartFile file) {
		// b: 게시글 번호, 수정된 제목, 작성자, 수정된 내용, [기존의 첨부파일이 있었다면 원본명, 수정명]
		
		// 새로 첨부된 파일이 있을 경우
		if(!file.getOriginalFilename().equals("")) {
			// 새로 넘어온 첨부파일도 있고 + 기존의 첨부파일이 있었을 경우
			if(b.getOriginName() != null) { // --> 기존의 첨부파일을 지워야 됨
				deleteFile(b.getChangeName(), request);
			}
			
			// 새로 넘어온 파일 서버에 업로드 처리해야 됨
			String changeName = saveFile(file, request);
			String originName = file.getOriginalFilename();
			
			b.setChangeName(changeName);
			b.setOriginName(originName);
		}
		
		int result = bService.updateBoard(b);
		
		if(result > 0) { // 수정 성공했을 경우 --> 상세보기 요청
			mv.addObject("bno", b.getBoardNo())
			  .setViewName("redirect:detail.bo"); // detail.bo?bno=1
		} else { // 수정 실패했을 경우 --> errorPage
			mv.addObject("msg", "게시글 수정 실패")
			  .setViewName("common/errorPage");
		}
		
		return mv;
		
	}
	
	// 전달 받은 파일명을 찾아서 삭제시키는 메소드 (공유해서 쓸 수 있게 따로 뺌)
	public void deleteFile(String fileName, HttpServletRequest request) {
		String resources = request.getSession().getServletContext().getRealPath("resources");
		String savePath = resources + "\\upload_files\\";
		
		File deleteFile = new File(savePath + fileName);
		deleteFile.delete();
	}
	
	// 전달 받은 파일을 서버에 업로드 시키는 메소드 (공유해서 쓸 수 있게 따로 뺌)
	public String saveFile(MultipartFile file, HttpServletRequest request) {
		
		// 1. 전달받은 파일을 업로드 할 폴더 경로 (String savePath)
		String resources = request.getSession().getServletContext().getRealPath("resources");
		String savePath = resources + "\\upload_files\\";
		
		// 2. 실제로 서버에 업로드 할 파일명 수정 (년월일시분초.jpg --> 20200421131523.jpg)
		String originName = file.getOriginalFilename(); // 원본명 (aaa.jpg)
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(new Date()); // "20200421131523"
		String ext = originName.substring(originName.lastIndexOf("."));	// ".jpg"
		
		String changeName = currentTime + ext; // 20200421131523.jpg
		
		try {
			file.transferTo(new File(savePath + changeName)); // 서버에 업로드 시키는 구문
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return changeName;
	}
	
	@ResponseBody
	@RequestMapping(value="rlist.bo", produces="application/json; charset=utf-8")
	public String selectReplyList(int bno) {
		
		ArrayList<Reply> list = bService.selectReplyList(bno);
		
		// Gson gson = new Gson(); // 기본 Date 포맷
		// date 포맷을 지정하고자 할 때는 GsonBuilder를 사용한다
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		return gson.toJson(list);
	}
	
	@ResponseBody
	@RequestMapping("rinsert.bo")
	public String insertReply(Reply r) {
		
		int result = bService.insertReply(r);
		
		return String.valueOf(result); // 문자열로 변환시켜 리턴
	}
}
