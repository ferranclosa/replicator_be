package com.closa.replicator.services;
import com.closa.replicator.domain.Connections;
import com.closa.replicator.domain.views.ConnectionParams;
import com.closa.replicator.functions.Connector;
import org.apache.logging.log4j.spi.LoggerContextShutdownEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.SQLException;
@Service
public class GetConnections {
private final Logger log =  LoggerFactory.getLogger(this.getClass());
public Connections GetConnections (ConnectionParams js ) throws SQLException {
Connection conFrom = new Connector().ConFrom(js);
log.info("Connector for Source succeeded...on " + conFrom.toString());
Connection conTo = new Connector().ConTo(js);
log.info("Connector for Target succreded...on " + conTo.toString());
return  new Connections(conFrom, conTo);
}
}
