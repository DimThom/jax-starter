package gr.aub.cf.jaxstarter4.rest;

import gr.aub.cf.jaxstarter4.dto.TeacherInsertDTO;
import gr.aub.cf.jaxstarter4.dto.TeacherReadOnlyDTO;
import gr.aub.cf.jaxstarter4.dto.TeacherUpdateDTO;
import gr.aub.cf.jaxstarter4.model.Teacher;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.*;

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
                (new Teacher(2L, "123456", "Makis", "Kap.")),
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

    @GET
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher(@PathParam("teacherId") Long teacherId) {
        // Assume that call getTeacherById() and
        // we get back a teacher instance or null
        Teacher teacher = new Teacher(teacherId, "123456", "Chris", "Andr.");

        if (teacher == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND").build();
        }

        TeacherReadOnlyDTO dto = mapFrom(teacher);
            return Response.status(Response.Status.OK).entity(dto).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(MultivaluedMap<String, String> formParams, @Context UriInfo uriInfo) {
        TeacherInsertDTO teacherInsertDTO = mapFromMultiV(formParams);
        List<String> errors = new ArrayList<>();

        Set<ConstraintViolation<TeacherInsertDTO>> violations = validator.validate(teacherInsertDTO);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<TeacherInsertDTO> violation : violations) {
                errors.add(violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        // insert teacher
        Teacher teacher = new Teacher(1L, teacherInsertDTO.getSsn(),
                teacherInsertDTO.getFirstname(),
                teacherInsertDTO.getLastname());
        TeacherReadOnlyDTO dto = mapFrom(teacher);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(teacher.getId().toString()).build();
        return Response.status(Response.Status.OK).location(uri).entity(dto).build();
    }

    @DELETE
    @Path("/{teacherId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("teacherId") Long teacherId) {

        // We call deleteTeacher(teacherId) and
        // we get back the deleted teacher
        Teacher teacher = new Teacher(teacherId, "123456", "Alice", "W.");
        if (teacher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        TeacherReadOnlyDTO dto = mapFrom(teacher);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @PUT
    @Path("/{teacherId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@PathParam("teacherId") Long teacherId, MultivaluedMap<String, String> params) {
        if (!Objects.equals(teacherId, Long.parseLong(params.getFirst("id")))) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<String> errors = new ArrayList<>();
        TeacherUpdateDTO teacherUpdateDTO = mapFromMulti(params);

        Set<ConstraintViolation<TeacherUpdateDTO>> violations = validator.validate(teacherUpdateDTO);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<TeacherUpdateDTO> violation : violations) {
                errors.add(violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        // call update service
        Teacher teacher = new Teacher(teacherId, teacherUpdateDTO.getSsn(),
                teacherUpdateDTO.getFirstname(), teacherUpdateDTO.getLastname());

        TeacherReadOnlyDTO dto = mapFrom(teacher);
        return Response.status(Response.Status.OK).entity(dto).build();
    }

    private TeacherInsertDTO mapFromMultiV(MultivaluedMap<String, String> params) {
        TeacherInsertDTO dto = new TeacherInsertDTO();
        dto.setSsn(params.getFirst("ssn"));
        dto.setFirstname(params.getFirst("firstname"));
        dto.setLastname(params.getFirst("lastname"));
        return dto;
    }

    private TeacherUpdateDTO mapFromMulti(MultivaluedMap<String, String> params) {
        TeacherUpdateDTO dto = new TeacherUpdateDTO();
        dto.setId(Long.parseLong(params.getFirst("id")));
        dto.setSsn(params.getFirst("ssn"));
        dto.setFirstname(params.getFirst("firstname"));
        dto.setLastname(params.getFirst("lastname"));
        return dto;
    }

    private TeacherReadOnlyDTO mapFrom(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(),
                teacher.getSsn(), teacher.getFirstname(), teacher.getLastname());
    }
}
