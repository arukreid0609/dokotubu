package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.Mutter;

public class MuttersDAO extends DAO{
	// 全件取得
	public List<Mutter> findAll(){
		List<Mutter> list = new ArrayList<Mutter>();
		try {
			this.connect();
			stmt = con.prepareStatement("SELECT * FROM mutters");
			rs = stmt.executeQuery();
			while(rs.next()) {
				// レコードから各カラムのデータ取得
				int id = rs.getInt("id");
				String userName = rs.getString("userName");
				String text = rs.getString("mutter");
				
				// 取得したデータでインスタンス作成、リストに追加
				Mutter mutter = new Mutter(id,userName,text);
				list.add(mutter);
			}
		}catch(NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
		return list;
	}

	// データ１件追加
	public void insertOne(Mutter mutter) {
		try {
			this.connect();
			stmt = con.prepareStatement("INSERT INTO mutters(userName, text) VALUES(?,?)");
			stmt.setString(1, mutter.getUserName());
			stmt.setString(2, mutter.getText());
			stmt.execute();
		}catch(NamingException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.disconnect();
		}
	}
}
