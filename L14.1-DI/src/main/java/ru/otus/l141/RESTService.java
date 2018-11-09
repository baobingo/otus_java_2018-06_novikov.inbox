package ru.otus.l141;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.l141.dataSets.UserDataSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class RESTService {

    @Autowired
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

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public int test() {
        return 1;
    }
}

