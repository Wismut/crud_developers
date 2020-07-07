package ua.wismut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.http.HttpStatus;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.wismut.model.Skill;
import ua.wismut.response.ResponseEntityWithErrorAndMessage;
import ua.wismut.service.impl.SkillServiceImpl;
import ua.wismut.util.ControllerUtil;
import ua.wismut.util.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/skills/*")
public class SkillController {
    private final SkillServiceImpl skillServiceImpl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SkillController(SkillServiceImpl skillServiceImpl) {
        this.skillServiceImpl = skillServiceImpl;
    }

    @GetMapping
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(id)) {
            Optional<Skill> skill;
            try {
                skill = getById(Long.parseLong(id));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpStatus.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), "Id must be a number");
                return;
            }
            if (skill.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), skill.get());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Skill with id = " + id + " was not found");
            }
        } else if (StringUtils.isNotBlank(name)) {
            Optional<Skill> skill = getByName(name);
            if (skill.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), skill.get());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Skill with name = " + name + " was not found");
            }
        } else {
            List<Skill> skills = getAll();
            if (skills.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), "Skills list is empty");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), skills);
            }
        }
    }

    @PostMapping
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Skill skillFromRequest;
        try {
            skillFromRequest = objectMapper.readValue(req.getReader(), Skill.class);
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        } catch (MismatchedInputException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        }
        if (StringUtils.isBlank(skillFromRequest.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                    "Necessary parameter 'name' is absent or empty"));
        } else {
            Skill probablySavedSkill = save(skillFromRequest);
            if (probablySavedSkill.getId() != null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), probablySavedSkill);
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                objectMapper.writeValue(resp.getWriter(), "Skill was not saved");
            }
        }
    }

    @PutMapping
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        Skill skillFromRequest;
        try {
            skillFromRequest = objectMapper.readValue(req.getReader(), Skill.class);
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        }
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
            objectMapper.writeValue(resp.getWriter(), updatedSkill);
        }
    }

    @DeleteMapping
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                    "Necessary parameter 'id' is absent"));
        } else {
            deleteById(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    public void deleteById(Long id) {
        skillServiceImpl.deleteBy(id);
    }

    public Skill save(Skill skill) {
        return skillServiceImpl.save(skill);
    }

    public Skill update(Skill skill) {
        return skillServiceImpl.update(skill);
    }

    public Optional<Skill> getById(Long id) {
        return skillServiceImpl.getById(id);
    }

    public List<Skill> getAll() {
        return skillServiceImpl.getAll();
    }

    public Optional<Skill> getByName(String name) {
        return skillServiceImpl.getByName(name);
    }
}
