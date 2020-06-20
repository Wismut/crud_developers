package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import factory.ComponentFactory;
import model.Specialty;
import org.junit.platform.commons.util.StringUtils;
import response.ResponseEntity;
import service.SpecialtyService;
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

@WebServlet(urlPatterns = "/api/v1/specialties/*")
public class SpecialtyController extends HttpServlet {
    private final SpecialtyService specialtyService;
    private final ObjectMapper mapper = new ObjectMapper();

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public SpecialtyController() {
        this.specialtyService = ComponentFactory.getBy(SpecialtyService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isNotBlank(id)) {
            Optional<Specialty> specialty = getById(Long.parseLong(id));
            if (specialty.isPresent()) {
                mapper.writeValue(resp.getWriter(), specialty.get());
            } else {
                mapper.writeValue(resp.getWriter(), "Specialty with id = " + id + " was not found");
            }
        } else {
            List<Specialty> specialties = getAll();
            if (specialties.isEmpty()) {
                mapper.writeValue(resp.getWriter(), "Specialties list is empty");
            } else {
                mapper.writeValue(resp.getWriter(), specialties);
            }
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
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

    @Override
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Bad request",
                    resp.getStatus(),
                    "Necessary parameter 'id' is absent");
            mapper.writeValue(resp.getWriter(), responseEntity);
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
