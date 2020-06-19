package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import factory.ComponentFactory;
import model.Skill;
import org.junit.platform.commons.util.StringUtils;
import response.ResponseEntity;
import service.SkillService;
import util.ControllerUtil;
import util.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = "/api/v1/skills/*")
public class SkillController extends HttpServlet {
    private final SkillService skillService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public SkillController() {
        this.skillService = ComponentFactory.getBy(SkillService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(id)) {
            Optional<Skill> skill = getById(Long.parseLong(id));
            if (skill.isPresent()) {
                objectMapper.writeValue(resp.getWriter(), skill.get());
            } else {
                objectMapper.writeValue(resp.getWriter(), "Skill with id = " + id + " was not found");
            }
        } else if (StringUtils.isNotBlank(name)) {
            Optional<Skill> skill = getByName(name);
            if (skill.isPresent()) {
                objectMapper.writeValue(resp.getWriter(), skill.get());
            } else {
                objectMapper.writeValue(resp.getWriter(), "Skill with name = " + name + " was not found");
            }
        } else {
            List<Skill> skills = getAll();
            if (skills.isEmpty()) {
                objectMapper.writeValue(resp.getWriter(), "Skills list is empty");
            } else {
                objectMapper.writeValue(resp.getWriter(), skills);
            }
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Skill skillFromRequest;
        try {
            skillFromRequest = objectMapper.readValue(req.getReader(), Skill.class);
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        }
        if (StringUtils.isBlank(skillFromRequest.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Necessary parameter 'name' is absent");
        } else {
            Skill probablySavedSkill = save(skillFromRequest);
            if (probablySavedSkill.getId() != null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), "Skill with id = " +
                        probablySavedSkill.getId() +
                        " was saved");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(resp.getWriter(), "Skill was not saved");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        Skill skillFromRequest = objectMapper.readValue(req.getReader(), Skill.class);
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Necessary parameter 'id' is absent");
        } else if (StringUtils.isBlank(skillFromRequest.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Necessary parameter 'name' is absent");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            Skill updatedSkill = update(new Skill(Long.parseLong(id),
                    skillFromRequest.getName()));
            objectMapper.writeValue(resp.getWriter(), "Skill was updated: " + updatedSkill);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Bad request",
                    resp.getStatus(),
                    "Necessary parameter 'id' is absent");
            objectMapper.writeValue(resp.getWriter(), responseEntity);
        } else {
            deleteById(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
