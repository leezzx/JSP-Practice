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
		
		int num = 0; // MVC model 2 ���, Controller�� view���� �и�
		String num_ = request.getParameter("n");
		if(num_ != null && !num_.equals("")) {
			num = Integer.parseInt(num_);
		}
		
		String result;
		
		if(num % 2 != 0) {
			result = "Ȧ��";
		} else {
			result = "¦��";
		}
		
		request.setAttribute("result", result); // request�� result���� ����
		
		String[] names = {"mewlec", "dragon"}; // ELȰ�� �׽�Ʈ�� ���� ����Ʈ ����
		request.setAttribute("names", names);
		
		Map<String, Object> notice = new HashMap<String, Object>(); // ELȰ�� �׽�Ʈ
		notice.put("id", 1);
		notice.put("title", "EL�� ���ƿ�");
		request.setAttribute("notice", notice);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("spag.jsp"); // forward �ϱ� ���� ����
		dispatcher.forward(request, response);
		
	}
	
}
