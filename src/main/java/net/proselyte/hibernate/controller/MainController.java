package net.proselyte.hibernate.controller;

import net.proselyte.hibernate.dao.hibernate.*;

/**
 * Created by Nastya on 07.12.2017.
 */
public class MainController extends AbstractController{

    private static final int DEVELOPERS = 1;
    private static final int SKILLS = 2;
    private static final int COMPANIES= 3;
    private static final int CUSTOMER = 4;
    private static final int PROJECT = 5;
    private static final int EXIT = 0;

    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.previous_menu();
    }

    public void previous_menu(){
        DeveloperController developerController = new DeveloperController(
                HibernateDeveloperDAOImpl.getInstance(),
                HibernateProjectDAOImpl.getInstance(),
                HibernateSkiiDAOImpl.getInstance());
        SkillController skillController = new SkillController(
                HibernateSkiiDAOImpl.getInstance(),
                HibernateDeveloperDAOImpl.getInstance()
        );
        ProjectController projectController = new ProjectController(
                HibernateProjectDAOImpl.getInstance(),
                HibernateDeveloperDAOImpl.getInstance(),
                HibernateCustomerDAOImpl.getInstance(),
                HibernateCompanyDAOImpl.getInstance()
        );
        CustomerController customerController = new CustomerController(
                HibernateCustomerDAOImpl.getInstance(),
                HibernateProjectDAOImpl.getInstance()
        );
        CompanyController companyController = new CompanyController(
                HibernateCompanyDAOImpl.getInstance(),
                HibernateProjectDAOImpl.getInstance()
        );
        while (true) {
            System.out.println("1 - Developers");
            System.out.println("2 - Skills");
            System.out.println("3 - Companies");
            System.out.println("4 - Customers");
            System.out.println("5 - Projects");
            System.out.println("0 - Exit");

            if (getScan().hasNextInt()) {
                int choise = getScan().nextInt();
                switch (choise) {
                    case DEVELOPERS:
                        developerController.menu();
                        break;
                    case SKILLS:
                        skillController.menu();
                        break;
                    case COMPANIES:
                        companyController.menu();
                        break;
                    case CUSTOMER:
                        customerController.menu();
                        break;
                    case PROJECT:
                        projectController.menu();
                        break;
                    case EXIT:
                        break;
                    default:
                        break;
                }
            }
        }
    }


}
