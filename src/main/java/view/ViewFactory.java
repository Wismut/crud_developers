package view;


import factory.ComponentFactory;
import view.impl.DeveloperViewImpl;
import view.impl.SkillViewImpl;
import view.impl.SpecialityViewImpl;

public class ViewFactory {
    public static View create(String type) {
        switch (type) {
            case View.USER_NUMBER:
            case View.USER_LETTER:
                return ComponentFactory.getBy(SpecialityViewImpl.class);
            case View.POST_NUMBER:
            case View.POST_LETTER:
                return ComponentFactory.getBy(DeveloperViewImpl.class);
            case View.REGION_NUMBER:
            case View.REGION_LETTER:
                return ComponentFactory.getBy(SkillViewImpl.class);
            default:
                return null;
        }
    }
}
