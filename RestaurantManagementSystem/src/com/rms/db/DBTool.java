package com.rms.db;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.rms.model.User;
import com.rms.util.StringUtil;

public class DBTool {

	public static Logger logger = Logger.getLogger(DBTool.class);
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://127.0.0.1:3306/debuggroup?useUnicode=true&characterEncoding=UTF-8";
	public static String user = "tzhang";
	public static String pwd = "tzhang";
	public static boolean isinit = false;
	public Coon coon=new Coon();




	
	
	
	public ArrayList<HashMap<String, String>> GetCatagoryByType(String type) throws Exception
	{
		
		String sql =String.format("select title from typecatagory where type='%s' order by title", type);
		return DBSelect(sql);
		
	}
	public ArrayList<HashMap<String, String>> GetFreeTable() throws Exception
	{
		
		String sql =String.format("select id as dining_table_id,table_num from dining_table where status='free' order by table_num");
		//String sql =String.format("select id as dining_table_id,table_num from dining_table order by table_num");
		return DBSelect(sql);
		
	}

	public ArrayList<HashMap<String, String>> GetMenuInfo() throws Exception
	{
		
		String sql =String.format("select * from menu");
		return DBSelect(sql);
		
	}
	
	public ArrayList<HashMap<String, String>> GetTitle(String tablename) throws Exception
	{
		
		String sql =String.format("select id,title from "+tablename+" ");
		return DBSelect(sql);
		
	}

	/**
	 * 鑾峰彇鍙傛暟鍒楄〃
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap GetParamter(String id) throws Exception {
		String sql = "select * from param where id = '" + id + "'";
		ArrayList data = DBSelect(sql);
		return data.size() > 0 ? (HashMap) data.get(0) : null;
	}

	public HashMap GetCatagory(String id) throws Exception {
		String sql = "select * from catagory where id = '" + id + "'";
		ArrayList data = DBSelect(sql);
		return data.size() > 0 ? (HashMap) data.get(0) : null;
	}

	public HashMap SearchParam(String key, int index, int size,
			String sortField, String sortOrder) throws Exception {

		if (key == null)
			key = "";

		String sql = "select  * \n" + "from param\n"
				+ "where paramname like '%" + key + "%' \n";

		if (StringUtil.isNullOrEmpty(sortField) == false) {
			if ("desc".equals(sortOrder) == false)
				sortOrder = "asc";
			sql += " order by " + sortField + " " + sortOrder;
		} else {
			sql += " order by updatetm desc";
		}

		ArrayList dataAll = DBSelect(sql);

		ArrayList data = new ArrayList();
		int start = index * size, end = start + size;

		for (int i = 0, l = dataAll.size(); i < l; i++) {
			HashMap record = (HashMap) dataAll.get(i);
			if (record == null)
				continue;
			if (start <= i && i < end) {
				data.add(record);
			}
			// record.put("createtime", new Timestamp(100,10,10,1,1,1,1));
		}

		HashMap result = new HashMap();
		result.put("data", data);
		result.put("total", dataAll.size());

		return result;
	}

	
	
	
	public HashMap SearchCatagory(String key, int index, int size,
			String sortField, String sortOrder) throws Exception {
		// System.Threading.Thread.Sleep(300);
		if (key == null)
			key = "";

		String sql = "select  * \n" + "from catagory\n" + "where title like '%"
				+ key + "%' \n";

		if (StringUtil.isNullOrEmpty(sortField) == false) {
			if ("desc".equals(sortOrder) == false)
				sortOrder = "asc";
			sql += " order by " + sortField + " " + sortOrder;
		} else {
			sql += " order by updatetm desc";
		}

		ArrayList dataAll = DBSelect(sql);

		ArrayList data = new ArrayList();
		int start = index * size, end = start + size;

		for (int i = 0, l = dataAll.size(); i < l; i++) {
			HashMap record = (HashMap) dataAll.get(i);
			if (record == null)
				continue;
			if (start <= i && i < end) {
				data.add(record);
			}
			// record.put("createtime", new Timestamp(100,10,10,1,1,1,1));
		}

		HashMap result = new HashMap();
		result.put("data", data);
		result.put("total", dataAll.size());

		return result;
	}

	public String insertCatagory(HashMap catagory) {
		String id = (catagory.get("id") == null || catagory.get("id")
				.toString().equals("")) ? UUID.randomUUID().toString()
				: catagory.get("id").toString();
		catagory.put("id", id);

		String sql = String.format(
				"insert into catagory (id, type,title, username, updatetm)"
						+ " values('%s','%s', '%s', '%s', now())",
				catagory.get("id"),catagory.get("type"), catagory.get("title"),
				catagory.get("username"));

		Statement stmt = getStatement();
		try {
			stmt.execute(sql);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}

	public String insertParam(HashMap param) {
		String id = (param.get("id") == null || param.get("id").toString()
				.equals("")) ? UUID.randomUUID().toString() : param.get("id")
				.toString();
				param.put("id", id);

		String sql = String
				.format("insert into param (id, stand_no,class1,class2,class3, paramname, paramcnname,meaning," +
						"checkmethod,valuetype,initvalue,recommendationvalue,recommendation,note,checkmethodids,username,updatetm)"
						+ " values('%s', '%s','%s', '%s', '%s', '%s', '%s', '%s','%s', '%s', '%s', '%s', '%s', '%s','%s', '%s', now())",
						id, param.get("stand_no"), param.get("class1"),
						param.get("class2"), param.get("class3"),
						param.get("paramname"), param.get("paramcnname"),
						param.get("meaning"), param.get("checkmethod"),
						param.get("valuetype"), param.get("initvalue"),
						param.get("recommendationvalue"),
						param.get("recommendation"), param.get("note"),
						param.get("checkmethodids"), param.get("username"));

		logger.debug(sql);
		Statement stmt = getStatement();
		try {
			stmt.execute(sql);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}



	
	

	
	
	public void deleteParam(String id) {

		try {
			DBDelete("delete from param where id='" + id + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
	}

	public void deleteCatagory(String cid) {

		try {
			DBDelete("delete from catagory where id='" + cid + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
	}

	


	public void deleteTable(String id) {

		try {
			DBDelete("delete from dining_table where id='" + id + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
	}
	public String insertTable(HashMap table) {
		String id = (table.get("id") == null || table.get("id")
				.toString().equals("")) ? UUID.randomUUID().toString()
				: table.get("id").toString();
				table.put("id", id);

		String sql = String.format(
				"insert into dining_table (id, table_num,location, status, size)"
						+ " values('%s','%s', '%s', '%s','%s')",
						table.get("id"),table.get("table_num"), table.get("location"),
						table.get("status"),table.get("size"));

		Statement stmt = getStatement();
		try {
			stmt.execute(sql);
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}
	public HashMap SearchTable(String key, int index, int size,
			String sortField, String sortOrder) throws Exception {
		// System.Threading.Thread.Sleep(300);
		if (key == null)
			key = "";

		String sql = "select * from dining_table";

		if (StringUtil.isNullOrEmpty(sortField) == false) {
			if ("desc".equals(sortOrder) == false)
				sortOrder = "asc";
			sql += " order by " + sortField + " " + sortOrder;
		} else {
			sql += " order by table_num desc";
		}

		ArrayList dataAll = DBSelect(sql);

		ArrayList data = new ArrayList();
		int start = index * size, end = start + size;

		for (int i = 0, l = dataAll.size(); i < l; i++) {
			HashMap record = (HashMap) dataAll.get(i);
			if (record == null)
				continue;
			if (start <= i && i < end) {
				data.add(record);
			}
			
		}

		HashMap result = new HashMap();
		result.put("data", data);
		result.put("total", dataAll.size());

		return result;
	}	
	
	public HashMap GetTable(String id) throws Exception {
		String sql = "select * from dining_table where id = '" + id + "'";
		ArrayList data = DBSelect(sql);
		return data.size() > 0 ? (HashMap) data.get(0) : null;
	}
	public String updateCatagory(HashMap catagory) {
		String id = catagory.get("id").toString();

		String sql = String
				.format(" update catagory set type='%s',title='%s',username='%s',updatetm=now() where id='%s'",
						catagory.get("type"),catagory.get("title"), catagory.get("username"), id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}

	public String updateProblem(HashMap catagory) {
		String id = catagory.get("id").toString();

		String sql = String
				.format(" update problem set ptitle='%s',pcontent='%s',testappearance='%s',checkmethods='%s',solutionmethods='%s',username='%s',updatetm=now() where id='%s'",
						catagory.get("ptitle"), catagory.get("pcontent"),catagory.get("testappearance"),
						catagory.get("checkmethods"),
						catagory.get("solutionmethods"),
						catagory.get("username"), id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	
	public String updateTestappearance(HashMap testappearance) {
		String id = testappearance.get("id").toString();

		String sql = String
				.format(" update testappearance set title='%s',content='%s',tag='%s',type='%s',username='%s',updatetm=now() where id='%s'",
						testappearance.get("title"), testappearance.get("content"),
						testappearance.get("tag"),
						testappearance.get("type"),
						testappearance.get("username"), id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	
	public String updateParam(HashMap param) {
		String id = param.get("id").toString();

		String sql = String
				.format(" update param set stand_no='%s', class1='%s', class2='%s', class3='%s', paramname='%s', paramcnname='%s', meaning='%s', checkmethod='%s', valuetype='%s', initvalue='%s', recommendationvalue='%s', recommendation='%s', note='%s', checkmethodids='%s', username='%s',updatetm=now() where id='%s'",
						param.get("stand_no"), param.get("class1"),
						param.get("class2"), param.get("class3"),
						param.get("paramname"), param.get("paramcnname"),
						param.get("meaning"), param.get("checkmethod"),
						param.get("valuetype"), param.get("initvalue"),
						param.get("recommendationvalue"),
						param.get("recommendation"), param.get("note"),
						param.get("checkmethodids"), param.get("username"), id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}

	public String updateCheckmethod(HashMap row) {
		String id = row.get("id").toString();

		String sql = String
				.format(" update checkmethod set title='%s', content='%s', username='%s', attachments='%s', inputparams='%s', outputparams='%s',  updatetm=now() where id='%s'",
					 row.get("title"), row.get("content"), row.get("username"), row.get("attachments"), row.get("inputparams"), row.get("outputparams"),  id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	
	public String updateTable(HashMap table) {
		String id = table.get("id").toString();

		String sql = String
				.format(" update dining_table set table_num='%s',location='%s',status='%s',size='%S' where id='%s'",
						table.get("table_num"),table.get("location"), table.get("status"), table.get("size") ,id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	public Statement getStatement() {
		Statement statement = null;

			try {
				Connection conn = coon.getCoon();
				statement = conn.createStatement();
			}  catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.toString());
			}
		

		return statement;
	}

	public void DBDelete(String sql) throws Exception {

		Statement stmt = getStatement();
		stmt.executeUpdate(sql);
		stmt.close();

	}

	public void DBUpdate(String sql) throws Exception {

		Statement stmt = getStatement();
		stmt.executeUpdate(sql);
		stmt.close();

	}

	public ArrayList<HashMap<String, String>> DBSelect(String sql)
			throws Exception {
		Connection conn = coon.getCoon();
		Statement stmt = getStatement();
        logger.debug(sql);
		ResultSet rst = stmt.executeQuery(sql);
		ArrayList<HashMap<String, String>> list = ResultSetToList(rst);

		rst.close();
		stmt.close();
		conn.close();

		return list;
	}

	private static ArrayList<HashMap<String, String>> ResultSetToList(
			ResultSet rs) throws Exception {
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> rowData;
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				Object v = rs.getObject(i);

				if (v != null
						&& (v.getClass() == Date.class || v.getClass() == java.sql.Date.class)) {
					Timestamp ts = rs.getTimestamp(i);
					v = new java.util.Date(ts.getTime());
					// v = ts;
				} else if (v != null && v.getClass() == Clob.class) {
					v = clob2String((Clob) v);
				}
				// System.out.println(v.toString());
				if (v == null) {
					rowData.put(md.getColumnName(i), "");
				} else {
					rowData.put(md.getColumnName(i), v.toString());
				}
			}
			list.add(rowData);
		}
		return list;
	}

	private static String clob2String(Clob clob) throws Exception {
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	}

	private int ToInt(Object o) {
		if (o == null)
			return 0;
		double d = Double.parseDouble(o.toString());
		int i = 0;
		i -= d;
		return -i;
	}

	private String ToString(Object o) {
		if (o == null)
			return "";
		return o.toString();
	}

	private Timestamp ToDate(Object o) {
		try {
			if (o.getClass() == String.class) {

				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				o = format.parse(o.toString());
				return new java.sql.Timestamp(((Date) o).getTime());
			}
			return o != null ? new java.sql.Timestamp(((Date) o).getTime())
					: null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	



//////processenvproblem/////////////////////
	
	public void deleteProcessenvproblem(String id) {

		try {
			DBDelete("delete from processenvproblem where id='" + id + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
	}

	

	//===user
	

public void deleteUser(String id) {

		try {
			DBDelete("delete from user where id='" + id + "'");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}
	}

public void deleteOrder(String id) {

	try {
		DBDelete("delete from `order` where id='" + id + "'");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}
public void deleteDatabasebackup(String id) {

	try {
		DBDelete("delete from `database_backup_info` where id='" + id + "'");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}

public void recoveryDatabasebackup(String source_tablename,String backup_tablename) {

	try {
		//DBDelete("delete from `database_backup_info` where id='" + id + "'");
		DBUpdate("truncate table  `"+source_tablename+"`");
		String sql ="insert into `"+source_tablename+"`  select * from `"+backup_tablename+"`";
		System.out.println(sql);
		DBUpdate(sql);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}
public void deleteOrderDetail(String id) {

	try {
		DBDelete("delete from `order_detail` where id='" + id + "'");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}
public void deleteMenucategory(String id) {

	try {
		DBDelete("delete from menu_category where id='" + id + "'");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}

public void deleteMenu(String id) {

	try {
		DBDelete("delete from menu where id='" + id + "'");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}
}
public HashMap GetUser(String id) throws Exception {
		String sql = "select * from user where id = '" + id + "'";
		ArrayList data = DBSelect(sql);
		return data.size() > 0 ? (HashMap) data.get(0) : null;
	}
public HashMap GetOrder(String id) throws Exception {
	String updatesql="update  `order`  set total_amount=( select sum(t.pr*t.nu)  from   ( select menu.price pr,order_detail.num nu    from order_detail left join menu    on menu.id=order_detail.menu_id where order_detail.order_id='"+id+"') as t) where id='"+id+"' ";
	System.out.println(updatesql);		
	DBUpdate(updatesql);
	String sql = "select  `order`.id ,dining_table.table_num,`order`.dining_table_id,`order`.user_id,user.name as name, `order`.total_amount , `order`.pay_amount, `order`.change,`order`.order_status,`order`.date \n" + " from `order`, dining_table, user  \n" + " where    `order`.user_id=user.id and `order`.dining_table_id=dining_table.id and  `order`.id = '" + id + "'";
	System.out.println(sql);
	ArrayList data = DBSelect(sql);
	return data.size() > 0 ? (HashMap) data.get(0) : null;
}

public HashMap GetOrderDetail(String id) throws Exception {
	
	String sql = "select  id,user_id,order_id,user.name as username,menu.name as menuname,num,`order_detail`.date" + " from `order_detail`, menu, user  \n" + " where    `order_detail`.user_id=user.id and `order_detail`.menu_id=menu.id and  `order_detail`.id = '" + id + "'";
	System.out.println(sql);
	ArrayList data = DBSelect(sql);
	return data.size() > 0 ? (HashMap) data.get(0) : null;
}

public User getUserInfomation(String temp_username,String temp_password,String age,String contract_num,String role,String updatetm,String id){
	User usr = new User();
	usr.setUsername(temp_username);
	usr.setPassword(temp_password);
	usr.setAge(Integer.parseInt(age));
	usr.setContract_num(contract_num);
	usr.setRole(role);
	usr.setUpdatetm(updatetm);
	usr.setId(id);
	return usr;
}
public HashMap GetMenuCategory(String id) throws Exception {
	String sql = "select * from menu_category where id = '" + id + "'";
	ArrayList data = DBSelect(sql);
	return data.size() > 0 ? (HashMap) data.get(0) : null;
}
public HashMap GetMenu(String id) throws Exception {
	String sql = "select * from menu where id = '" + id + "'";
	ArrayList data = DBSelect(sql);
	return data.size() > 0 ? (HashMap) data.get(0) : null;
}

public String insertUser(HashMap row) {
	String id = (row.get("id") == null || row.get("id")
			.toString().equals("")) ? UUID.randomUUID().toString()
			: row.get("id").toString();
	row.put("id", id);

		String sql = String.format(
				"insert into user (id, name, password, age, contract_num, role, updatetm)"
						+ " values(  '%s', '%s', '%s', '%s', '%s', '%s',  now())",
						 row.get("id"), row.get("name"), row.get("password"), row.get("age"), row.get("contract_num"), row.get("role"));
		Statement stmt = getStatement();
		
		try {
			stmt.execute(sql);
			
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}

public String insertOrder(HashMap row) {
	String id = (row.get("id") == null || row.get("id")
			.toString().equals("")) ? UUID.randomUUID().toString()
			: row.get("id").toString();
	row.put("id", id);

		String sql = String.format(
				"insert into `order` (id, user_id, dining_table_id, order_status, date)"
						+ " values(  '%s', '%s', '%s', '%s', '%s')",
						 row.get("id"), row.get("user_id"), row.get("dining_table_id"), row.get("order_status"), row.get("date"));
		Statement stmt = getStatement();
		
		try {
			stmt.execute(sql);
			
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}

public String insertDatabasebackup(HashMap row) {
	String id = (row.get("id") == null || row.get("id")
			.toString().equals("")) ? UUID.randomUUID().toString()
			: row.get("id").toString();
	row.put("id", id);

	
		
	
		try {
			Statement stmt = getStatement();
			String sql ="create table `"+row.get("backup_tablename")+"` like `"+ row.get("source_tablename")+"`";
			System.out.println(sql);
			stmt.execute(sql);
			
			sql ="insert into `"+row.get("backup_tablename")+"`  select * from `"+ row.get("source_tablename")+"`";
			
			System.out.println(sql);
			stmt.execute(sql);
					 sql = String.format(
							"insert into `database_backup_info` (id, source_tablename, backup_tablename, updatetm)"
									+ " values(  '%s', '%s', '%s',now())",
									 row.get("id"), row.get("source_tablename"), row.get("backup_tablename"));
				
					System.out.println(sql);
			stmt.execute(sql);
			
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}
public String insertOrderDetail(HashMap row) {
	String id = (row.get("id") == null || row.get("id")
			.toString().equals("")) ? UUID.randomUUID().toString()
			: row.get("id").toString();
	row.put("id", id);

		String sql = String.format(
				"insert into `order_detail` (id, user_id, menu_id, num,order_id, date)"
						+ " values(  '%s', '%s', '%s', '%s', '%s', '%s')",
						 row.get("id"), row.get("user_id"), row.get("menu_id"), row.get("num"), row.get("order_id"), row.get("date"));
		Statement stmt = getStatement();
		
		try {
			stmt.execute(sql);
			
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}
public String insertMenuCategory(HashMap row) {
	String id = (row.get("id") == null || row.get("id")
			.toString().equals("")) ? UUID.randomUUID().toString()
			: row.get("id").toString();
	row.put("id", id);

		String sql = String.format(
				"insert into menu_category (id, name)"
						+ " values(  '%s', '%s')",
						 row.get("id"), row.get("name"));
		Statement stmt = getStatement();
		
		try {
			stmt.execute(sql);
			
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}
public String insertMenu(HashMap row) {
	String id = (row.get("id") == null || row.get("id")
			.toString().equals("")) ? UUID.randomUUID().toString()
			: row.get("id").toString();
	row.put("id", id);

	String sql = String.format(
	"insert into menu (id, name, price, stock, code, category_id, abbreviation)"
			+ " values( '%s', '%s', '%s', '%s', '%s', '%s',  '%s')",
			 row.get("id"), row.get("name"), row.get("price"), row.get("stock"), 
			 row.get("code"), row.get("category_id"),
			 row.get("abbreviation"));
		Statement stmt = getStatement();
		try {
			stmt.execute(sql);
			
		} catch (Exception ex) {
			logger.error(ex);
		}

		return id;

	}
public HashMap SearchUser(String key, int index, int size,
			String sortField, String sortOrder) throws Exception {
		// System.Threading.Thread.Sleep(300);
		if (key == null)
			key = "";

		String sql = "select  * \n" + "from user\n" + "where name like '%"
				+ key + "%' \n";

		if (StringUtil.isNullOrEmpty(sortField) == false) {
			if ("desc".equals(sortOrder) == false)
				sortOrder = "asc";
			sql += " order by " + sortField + " " + sortOrder;
		} else {
			sql += " order by name desc";
		}

		ArrayList dataAll = DBSelect(sql);

		ArrayList data = new ArrayList();
		int start = index * size, end = start + size;

		for (int i = 0, l = dataAll.size(); i < l; i++) {
			HashMap record = (HashMap) dataAll.get(i);
			if (record == null)
				continue;
			if (start <= i && i < end) {
				data.add(record);
			}
			
		}

		HashMap result = new HashMap();
		result.put("data", data);
		result.put("total", dataAll.size());

		return result;
	}	
public String updateMenu(HashMap table) {
	String id = table.get("id").toString();

	String sql = String
			.format(" update menu set name='%s',price='%s',stock='%s',code='%S' , category_id='%S', abbreviation='S%' where id='%s'",
					table.get("name"),table.get("price"), table.get("stock"), table.get("code") ,table.get("category_id"),table.get("abbreviation"),id);

	try {
		DBUpdate(sql);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.error(e);
	}

	return id;

}
public HashMap SearchOrder(String key, int index, int size,
		String sortField, String sortOrder) throws Exception {
	// System.Threading.Thread.Sleep(300);
	if (key == null)
		key = "";

	//String sql = "select  *  \n" + " from `order`, dining_table, user  \n" + " where    order.user_id=user.id and `order`.dining_table_id=dining_table.id \n";
	String sql = "select  `order`.id ,dining_table.table_num,user.name, `order`.total_amount ,`order`.order_status,`order`.date  \n" + " from `order`, dining_table, user  \n" + " where    order.user_id=user.id and `order`.dining_table_id=dining_table.id \n";
	System.out.println(sql);
	if (StringUtil.isNullOrEmpty(sortField) == false) {
		if ("desc".equals(sortOrder) == false)
			sortOrder = "asc";
		sql += " order by " + sortField + " " + sortOrder;
	} else {
		sql += " order by dinning_table. table_num desc";
	}

	ArrayList dataAll = DBSelect(sql);

	ArrayList data = new ArrayList();
	int start = index * size, end = start + size;

	for (int i = 0, l = dataAll.size(); i < l; i++) {
		HashMap record = (HashMap) dataAll.get(i);
		if (record == null)
			continue;
		if (start <= i && i < end) {
			data.add(record);
		}
		
	}

	HashMap result = new HashMap();
	result.put("data", data);
	result.put("total", dataAll.size());

	return result;
}
public HashMap SearchDatabaseBackupinfo(String key, int index, int size,
		String sortField, String sortOrder) throws Exception {
	// System.Threading.Thread.Sleep(300);
	if (key == null)
		key = "";

	//String sql = "select  *  \n" + " from `order`, dining_table, user  \n" + " where    order.user_id=user.id and `order`.dining_table_id=dining_table.id \n";
	String sql = "select  *  \n" + " from  database_backup_info \n" + " where   source_tablename like '%"+key+"%'\n";
	System.out.println(sql);
	if (StringUtil.isNullOrEmpty(sortField) == false) {
		if ("desc".equals(sortOrder) == false)
			sortOrder = "asc";
		sql += " order by " + sortField + " " + sortOrder;
	} else {
		sql += " order by source_tablename desc";
	}

	ArrayList dataAll = DBSelect(sql);

	ArrayList data = new ArrayList();
	int start = index * size, end = start + size;

	for (int i = 0, l = dataAll.size(); i < l; i++) {
		HashMap record = (HashMap) dataAll.get(i);
		if (record == null)
			continue;
		if (start <= i && i < end) {
			data.add(record);
		}
		
	}

	HashMap result = new HashMap();
	result.put("data", data);
	result.put("total", dataAll.size());

	return result;
}
public HashMap SearchOrderDetail(String order_id,String key, int index, int size,
		String sortField, String sortOrder) throws Exception {
	// System.Threading.Thread.Sleep(300);
	if (key == null)
		key = "";

	//String sql = "select  *  \n" + " from `order`, dining_table, user  \n" + " where    order.user_id=user.id and `order`.dining_table_id=dining_table.id \n";
	String sql = "select order_detail.id,`user`.name as username,menu.name as menuname,num,order_detail.date " + " from `order_detail`, menu, user  \n" + " where    `order_detail`.user_id=user.id and `order_detail`.menu_id=menu.id and order_detail.order_id='"+order_id+"' ";

/*	if (StringUtil.isNullOrEmpty(sortField) == false) {
		if ("desc".equals(sortOrder) == false)
			sortOrder = "asc";
		sql += " order by " + sortField + " " + sortOrder;
	} else {
		sql += " order by order_detail. num desc";
	}*/
	System.out.println(sql);
	ArrayList dataAll = DBSelect(sql);

	ArrayList data = new ArrayList();
	int start = index * size, end = start + size;

	for (int i = 0, l = dataAll.size(); i < l; i++) {
		HashMap record = (HashMap) dataAll.get(i);
		if (record == null)
			continue;
		if (start <= i && i < end) {
			data.add(record);
		}
		
	}

	HashMap result = new HashMap();
	result.put("data", data);
	result.put("total", dataAll.size());

	return result;
}
public HashMap SearchMenuCatagory(String key, int index, int size,
		String sortField, String sortOrder) throws Exception {
	// System.Threading.Thread.Sleep(300);
	if (key == null)
		key = "";

	String sql = "select  * \n" + "from menu_category\n" + "where name like '%"
			+ key + "%' \n";

	if (StringUtil.isNullOrEmpty(sortField) == false) {
		if ("desc".equals(sortOrder) == false)
			sortOrder = "asc";
		sql += " order by " + sortField + " " + sortOrder;
	} else {
		sql += " order by name desc";
	}

	ArrayList dataAll = DBSelect(sql);

	ArrayList data = new ArrayList();
	int start = index * size, end = start + size;

	for (int i = 0, l = dataAll.size(); i < l; i++) {
		HashMap record = (HashMap) dataAll.get(i);
		if (record == null)
			continue;
		if (start <= i && i < end) {
			data.add(record);
		}
		
	}

	HashMap result = new HashMap();
	result.put("data", data);
	result.put("total", dataAll.size());

	return result;
}	

public String checkUser(String username,String password,HttpServletRequest req){

    boolean has_username = false;

    boolean password_correct = false;

    ResultSet rs = null;

    try {
    	Statement stmt = getStatement();
        rs = stmt.executeQuery("select * from user");

    } catch (SQLException e) {

        System.err.println("查询数据库出错");

        e.printStackTrace();

        return null;

    }

    try {

        while(rs.next()){

        	  String temp_username = rs.getString("name").trim();
        	  
        	  String id = rs.getString("id").trim();

              String temp_password = rs.getString("password").trim();
              
              String age = rs.getString("age");
              
              String contract_num = rs.getString("contract_num");
              
              String role = rs.getString("role");
              
              String updatetm = rs.getString("updatetm");

              if(username.equals(temp_username)){

                  has_username = true;

                  if(password.equals(temp_password)){

                      password_correct = true;
                      
                      User userInfomation = getUserInfomation(temp_username,temp_password,age,contract_num,role,updatetm,id);
                      req.setAttribute("userInfomation", userInfomation);
                      
                      return "User name and password are correct";

                  }

                  return "The username is correct and the password is incorrect";

            }

        }

    } catch (SQLException e) {

        System.err.println("操作ResultSet出错");

        e.printStackTrace();

    }

    return "Without this user！";

}
public HashMap SearchMenu(String key, int index, int size,
		String sortField, String sortOrder) throws Exception {
	// System.Threading.Thread.Sleep(300);
	if (key == null)
		key = "";

	String sql = "select  * \n" + "from menu\n" + "where name like '%"
			+ key + "%' \n";

	if (StringUtil.isNullOrEmpty(sortField) == false) {
		if ("desc".equals(sortOrder) == false)
			sortOrder = "asc";
		sql += " order by " + sortField + " " + sortOrder;
	} else {
		sql += " order by name desc";
	}

	ArrayList dataAll = DBSelect(sql);

	ArrayList data = new ArrayList();
	int start = index * size, end = start + size;

	for (int i = 0, l = dataAll.size(); i < l; i++) {
		HashMap record = (HashMap) dataAll.get(i);
		if (record == null)
			continue;
		if (start <= i && i < end) {
			data.add(record);
		}
		
	}

	HashMap result = new HashMap();
	result.put("data", data);
	result.put("total", dataAll.size());

	return result;
}	
	public String updateUser(HashMap row) {
		String id = row.get("id").toString();

		String sql = String
				.format(" update user set title='%s', password='%s', email='%s', gender='%s', credit2='%s', credit1='%s', image='%s', regip='%s', regtime='%s', lastip='%s', lasttime='%s',  updatetm=now() where id='%s'",
					 row.get("title"), row.get("password"), row.get("email"), row.get("gender"), row.get("credit2"), row.get("credit1"), row.get("image"), row.get("regip"), row.get("regtime"), row.get("lastip"), row.get("lasttime"),  id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	public String updateOrder(HashMap row) {
		String id = row.get("id").toString();

		String sql = String.format(" update `order` set user_id='%s', dining_table_id='%s', order_status='%s', total_amount='%s', date='%s' , pay_amount='%s'  , `change`='%s'  where  id='%s'",
					 row.get("user_id"), row.get("dining_table_id"), row.get("order_status"), row.get("total_amount"), row.get("date"), row.get("pay_amount"), row.get("change"),id);
      System.out.println(sql);
		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	public String updateOrderDetail(HashMap row) {
		String id = row.get("id").toString();

		String sql = String.format(" update `order_detail` set user_id='%s', menu_id='%s', num='%s', date='%s'  where  id='%s'",
					 row.get("user_id"), row.get("menu_id"), row.get("num"), row.get("date"),id);
      System.out.println(sql);
		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	public String updateMenuCategory(HashMap row) {
		String id = row.get("id").toString();

		String sql = String
				.format(" update menu_category set name='%s' where id='%s'",
					row.get("name"),  id);

		try {
			DBUpdate(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
		}

		return id;

	}
	
	///===solutionmethod
	




	



	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
