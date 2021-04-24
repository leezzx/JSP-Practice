package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public class NoticeService {
	
	public int removeNotice(int[] ids) {
		
		return 0;
		
	}
	
	public int pubNoticeAll(int[] ids) {
		
		return 0;
		
	}
	
	public int insertNotice(Notice notice) {
		
		return 0;
		
	}
	
	public int deleteNotice(int id) {
		
		return 0;
		
	}
	
	public int updateNotice(Notice notice) {
		
		return 0;
		
	}
	
	List<Notice> getNoticeNewestListt() {
		
		return null;
		
	}
	
	public List<NoticeView> getNoticeList() {
		
		return getNoticeList("title", "", 1);
		
	}
	
	public List<NoticeView> getNoticeList(int page) {
		
		return getNoticeList("title", "", page);
		
	}
	
	public List<NoticeView> getNoticeList(String field /* TITLE, WRITER_ID */, String query /* A */, int page) {
		
		// DB를 가져오기 위해 JDBC 활용, WEB-INF의 lib에 ojdbc8.jar을 미리 복사해 두어야 함
		
		List<NoticeView> list = new ArrayList<>();
		
		String sql = "SELECT * FROM (" +
				"	 SELECT ROWNUM NUM, N.* " +
				"	 FROM (SELECT * FROM NOTICE_VIEW WHERE " + field + " LIKE ? ORDER BY REGDATE DESC) N" +
				") " +
				"WHERE NUM BETWEEN ? AND ?";
		
		// 1, 11, 21, 31, -> 1 + (page - 1) * 10
		// 10, 20, 30, 40, -> page * 10
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "272452"); // 빨간줄은 Ctrl+space를 통해 import
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%" + query + "%");
			st.setInt(2, 1 + (page - 1) * 10);
			st.setInt(3, page * 10);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) { // DB와 연동한 데이터 가져오기
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				// String content = rs.getString("CONTENT"); 해당 오라클 뷰에는 content 없음
				int cmtCount = rs.getInt("CMT_COUNT");
				
				NoticeView notice = new NoticeView( // NoticeView라는 entity에서 가져오기
						id,
						title,
						regdate,
						writerId,
						hit,
						files,
						// content
						cmtCount
					);
				list.add(notice);
			}
			
		    rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public int getNoticeCount() {
		
		return getNoticeCount("title", "");
		
	}
	
	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM (" +
				"	 SELECT ROWNUM NUM, N.* " +
				"	 FROM (SELECT * FROM NOTICE WHERE " + field + " LIKE ? ORDER BY REGDATE DESC) N" +
				") ";
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "272452");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%" + query + "%");
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("COUNT");
			}
			
		    rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "272452");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(
						nid,
						title,
						regdate,
						writerId,
						hit,
						files,
						content
					);
			}
			
		    rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return notice;
		
	}
	
    public Notice getNextNotice(int id) {
		
    	Notice notice = null;
    	
    	String sql = "SELECT * FROM NOTICE " +
    			"WHERE ID = ( " +
    			"	 SELECT ID FROM NOTICE " +
    			"	 WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=?) " +
    			"	 AND ROWNUM = 1 " +
    			") ";
    	
    	String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "272452");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(
						nid,
						title,
						regdate,
						writerId,
						hit,
						files,
						content
					);
			}
			
		    rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return notice;
    	
	}
    
    public Notice getPrevNotice(int id) {
		
    	Notice notice = null;
    	
    	String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) " +
				"WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?) " +
				"AND ROWNUM = 1";
    	
    	String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "newlec", "272452");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regdate = rs.getDate("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				notice = new Notice(
						nid,
						title,
						regdate,
						writerId,
						hit,
						files,
						content
					);
			}
			
		    rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return notice;
    	
	}
	
}
