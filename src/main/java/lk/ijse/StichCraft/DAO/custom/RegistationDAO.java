package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.RegistrationDto;

import java.sql.SQLException;

public interface RegistationDAO {
    boolean SaveUser(RegistrationDto dto) throws SQLException;
    boolean ValidUser(String userName,String pw) throws SQLException;
}
