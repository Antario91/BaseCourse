package service.endpoint;

import domain.DBUser;
import handlingException.UserAlreadyExistException;
import handlingException.UserDoesNotExistException;
import org.apache.log4j.Logger;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import persistence.DBUserREPO;
import service.endpointrequestresponse.*;
import service.dto.*;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * Created by olgo on 22-Nov-16.
 */
@Endpoint
public class CRUDEndpoint {
    final static Logger logger = Logger.getLogger(CRUDEndpoint.class);
    private static final String namespaceUri = "http://www.endpointRequestResponse.service";
    private DBUserREPO userREPO;

    @PayloadRoot(localPart = "CreateDBUserRequest", namespace = namespaceUri)
    public void createDBUser(@RequestPayload CreateDBUserRequest request) throws UserAlreadyExistException {
            DBUserDTO userDTO = (DBUserDTO) request.getDBUserDTO();

            DBUser user = new DBUser();

            user.setUsername(userDTO.getUsername());
            user.setCreatedBy(userDTO.getCreatedBy());

            userREPO.createDBUser(user);
    }

    @PayloadRoot(localPart = "GetDBUserRequest", namespace = namespaceUri)
    @ResponsePayload
    public GetDBUserResponse getDBUser(@RequestPayload GetDBUserRequest request) throws UserDoesNotExistException {
        DBUser user = userREPO.getDBUser(request.getUsernameOfDBUser());

        DBUserDTO userDTO = new DBUserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setCreatedBy(user.getCreatedBy());

        GetDBUserResponse response = new GetDBUserResponse();
        response.setDBUser(userDTO);
        return response;
    }

    @PayloadRoot(localPart = "UpdateDBUserRequest", namespace = namespaceUri)
    public void updateDBUser(@RequestPayload UpdateDBUserRequest request) throws UserDoesNotExistException, UserAlreadyExistException {
        UpdateDBUserDTO updateDBUserDTO = request.getDBUser();

        DBUser user = userREPO.getDBUser(updateDBUserDTO.getUsername());

        user.setUsername(updateDBUserDTO.getNewUsername());
        user.setCreatedBy(updateDBUserDTO.getCreatedBy());

        userREPO.updateDBUser(user);
    }

    @PayloadRoot(localPart = "DeleteDBUserRequest", namespace = namespaceUri)
    public void deleteDBUser(@RequestPayload DeleteDBUserRequest request) throws UserDoesNotExistException {
        userREPO.deleteDBUser(request.getDBUserName());
    }

    public DBUserREPO getUserREPO() {
        return userREPO;
    }

    public void setUserREPO(DBUserREPO userREPO) {
        this.userREPO = userREPO;
    }
}
