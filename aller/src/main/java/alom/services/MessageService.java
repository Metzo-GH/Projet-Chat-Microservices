package alom.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://services.alom/")
public interface MessageService {

    @WebMethod
    String sendPrivateMessage(
            @WebParam(name = "token", targetNamespace = "http://services.alom/") String token,
            @WebParam(name = "recipient", targetNamespace = "http://services.alom/") String recipient,
            @WebParam(name = "message", targetNamespace = "http://services.alom/") String message);

    @WebMethod
    String sendChannelMessage(
            @WebParam(name = "token", targetNamespace = "http://services.alom/") String token,
            @WebParam(name = "channel", targetNamespace = "http://services.alom/") String channel,
            @WebParam(name = "message", targetNamespace = "http://services.alom/") String message);

    @WebMethod
    String joinChannel(
            @WebParam(name = "token", targetNamespace = "http://services.alom/") String token,
            @WebParam(name = "channel", targetNamespace = "http://services.alom/") String channel);

    @WebMethod
    String leaveChannel(
            @WebParam(name = "token", targetNamespace = "http://services.alom/") String token,
            @WebParam(name = "channel", targetNamespace = "http://services.alom/") String channel);

    @WebMethod
    String register(
            @WebParam(name = "nickname", targetNamespace = "http://services.alom/") String nickname,
            @WebParam(name = "password", targetNamespace = "http://services.alom/") String password);

    @WebMethod
    String login(
            @WebParam(name = "nickname", targetNamespace = "http://services.alom/") String nickname,
            @WebParam(name = "password", targetNamespace = "http://services.alom/") String password);

    @WebMethod
    String validateToken(
            @WebParam(name = "token", targetNamespace = "http://services.alom/") String token);
}
