package ru.unclediga.javabrains.jaxrs.resources;

import ru.unclediga.javabrains.jaxrs.model.Profile;
import ru.unclediga.javabrains.jaxrs.service.ProfileService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/profiles")
public class ProfileResource {
    private final ProfileService service = new ProfileService();

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.TEXT_XML})
    public List<Profile> getProfiles() {
        return service.getAllProfiles();
    }
    @GET
    @Path("/{pname}")
    @Produces(value={MediaType.APPLICATION_XML,MediaType.TEXT_XML})
    public Profile getProfile(@PathParam("pname") String profileName){
        return service.getProfile(profileName);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getProfilesJ(){
        return service.getAllProfiles();
    }
    @GET
    @Path("/{pname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profile getProfileJ(@PathParam("pname") String profileName){
        return service.getProfile(profileName);
    }
}
