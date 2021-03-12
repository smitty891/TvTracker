package com.tvtracker.enterprise.dao;

import com.tvtracker.enterprise.EnterpriseApplication;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Parent DAO class containing all functionality for database communication
 */
public class BaseDAO {

    private String tableName;

    private StringBuffer whereCondition;

    private HashMap<String, Object> updatedColumns;

    private HashMap<String, Object> insertValues;


    /**
     * Method for getting a connection to the database
     *
     * @return Connection to database
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Properties properties = new Properties();
            properties.load(EnterpriseApplication.class.getClassLoader().getResourceAsStream("application.properties"));

            return DriverManager.getConnection(properties.getProperty("url"), properties);
    }

    /**
     * Method for Subclasses to set the table name for their corresponding table
     *
     * @param tableName Name of the database table
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * This is a method for setting constraints on sql statements
     *
     * @param column column name
     * @param value String value required in the column
     */
    public void addWhere(String column, String value) {
        if(column == null || value == null) {
            return;
        }

        if(this.whereCondition == null) {
            this.whereCondition = new StringBuffer();
            this.whereCondition.append(" WHERE ");
        } else {
            this.whereCondition.append(" AND ");
        }

        this.whereCondition.append(column).append(" = '").append(value).append("'");
    }

    /**
     * This is a method for setting constraints on sql statements
     *
     * @param column column name
     * @param value integer value required in the column
     */
    public void addWhere(String column, int value) {
        if(column == null) {
            return;
        }

        if(this.whereCondition == null) {
            this.whereCondition = new StringBuffer();
            this.whereCondition.append(" WHERE ");
        } else {
            this.whereCondition.append(" AND ");
        }

        this.whereCondition.append(column).append(" = ").append(value);
    }

    /**
     * This method is used for running select statements against the database
     *
     * @return ArrayList containing key value pairs
     */
    public ArrayList<HashMap<String, Object>> select() {
        ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();

        Connection conn = null;
        Statement statement = null;

        try {
            StringBuffer sql = new StringBuffer();

            sql.append("SELECT * FROM ").append(tableName);
            if(whereCondition != null) {
                sql.append(whereCondition);
            }

            conn = getConnection();
            statement = conn.createStatement();
            statement.execute(sql.toString());

            results = getResultList(statement.getResultSet());

            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<HashMap<String, Object>>();
        } finally {
            whereCondition = null;

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method is for setting columns on the table to be updated
     *
     * @param column name of column to be updated
     * @param value value to be populated into the given column
     */
    public void updateColumn(String column, Object value) {
        if(updatedColumns == null)
            updatedColumns = new HashMap<>();

        updatedColumns.put(column, value);
    }

    /**
     * This method is used for running update statements against the database
     *
     * @return boolean indicating whether update was successful
     */
    public boolean update() {
        Connection conn = null;
        Statement statement = null;

        try {
            StringBuffer sql = new StringBuffer();

            sql.append("UPDATE ").append(tableName).append(" SET ");

            int index = 0;
            for(Map.Entry entry: updatedColumns.entrySet()) {
                index++;
                String column = (String) entry.getKey();
                Object value = entry.getValue();

                sql.append(column).append(" = ");

                if(value instanceof String || value instanceof Timestamp) {
                    sql.append("'").append(value.toString()).append("'");
                } else {
                    sql.append(value);
                }

                if(index < updatedColumns.size()) {
                    sql.append(", ");
                }
            }

            if(whereCondition != null) {
                sql.append(whereCondition);
            }

            conn = getConnection();
            statement = conn.createStatement();
            int numOfUpdates = statement.executeUpdate(sql.toString());

            return numOfUpdates > 0;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            whereCondition = null;
            updatedColumns = null;

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is for setting columns values for an insert statement
     *
     * @param column name of column
     * @param value value to be populated into the given column
     */
    public void setInsertValue(String column, Object value) {
        if(insertValues == null)
            insertValues = new HashMap<>();

        insertValues.put(column, value);
    }

    /**
     * This method is used for running insert statements against the database
     *
     * @return boolean indicating whether insert was successful
     */
    public boolean insert() {
        Connection conn = null;
        Statement statement = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO ").append(tableName).append("(");

            int colIndex = 0;
            for(Map.Entry entry: insertValues.entrySet()) {
                colIndex++;
                sql.append(entry.getKey().toString());

                if(colIndex < insertValues.entrySet().size()) {
                    sql.append(",");
                }
            }
            sql.append(") VALUES (");
            int valIndex = 0;
            for(Map.Entry entry: insertValues.entrySet()) {
                valIndex++;
                Object value = entry.getValue();

                if(value instanceof String || value instanceof Timestamp) {
                    sql.append("'").append(value.toString()).append("'");
                } else {
                    sql.append(value);
                }

                if(valIndex < insertValues.entrySet().size()) {
                    sql.append(",");
                }
            }
            sql.append(")");

            conn = getConnection();
            statement = conn.createStatement();
            return statement.executeUpdate(sql.toString()) > 0;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            insertValues = null;

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used for running delete statements against the database
     *
     * @return boolean indicating whether delete was successful
     */
    public boolean delete() {
        Connection conn = null;
        Statement statement = null;

        try{
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM ").append(tableName);

            if(whereCondition != null) {
                sql.append(whereCondition);
            } else {
                return false;
            }

            conn = getConnection();
            statement = conn.createStatement();

            return statement.executeUpdate(sql.toString()) > 0;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            whereCondition = null;

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Method for parsing SQL Result sets into an ArrayList
     *
     * @param resultSet returned from the execution of a SQL statement
     * @return ArrayList of key value pairs
     */
    private ArrayList<HashMap<String, Object>> getResultList(ResultSet resultSet) {
        ArrayList<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> valuesMap;

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();

            while (resultSet.next()) {
                valuesMap = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    int columnType = metaData.getColumnType(i);
                    Object value;
                    if(columnType == 93) {
                        value = resultSet.getTimestamp(i);
                    } else {
                        value = resultSet.getObject(i);
                    }

                    valuesMap.put(metaData.getColumnName(i), value);
                }
                results.add(valuesMap);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            return results;
        }
    }
}
