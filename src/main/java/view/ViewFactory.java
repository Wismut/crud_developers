package view;


import factory.ComponentFactory;
import view.impl.DeveloperViewImpl;
import view.impl.SkillViewImpl;
import view.impl.SpecialityViewImpl;

public class ViewFactory {
    public static View create(String type) {
        switch (type) {
            case View.DEVELOPER_NUMBER:
                return ComponentFactory.getBy(DeveloperViewImpl.class);
            case View.SKILL_NUMBER:
                return ComponentFactory.getBy(SkillViewImpl.class);
            case View.SPECIALTY_NUMBER:
                return ComponentFactory.getBy(SpecialityViewImpl.class);
            default:
                return null;
        }
    }
}
