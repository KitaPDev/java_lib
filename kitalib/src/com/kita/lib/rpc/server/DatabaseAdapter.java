package com.kita.lib.rpc.server;

import java.sql.*;

public class DatabaseAdapter {

    public Connection establishConnection(String p_DB_URL, String p_USER, String p_PASSWORD) {
        Connection conn = null;

        try {

            conn = DriverManager.getConnection(p_DB_URL, p_USER, p_PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    public void update(Connection conn, String p_SQLStatement) {
        try {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(p_SQLStatement);

        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(Connection conn, String p_SQLStatement) {
        ResultSet rs = null;

        try {
            Statement stmt = conn.createStatement();

            rs = stmt.executeQuery(p_SQLStatement);

        } catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return rs;
    }

}
