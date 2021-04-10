package com.newlecture.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class Spag extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int num = 0; // MVC model 2 방식, Controller와 view서블릿 분리
		String num_ = request.getParameter("n");
		if(num_ != null && !num_.equals("")) {
			num = Integer.parseInt(num_);
		}
		
		String result;
		
		if(num % 2 != 0) {
			result = "홀수";
		} else {
			result = "짝수";
		}
		
		request.setAttribute("result", result); // request에 result값을 담음
		
		String[] names = {"mewlec", "dragon"}; // EL활용 테스트를 위한 리스트 생성
		request.setAttribute("names", names);
		
		Map<String, Object> notice = new HashMap<String, Object>(); // EL활용 테스트
		notice.put("id", 1);
		notice.put("title", "EL은 좋아요");
		request.setAttribute("notice", notice);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("spag.jsp"); // forward 하기 위한 구문
		dispatcher.forward(request, response);
		
	}
	
}
