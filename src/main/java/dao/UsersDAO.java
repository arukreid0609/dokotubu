package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.User;

public class UsersDAO extends DAO{
	// 全件取得
	public List<User> findAll(){
		List<User> list = new ArrayList<User>();
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM users");
			rs = stmt.executeQuery();
			while(rs.next()) {
				// レコードから各カラムのデータ取得
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String pass = rs.getString("pass");
				
				// 取得したデータでインスタンス作成、リストに追加
				User user = new User(id,name,pass);
				list.add(user);
			}
		}catch(NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return list;
	}
	
	// データ１件追加
	public void insertOne(User user) {
		try {
			this.connect();
			stmt = con.prepareStatement("INSERT INTO users(name, pass) VALUES(?,?)");
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getPass());
			stmt.execute();
		}catch(NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
	}
	
	// １件取得
	public boolean isExist(String userName) {
		boolean result = false;
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM users WHERE name = ?");
			stmt.setString(1, userName);
			rs = stmt.executeQuery();

		}catch(NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return false;
	}
}
