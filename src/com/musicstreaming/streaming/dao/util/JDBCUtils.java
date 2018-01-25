package com.musicstreaming.streaming.dao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.musicstreaming.streaming.exceptions.DataException;

public final class JDBCUtils {

	private JDBCUtils() {}

	public static void closeResultSet(ResultSet resultSet)
		throws DataException {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DataException(e);
			}
		}

	}

	public static void closeStatement(Statement statement)
		throws DataException {

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new DataException(e);
			}
		}

	}


	public static void closeConnection(Connection connection)
			throws DataException {
		
		if (connection != null) {
			try {
				// Previene un mal uso 
				if (!connection.getAutoCommit()) {
					throw new DataException("Autocommit disabled. Commit or Rollback should be done explicitly.");
				}			
				
				connection.close();
			} catch (SQLException e) {
				throw new DataException(e);
			}
		}
	}
	
	
	public static void closeConnection(Connection connection, boolean commitOrRollback)
		throws DataException {
        try {
            if (connection != null) {
                if (commitOrRollback) {
                	connection.commit();
                } else {
                	connection.rollback();                        
                }
                connection.close();
            }
        } catch (SQLException e) {
            throw new DataException(e);
        }
	}

}
