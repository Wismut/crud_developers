package ua.wismut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ua.wismut.model.Specialty;
import ua.wismut.response.ResponseEntityWithErrorAndMessage;
import ua.wismut.service.SpecialtyService;
import ua.wismut.util.ControllerUtil;
import ua.wismut.util.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/specialties/")
public class SpecialtyController {
    private final SpecialtyService specialtyService;
    private final ObjectMapper mapper = new ObjectMapper();

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isNotBlank(id)) {
            Optional<Specialty> specialty = getById(Long.parseLong(id));
            if (specialty.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), specialty.get());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), "Specialty with id = " + id + " was not found");
            }
        } else {
            List<Specialty> specialties = getAll();
            if (specialties.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), "Specialties list is empty");
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), specialties);
            }
        }
    }

    @PostMapping
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Specialty specialtyFromRequest;
        try {
            specialtyFromRequest = mapper.readValue(req.getReader(), Specialty.class);
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        }
        if (StringUtils.isBlank(specialtyFromRequest.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), "Necessary parameter 'name' is absent");
        } else {
            Specialty probablySavedSpecialty = save(specialtyFromRequest);
            if (probablySavedSpecialty.getId() != null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                mapper.writeValue(resp.getWriter(), "Specialty with id = " +
                        probablySavedSpecialty.getId() +
                        " was saved");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                mapper.writeValue(resp.getWriter(), "Specialty was not saved");
            }
        }
    }

    @PutMapping
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        Specialty specialtyFromRequest;
        try {
            specialtyFromRequest = mapper.readValue(req.getReader(), Specialty.class);
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        }
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), "Necessary parameter 'id' is absent");
        } else if (StringUtils.isBlank(specialtyFromRequest.getName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), "Necessary parameter 'name' is absent");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            Specialty updatedSpecialty = update(new Specialty(Long.parseLong(id),
                    specialtyFromRequest.getName(),
                    specialtyFromRequest.getDescription()));
            mapper.writeValue(resp.getWriter(), "Specialty was updated: " + updatedSpecialty);
        }
    }

    @DeleteMapping
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ResponseEntityWithErrorAndMessage responseEntityWithErrorAndMessage = new ResponseEntityWithErrorAndMessage("Bad request",
                    "Necessary parameter 'id' is absent");
            mapper.writeValue(resp.getWriter(), responseEntityWithErrorAndMessage);
        } else {
            deleteById(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    public Specialty save(Specialty specialty) {
        return specialtyService.save(specialty);
    }

    public Specialty update(Specialty specialty) {
        return specialtyService.update(specialty);
    }

    public void deleteById(Long id) {
        specialtyService.deleteBy(id);
    }

    public List<Specialty> getAll() {
        return specialtyService.getAll();
    }

    public Optional<Specialty> getById(Long id) {
        return specialtyService.getById(id);
    }
}
