package com.newlecture.web.controller.admin.notice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

@MultipartConfig( // 파일 업로드를 위하 서블릿 어노테이션
		// location="/tmp", // 전송한 데이터가 1메가바이트를 넘을 경우 디스크를 씀
		fileSizeThreshold=1024*1024, // 전송한 데이터가 1메가바이트
		maxFileSize=1024*1024*50, // 파일 하나의 최대 크기 50메가바이트
		maxRequestSize=1024*1024*50*5 // 전체 요청에서는 250메가바이트를 초과할 수 없다.
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(request, response);
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		
		Part filePart = request.getPart("file");
		filePart.getInputStream();
		
		String realPath = request.getServletContext().getRealPath("/upload"); // 파일 저장을 위한 물리경로를 알아내기
		
		boolean pub = false;
		if(isOpen != null) {
			pub = true;
		}
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec");
		
		NoticeService service = new NoticeService();
		service.insertNotice(notice);
		
		response.sendRedirect("list");
	}
	
}
