package io.codeforall.bootcamp.javabank;

import io.codeforall.bootcamp.javabank.controller.LoginController;
import io.codeforall.bootcamp.javabank.model.Bank;

public class App {

    private Bank bank;

    public static void main(String[] args) {

        App app = new App();
        app.bootStrap();
    }

    private void bootStrap() {

        Bootstrap bootstrap = new Bootstrap();
        bank = bootstrap.generateTestData();

        LoginController loginController = bootstrap.wireObjects(bank);

        // start application
        loginController.init();
    }
}
