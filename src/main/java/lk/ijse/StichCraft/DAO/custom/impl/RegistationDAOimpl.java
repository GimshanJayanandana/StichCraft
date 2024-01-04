package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.custom.RegistationDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.RegistrationDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistationDAOimpl implements RegistationDAO {
    public boolean SaveUser(RegistrationDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO user VALUES (?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,dto.getUser_name());
        ptsm.setString(2,dto.getPassword());

        return ptsm.executeUpdate() > 0;
    }

    public boolean ValidUser(String userName,String pw) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE user_name = ? AND password = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,userName);
        ptsm.setString(2,pw);

        ResultSet resultSet = ptsm.executeQuery();
        return resultSet.next();
    }

    public boolean check(String userName, String pw) throws SQLException {
        return ValidUser(userName,pw);
    }
}
