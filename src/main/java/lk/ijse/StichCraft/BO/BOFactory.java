package lk.ijse.StichCraft.BO;

import lk.ijse.StichCraft.BO.Custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getBoFactory(){return (boFactory == null) ? boFactory = new BOFactory() : boFactory;}

    public enum BOTypes {
        CUSTOMER, EMPLOYEE, ORDER, PRODUCTION, SALARY, SUPPLIER
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PRODUCTION:
                return new ProductionBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}
