/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package REST;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
/**
 *
 * @author quant
 */

@Path("/test")
public class TestRest {

    @GET
    @Path("/echo")
    @Produces(MediaType.TEXT_PLAIN)
    public String echo() {
        return "Hello from REST endpoint!";
    }
}