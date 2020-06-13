package runner;

import view.impl.MainView;

public class Runner {
    public static void main(String[] args) {
//        LiquibaseUtil.updateDB();
        MainView mainView = new MainView();
        mainView.run();
    }
}
