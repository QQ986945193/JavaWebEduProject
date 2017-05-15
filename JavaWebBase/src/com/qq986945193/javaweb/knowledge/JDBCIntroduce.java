package com.qq986945193.javaweb.knowledge;

/**
 * @Author ：程序员小冰
 * @GitHub: https://github.com/QQ986945193
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.mysql.jdbc.Driver;
import com.qq986945193.javaweb.utils.JdbcUtils;

/**
 * jdbc的一些简单的功能介绍
 */
public class JDBCIntroduce {

	@Test
	public void fun1() throws ClassNotFoundException, SQLException, IOException {
		java.sql.Connection connection = JdbcUtils.getConnection();
		// java.sql.Connection connection = getConnection();
		fun1(connection);
		// System.out.println(JdbcUtils.getConnection());
	}

	private void fun1(Connection connection) throws SQLException {
		if (connection != null) {
			// 不等于空，则说明连接成功 Statement是用来向数据库发送要执行的SQL语句的！
			/*
			 * 二、对数据库做增、删、改 1. 通过Connection对象创建Statement >
			 * Statement语句的发送器，它的功能就是向数据库发送sql语句！ 2. 调用它的int
			 * executeUpdate(String sql)，它可以发送DML、DDL
			 */
			Statement createStatement = connection.createStatement();
			String sqlString = "insert into user values ('lisis',22)";
			/**
			 * 执行sql语句 发送增删改语句
			 * 
			 */
			// createStatement.executeUpdate(sqlString);
			/**
			 * 发送查询的sql语句
			 */
			String sqlSelctString = "select * from emp";
			// 得到查询结果，然后将查询结果的内容读取出来

			ResultSet executeQuery = createStatement.executeQuery(sqlSelctString);
			// while (executeQuery.next()) {
			// String username = executeQuery.getString("name");
			// String password = executeQuery.getString("age");
			// System.out.println(username + "," + password);
			// }

			/*
			 * 三、解析ResultSet 1. 把行光标移动到第一行，可以调用next()方法完成！
			 */
			while (executeQuery.next()) {// 把光标向下移动一行，并判断下一行是否存在！
				int empno = executeQuery.getInt(1);// 通过列编号来获取该列的值！
				String ename = executeQuery.getString("ename");// 通过列名称来获取该列的值
				double sal = executeQuery.getDouble("sal");
				System.out.println(empno + ", " + ename + ", " + sal);
			}
			executeQuery.close();
			createStatement.close();
			connection.close();
		}

	}

	/**
	 * 加载驱动并进行获取连接 /** ClassNotFoundException找不到类 可能没导入驱动包
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *             检查是否开启了mysql服务器以及用户名密码是否正确
	 */
	private java.sql.Connection getConnection() throws ClassNotFoundException, SQLException {
		/*
		 * jdbc四大配置参数： > driverClassName：com.mysql.j dbc.Driver >
		 * url：jdbc:mysql://localhost:3306/db3 > username：root > password：123
		 */
		/*
		 * 所有的java.sql.Driver实现类，都提供了static块，块内的代码就是把自己注册到 DriverManager中！
		 */
		/*
		 * jdbc4.0之后，每个驱动jar包中，在META-INF/services目录下提供了一个名为java.sql.Driver的文件。
		 * 文件的内容就是该接口的实现类名称！
		 */
		Class.forName("com.mysql.jdbc.Driver");
		// com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
		// DriverManager.registerDriver(driver);
		// 使用url、username、password，得到连接对象
		java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db3", "root", "1234");
		System.out.println(connection);
		return connection;
	}

	// 规范化 执行 查询操作
	@Test
	public void fun2() throws Exception {
		Connection con = null;// 定义引用
		Statement stmt = null;
		ResultSet rs = null;
		try {
			/*
			 * 一、得到连接
			 */
			con = JdbcUtils.getConnection();// 实例化

			/*
			 * 二、创建Statement
			 */
			stmt = con.createStatement();
			String sql = "select * from emp";
			// 执行查询操作
			rs = stmt.executeQuery(sql);

			rs.last();// 把光标移动到最后一行
			System.out.println(rs.getRow());
			rs.beforeFirst();

			/*
			 * 三、循环遍历rs，打印其中数据
			 * 
			 * getString()和getObject()是通用的！
			 */
			// while(rs.next()) {
			// System.out.println(rs.getObject(1) + ", "
			// + rs.getString("ename") + ", " + rs.getDouble("sal"));
			// }
			// 得到行数
			int count = rs.getMetaData().getColumnCount();
			while (rs.next()) {// 遍历行
				// 得到ename列的所有数据
				// System.out.println(rs.getString("ename"));
				for (int i = 1; i <= count; i++) {// 遍历列 从1开始
					System.out.print(rs.getString(i));
					if (i < count) {
						System.out.print(", ");
					}
				}
				System.out.println();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// 关闭
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}

	}
	
	
	
}
