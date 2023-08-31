package gr.aub.cf.jaxstarter4.rest;

import gr.aub.cf.jaxstarter4.dto.TeacherReadOnlyDTO;
import gr.aub.cf.jaxstarter4.model.Teacher;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/teachers")
public class TeacherRestController {

    private final Validator validator;

    public TeacherRestController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeachers() {

        // Assume we call service layer and get back
        // a list of teachers
        List<Teacher> teachers = Arrays.asList(new Teacher(1L, "123456", "Ath.", "Andr."),
                (new Teacher(2L, "123456", "Makis", "Kapetis")),
                (new Teacher(3L, "234567", "Markos", "Karab.")),
                (new Teacher(4L, "345678", "Chr.", "Frag.")));

        if (teachers.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<TeacherReadOnlyDTO> teachersDto = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teachersDto.add(mapFrom(teacher));
        }

        return Response.status(Response.Status.OK).entity(teachersDto).build();

    }

    private TeacherReadOnlyDTO mapFrom(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(),
                teacher.getSsn(), teacher.getFirstname(), teacher.getLastname());
    }
}
