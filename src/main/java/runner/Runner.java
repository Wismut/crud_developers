package runner;

import liquibase.LiquibaseUtil;
import org.hibernate.query.validator.CheckHQL;
import view.impl.MainView;

@CheckHQL
public class Runner {
    public static void main(String[] args) {
        LiquibaseUtil.updateDB();
        MainView mainView = new MainView();
        mainView.run();
    }
}
