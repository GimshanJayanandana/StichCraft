package lk.ijse.StichCraft.DAO;

import lk.ijse.StichCraft.DAO.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER, EMPLOYEE, ORDER, PRODUCTION, PRODUCTIONDETAIL, SALARY, SUPPLIER,QUERY
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ORDER:
                return new OrderDAOimpl();
            case PRODUCTION:
                return new ProductionDAOimpl();
            case PRODUCTIONDETAIL:
                return new ProductionDetailDAOimpl();
            case SALARY:
                return new SalaryDAOimpl();
            case SUPPLIER:
                return new SuppllierDAOimpl();
            case QUERY:
                return new QueryDAOimpl();
            default:
                return null;
        }
    }
}
