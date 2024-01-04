package lk.ijse.StichCraft.DAO;

import lk.ijse.StichCraft.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

    public static <T> T execute(String sql, Object... ob) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement ptsm = connection.prepareStatement(sql);

        for (int i = 0; i < ob.length; i++) {
            ptsm.setObject((1+i),ob[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) ptsm.executeQuery();
        }else {
            return (T) (Boolean)(ptsm.executeUpdate()>0);
        }
    }
}
