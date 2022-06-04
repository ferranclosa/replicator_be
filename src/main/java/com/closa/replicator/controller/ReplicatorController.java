package com.closa.replicator.controller;

import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.domain.Connections;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.domain.views.ConnectionParams;
import com.closa.replicator.dto.ConnectionDTO;
import com.closa.replicator.dto.Question;
import com.closa.replicator.dto.Response;
import com.closa.replicator.functions.LoadProps;
import com.closa.replicator.services.GetConnections;
import com.closa.replicator.services.ReplicatorService;
import com.ibm.as400.access.AS400JDBCConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
        oDto.setConnectionParams(lp.loadProps(iDto.getRequestCode()));
        return oDto;
    }

    @PostMapping(value = "/getConnectors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getConnectors(@RequestBody Question iDto) throws SQLException {
        Response oDto = new Response();
        ConnectionParams parms = lp.loadProps(iDto.getRequestCode());
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
        ConnectionParams parms = lp.loadProps(iDto.getRequestCode());
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
    public Response executeRequest(@RequestBody Question iDto) {
        Response oDto = new Response();
        Optional<RPRequest> oRq = rqRepo.getByRequestCode(iDto.getRequestCode());
        try {
            oDto = replService.executeRequest(iDto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oDto;
    }
}
