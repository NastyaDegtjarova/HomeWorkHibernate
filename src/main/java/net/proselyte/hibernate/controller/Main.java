package net.proselyte.hibernate.controller;


import net.proselyte.hibernate.dao.hibernate.*;

import java.util.Scanner;

/**
 * Created by Nastya on 19.11.2017.
 */
public class Main {
    public static void main(String[] args) {
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

            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                int choise = scan.nextInt();
                if (choise == 1) {
                    developerController.menu();
                } else if (choise == 2) {
                    skillController.menu();
                } else if (choise == 3) {
                    companyController.menu();
                } else if (choise == 4) {
                    customerController.menu();
                } else if (choise == 5) {
                    projectController.menu();
                } else if (choise == 0) {
                    break;
                }
            }
        }
    }
}
