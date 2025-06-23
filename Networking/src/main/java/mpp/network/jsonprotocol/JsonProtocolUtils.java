package mpp.network.jsonprotocol;

// TODO 1: IMPORT MODELS FOR JSON PROTOCOL UTILS NETWORKING

import mpp.model.Configuratie;
import mpp.model.Joc;
import mpp.model.User;

import java.util.stream.StreamSupport;

public class JsonProtocolUtils {

    public static Request createLoginRequest(String alias) {
        Request request = new Request();
        request.setType(RequestType.LOGIN);
        request.setAlias(alias);
        return request;
    }

    public static Request createGetAllUsersRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_USERS);
        return request;
    }

    public static Request createGetAllConfiguratiiRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_CONFIGURATII);
        return request;
    }

    public static Request createSaveGameRequest(Joc joc) {
        Request request = new Request();
        request.setType(RequestType.SAVE_JOC);
        request.setJoc(joc);
        return request;
    }

    public static Request createGetAllJocuriRequest() {
        Request request = new Request();
        request.setType(RequestType.GET_ALL_JOCURI);
        return request;
    }

    ///Response
    public static Response createOkResponse(){
        Response response = new Response();
        response.setType(ResponseType.OK);
        return response;
    }

    public static Response createErrorResponse(String errorMessage){
        Response response=new Response();
        response.setType(ResponseType.ERROR);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static Response createGetAllUsersResponse(Iterable<User> users) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_USERS);
        response.setUtilizatori(StreamSupport.stream(users.spliterator(), false).toList());
        return response;
    }

    public static Response createGetAllConfiguratiiResponse(Iterable<Configuratie> configuratii) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_CONFIGURATII);
        response.setConfiguratii(StreamSupport.stream(configuratii.spliterator(), false).toList());
        return response;
    }

    public static Response createSaveGameResponse(Joc joc) {
        Response response = new Response();
        response.setType(ResponseType.SAVE_JOC);
        response.setJoc(joc);
        return response;
    }

    public static Response createGetAllJocuriResponse(Iterable<Joc> jocuri) {
        Response response = new Response();
        response.setType(ResponseType.GET_ALL_JOCURI);
        response.setJocuri(StreamSupport.stream(jocuri.spliterator(), false).toList());
        return response;
    }

}
