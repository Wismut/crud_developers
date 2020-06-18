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
        String result = null;
        switch (req.getParameter("action")) {
            case "getById":
                Optional<Skill> skill = getById(Long.parseLong(req.getParameter("id")));
                if (skill.isPresent()) {
                    result = skill.toString();
                } else {
                    result = "Skill with id = " +
                            req.getParameter("id") +
                    " was not found";
                }
                break;
        }
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject(result);
        writer.write(jsonObject.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String result = null;
        switch (req.getParameter("action")) {
            case "save":
                Skill probablySavedSkill = save(new Skill(req.getParameter("name")));
                if (probablySavedSkill.getId() != null) {
                    result = "Skill with id = " +
                            probablySavedSkill.getId() +
                            " was saved";
                } else {
                    result = "Skill was not saved";
                }
                break;
        }
        PrintWriter writer = resp.getWriter();
        JSONObject jsonObject = new JSONObject(result);
        writer.write(jsonObject.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("update".equalsIgnoreCase(req.getParameter("action"))) {
            Skill updatedSkill = update(new Skill(Long.parseLong(req.getParameter("id")),
                    req.getParameter("name")));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("delete".equalsIgnoreCase(req.getParameter("action"))) {
            deleteById(Long.parseLong(req.getParameter("id")));
        }
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
