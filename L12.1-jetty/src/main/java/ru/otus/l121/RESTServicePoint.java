package ru.otus.l121;

import ru.otus.l121.dataSets.UserDataSet;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rest/")
@Singleton
public class RESTServicePoint {

    @Inject
    private DBService hibernateDBService;

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public int count() {
        return hibernateDBService.count();
    }

    @GET
    @Path("get-by-id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getById(@PathParam("id") int id) {
        return hibernateDBService.getUserNameById(id);
    }

    @GET
    @Path("get-all-users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDataSet> getAllUsers() {
        return hibernateDBService.getAllUsers();
    }

    @POST
    @Path("put")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(UserDataSet userDataSet) {
        hibernateDBService.insertUsers(userDataSet);
        return Response.status(200).build();
    }
}

