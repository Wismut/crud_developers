package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import factory.ComponentFactory;
import model.Developer;
import org.apache.http.HttpStatus;
import org.junit.platform.commons.util.StringUtils;
import response.ResponseEntityWithData;
import response.ResponseEntityWithErrorAndMessage;
import service.DeveloperService;
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

@WebServlet(urlPatterns = "/api/v1/developers/*")
public class DeveloperController extends HttpServlet {
    private final DeveloperService developerService;
    private final ObjectMapper mapper = new ObjectMapper();

    public DeveloperController() {
        this.developerService = ComponentFactory.getBy(DeveloperService.class);
    }

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isNotBlank(id)) {
            Optional<Developer> developer;
            try {
                developer = getById(Long.parseLong(id));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpStatus.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                        "Id must be a number"));
                return;
            }
            if (developer.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), developer.get());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Not found",
                        "Developer with id = " + id + " was not found"));
            }
        } else {
            List<Developer> skills = getAll();
            if (skills.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), new ResponseEntityWithData<>("Developers list is empty"));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(), new ResponseEntityWithData<>(skills));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Developer developerFromRequest;
        try {
            developerFromRequest = mapper.readValue(req.getReader(), Developer.class);
            if (StringUtils.isBlank(developerFromRequest.getFirstName())) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                        "Necessary parameter 'firstName' is absent or empty"));
            } else if (StringUtils.isBlank(developerFromRequest.getLastName())) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                        "Necessary parameter 'lastName' is absent or empty"));
            } else {
                Developer probablySavedDeveloper = save(developerFromRequest);
                if (probablySavedDeveloper.getId() != null) {
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    mapper.writeValue(resp.getWriter(), probablySavedDeveloper);
                } else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    mapper.writeValue(resp.getWriter(), "Developer was not saved");
                }
            }
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
        } catch (MismatchedInputException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                    ExceptionHandler.handle(e)));
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        Developer developerFromRequest;
        try {
            developerFromRequest = mapper.readValue(req.getReader(), Developer.class);
        } catch (UnrecognizedPropertyException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), ExceptionHandler.handle(e));
            return;
        }
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseEntityWithData<>("Necessary parameter 'id' is absent"));
        } else if (StringUtils.isBlank(developerFromRequest.getFirstName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseEntityWithData<>("Necessary parameter 'firstName' is absent"));
        } else if (StringUtils.isBlank(developerFromRequest.getLastName())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseEntityWithData<>("Necessary parameter 'lastName' is absent"));
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            Developer updatedDeveloper = update(new Developer(Long.parseLong(id),
                    developerFromRequest.getFirstName(),
                    developerFromRequest.getLastName()));
            mapper.writeValue(resp.getWriter(), new ResponseEntityWithData<>(updatedDeveloper));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String id = ControllerUtil.getPathVariableFrom(req);
        if (StringUtils.isBlank(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(resp.getWriter(), new ResponseEntityWithErrorAndMessage("Bad request",
                    "Necessary parameter 'id' is absent"));
        } else {
            deleteById(Long.parseLong(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    public void deleteById(Long id) {
        developerService.deleteById(id);
    }

    public Developer update(Developer developer) {
        return developerService.update(developer);
    }

    public Developer save(Developer developer) {
        developerService.save(developer);
        return developer;
    }

    public Optional<Developer> getById(Long id) {
        return developerService.getById(id);
    }

    public List<Developer> getAllBySpeciality(String specialityName) {
        return developerService.getAllBySpeciality(specialityName);
    }

    public List<Developer> getAll() {
        return developerService.getAll();
    }
}
