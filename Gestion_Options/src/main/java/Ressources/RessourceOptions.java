package Ressources;
import filter.Secured;
import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
//@Secured
@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
public class RessourceOptions {

    public static OptionBusiness optionBusiness = new OptionBusiness();

    // 1. POST /options
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterOption(Option option) {

        boolean result = optionBusiness.addOption(option);

        if (result) {
            return Response.ok(option).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2. GET /options
    @GET
    public Response getOptions(
            @QueryParam("domaine") String domaine) {

        List<Option> options;

        if (domaine == null || domaine.isEmpty()) {
            options = optionBusiness.getListeOptions();
        } else {
            options = optionBusiness.getOptionsByDomaine(domaine);
        }

        return Response.ok(options).build();
    }

    // 3. DELETE /options/{code}
    @DELETE
    @Path("/{code}")
    public Response supprimerOption(
            @PathParam("code") int code) {

        boolean result = optionBusiness.deleteOption(code);

        if (result) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4. PUT /options/{code}
    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierOption(
            @PathParam("code") int code,
            Option option) {

        boolean result =
                optionBusiness.updateOption(code, option);

        if (result) {
            return Response.ok(option).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5. GET /options/{code}
    @GET
    @Path("/{code}")
    public Response getOption(
            @PathParam("code") int code) {

        Option option =
                optionBusiness.getOptionByCode(code);

        if (option != null) {
            return Response.ok(option).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}