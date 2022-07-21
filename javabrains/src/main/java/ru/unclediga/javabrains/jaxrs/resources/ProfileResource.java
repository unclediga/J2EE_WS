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
@Produces(value = {MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class ProfileResource {
    private final ProfileService service = new ProfileService();

    @GET
    public List<Profile> getProfiles() {
        return service.getAllProfiles();
    }

    @GET
    @Path("/{pname}")
    public Profile getProfile(@PathParam("pname") String profileName) {
        return service.getProfile(profileName);
    }

}
