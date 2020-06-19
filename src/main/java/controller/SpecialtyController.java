package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import factory.ComponentFactory;
import model.Specialty;
import org.junit.platform.commons.util.StringUtils;
import service.SpecialtyService;
import util.ControllerUtil;

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
