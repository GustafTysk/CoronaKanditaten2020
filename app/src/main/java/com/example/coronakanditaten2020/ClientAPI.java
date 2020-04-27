//package com.example.coronakanditaten2020;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.GenericType;
//import javax.ws.rs.core.Response;
//
//import java.util.List;
//
//public class ClientAPI {
//     Client client;
//     Response Response;
//     Location location;
//     WebTarget baseTarget;
//     String baseurl="url";
//
//
//    public ClientAPI() {
//        client =ClientBuilder.newClient();
//        baseTarget=client.target(baseurl);
//    }
//
//
//    List<Location> GetheatMapLocations(String regtime){
//        client =ClientBuilder.newClient();
//        List<Location>  heatMapLocations= client.target("URl")
//                .path("location").queryParam(regtime)
//                .request().get(new GenericType<List<Location>>(){ });
//
//        return heatMapLocations;
//    }
//
//
//    javax.ws.rs.core.Response UpdateUserLocations(List<Location> userlocations, String email){
//        client =ClientBuilder.newClient();
//        Response response= client.target("URl")
//                .path("location")
//                .request().header("email", email).put(Entity.json(userlocations));
//        return response;
//    }
//}
