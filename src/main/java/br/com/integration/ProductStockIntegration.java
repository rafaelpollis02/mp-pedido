package br.com.integration;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8093")
public interface ProductStockIntegration {

    @GET
    @Path("/api/v1/stock/{idProduct}")
    Response getProductById(@PathParam("idProduct") Long idProduct);

}
