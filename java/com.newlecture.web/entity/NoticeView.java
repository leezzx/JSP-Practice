package com.newlecture.web.entity; // class 만들시 superclass에 Notice를 적어서 연결

import java.util.Date;

public class NoticeView extends Notice {
	
	private int cmtCount;
	
	
	
	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}

	public NoticeView() {
		// TODO Auto-generated constructor stub
	}
	
	public NoticeView(int id, String title, Date regdate, String writerId, String hit, String files, int cmtCount) {
		super(id, title, regdate, writerId, hit, files, ""); // super를 통해 Notice entity에서 불러옴
		this.cmtCount = cmtCount;
	}

}
