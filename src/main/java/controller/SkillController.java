package controller;

import factory.ComponentFactory;
import model.Skill;
import org.json.JSONObject;
import service.SkillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "SkillController", urlPatterns = "/skills")
public class SkillController extends HttpServlet {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public SkillController() {
        this.skillService = ComponentFactory.getBy(SkillService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        switch (req.getParameter("action")) {
            case "save":
                Skill probablySavedSkill = save(new Skill(req.getParameter("name")));
                if (probablySavedSkill.getId() != null) {
                    writer.println(new JSONObject().append("Result", "Skill with id = " +
                            probablySavedSkill.getId() +
                            "was saved"));
                } else {
                    writer.println(new JSONObject().append("Result", "Skill was not saved"));
                }
                break;
            case "update":
                update(new Skill(Long.parseLong(req.getParameter("id")),
                        req.getParameter("name")));
                break;
            case "delete":
                deleteById(Long.parseLong(req.getParameter("id")));
                break;
            case "getById":
                getById(Long.parseLong(req.getParameter("id")));
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost");
    }

    public void deleteById(Long id) {
        skillService.deleteBy(id);
    }

    public Skill save(Skill skill) {
        return skillService.save(skill);
    }

    public Skill update(Skill skill) {
        return skillService.update(skill);
    }

    public Optional<Skill> getById(Long id) {
        return skillService.getById(id);
    }

    public List<Skill> getAll() {
        return skillService.getAll();
    }

    public Optional<Skill> getByName(String name) {
        return skillService.getByName(name);
    }
}
