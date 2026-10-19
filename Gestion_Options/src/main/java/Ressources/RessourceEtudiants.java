package Ressources;
import filter.Secured;
import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
//@Secured
@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
public class RessourceEtudiants {

    private EtudiantBusiness etudiantBusiness = new EtudiantBusiness();
    private OptionBusiness optionBusiness = new OptionBusiness();

    // 1)Création d'un nouvel étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean result = etudiantBusiness.addEtudiant(etudiant);
        if (result) {
            return Response.ok(etudiant).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) Récupération de la liste de tous les étudiants
    @GET
    public Response getAllEtudiants() {
        List<Etudiant> etudiants = etudiantBusiness.getAllEtudiants();
        return Response.ok(etudiants).build();
    }

    // 6) Récupération de la liste des étudiants inscrits à une option donnée (XML)
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Etudiant> liste = etudiantBusiness.getEtudiantsByOption(option);
        return Response.ok(new EtudiantList(liste)).build();
    }

    // 3) Récupération d'un étudiant ayant un identifiant spécifique
    @GET
    @Path("/{id}")
    public Response getEtudiantById(@PathParam("id") String id) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(id);
        if (etudiant != null) {
            return Response.ok(etudiant).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) Suppression d'un étudiant ayant un identifiant spécifique
    @DELETE
    @Path("/{id}")
    public Response supprimerEtudiant(@PathParam("id") String id) {
        boolean result = etudiantBusiness.deleteEtudiant(id);
        if (result) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) Modification d'un étudiant ayant un identifiant spécifique
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant != null && etudiant.getOption() != null) {
            Option opt = optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption());
            if (opt != null) {
                etudiant.setOption(opt);
            }
        }

        boolean result = etudiantBusiness.updateEtudiant(id, etudiant);
        if (result) {
            return Response.ok(etudiant).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
