package com.closa.replicator.controller;

import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.domain.Connections;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.domain.views.ConnectionParams;
import com.closa.replicator.dto.ConnectionDTO;
import com.closa.replicator.dto.ExecutionoDTO;
import com.closa.replicator.dto.Question;
import com.closa.replicator.dto.Response;
import com.closa.replicator.functions.LoadProps;
import com.closa.replicator.services.GetConnections;
import com.closa.replicator.services.ReplicatorService;
import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;
import com.closa.replicator.throwables.exceptions.ItemNotFoundException;
import com.ibm.as400.access.AS400JDBCConnection;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping(value = "/api/v1")
public class ReplicatorController {
    @Autowired
    LoadProps lp;
    @Autowired
    GetConnections getConnections;
    @Autowired
    RPRequestRepo rqRepo;
    @Autowired
    ReplicatorService replService;

    @PostMapping(value = "/getConnectorParams", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getConnectorParams(@RequestBody Question iDto) {
        Response oDto = new Response();
        try {
            oDto.setConnectionParams(lp.loadProps(iDto.getRequestCode()));
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return oDto;
    }

    @PostMapping(value = "/getConnectors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getConnectors(@RequestBody Question iDto) throws SQLException {
        Response oDto = new Response();
        ConnectionParams parms = null;
        try {
            parms = lp.loadProps(iDto.getRequestCode());
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        Connections conns = getConnections.GetConnections(parms);
        ConnectionDTO oDto2 = new ConnectionDTO();
        oDto2.setClosed(conns.getConFrom().isClosed());
        oDto2.setConnected(!conns.getConFrom().isClosed());
        oDto2.setUrl(conns.getConFrom().getMetaData().getURL());
        AS400JDBCConnection ajc = (AS400JDBCConnection) conns.getConFrom();
        oDto2.setSystemName(ajc.getSystem().getSystemName());
        oDto2.setLocale(String.valueOf(ajc.getSystem().getLocale()));
        oDto2.setUserName(conns.getConFrom().getMetaData().getUserName());
        oDto2.setHostname(ajc.getServerJobIdentifier());
        oDto.setMetaFrom(oDto2);
        oDto2 = new ConnectionDTO();
        oDto2.setClosed(conns.getConTo().isClosed());
        oDto2.setUrl(conns.getConTo().getMetaData().getURL());
        oDto2.setConnected(!conns.getConTo().isClosed());
        oDto2.setUserName(conns.getConTo().getMetaData().getUserName());
        oDto.setMetaTo(oDto2);
        return oDto;
    }

    @PostMapping(value = "/cloneTable", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response cloneTable(@RequestBody Question iDto) throws SQLException {
        Response oDto = new Response();
        ConnectionParams parms = null;
        try {
            parms = lp.loadProps(iDto.getRequestCode());
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        Connections conns = getConnections.GetConnections(parms);
        ConnectionDTO oDto2 = new ConnectionDTO();
        oDto2.setClosed(conns.getConFrom().isClosed());
        oDto2.setConnected(!conns.getConFrom().isClosed());
        oDto2.setUrl(conns.getConFrom().getMetaData().getURL());
        AS400JDBCConnection ajc = (AS400JDBCConnection) conns.getConFrom();
        oDto2.setSystemName(ajc.getSystem().getSystemName());
        oDto2.setLocale(String.valueOf(ajc.getSystem().getLocale()));
        oDto2.setUserName(conns.getConFrom().getMetaData().getUserName());
        oDto2.setHostname(ajc.getServerJobIdentifier());
        oDto.setMetaFrom(oDto2);
        oDto2 = new ConnectionDTO();
        oDto2.setClosed(conns.getConTo().isClosed());
        oDto2.setUrl(conns.getConTo().getMetaData().getURL());
        oDto2.setConnected(!conns.getConTo().isClosed());
        oDto2.setUserName(conns.getConTo().getMetaData().getUserName());
        oDto.setMetaTo(oDto2);
        return oDto;
    }

    @PostMapping(value = "/getRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getRequest(@RequestBody Question iDto) {
        Response oDto = new Response();
        Optional<RPRequest> oRq = rqRepo.getByRequestCode(iDto.getRequestCode());
        if (oRq.isPresent()) {
            oDto.setRequest(oRq.get());
        } else return null;
        return oDto;
    }

    @PostMapping(value = "/executeRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionoDTO executeRequest(@RequestBody Question iDto) {
        ExecutionoDTO oDto = new ExecutionoDTO();
        Optional<RPRequest> oRq = rqRepo.getByRequestCode(iDto.getRequestCode());
        try {
            oDto = replService.executeRequest(iDto);
        }
        catch (SQLException e ) {
            oDto.setResponseCode(MessageCode.APP0008.getrCode());
            oDto.setResponseMessage(MessageCode.APP0008.getmMsg());
            oDto.getMessageList().addAll(Arrays.asList(ExceptionUtils.getRootCauseStackTrace(e)));
            e.printStackTrace();
        }catch (AppException e){
            oDto.setResponseCode(e.getMessageCode().getrCode());
            oDto.setResponseMessage(e.getMessageCode().getmMsg());
            oDto.getMessageList().add(e.getMessageText());
        }
        catch (Exception e) {
            oDto.setResponseCode(MessageCode.APP0099.getrCode());
            oDto.setResponseMessage(MessageCode.APP0099.getmMsg());
            oDto.getMessageList().add(e.getLocalizedMessage());
            oDto.getMessageList().addAll(Arrays.asList(ExceptionUtils.getRootCauseStackTrace(e)));
        }

        return oDto;
    }
}
