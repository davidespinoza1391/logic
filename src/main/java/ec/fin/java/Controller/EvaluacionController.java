package ec.fin.java.Controller;

import ec.fin.java.services.Calcular;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@Path("/api")
public class EvaluacionController {

    private static final Logger LOG = Logger.getLogger(EvaluacionController.class);

    @Inject
    Calcular calcular;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        LOG.info("Ingreso");
        //JSONObject resp=calcular.execute();

        return "Hello RESTEasy";
    }

    @POST
    @Path("/calcular")
    @Produces(MediaType.TEXT_PLAIN)
    public  JSONObject calcular(String data)  {
        LOG.info("INGRESA calcular");
        LOG.info(data);
        JSONObject jsonObject = new JSONObject(data);
        JSONObject resp=calcular.execute(jsonObject);

        return resp;
    }

}
