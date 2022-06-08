package com.closa.replicator.services;

import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.domain.Connections;
import com.closa.replicator.domain.RPFile;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.domain.WorkRequest;
import com.closa.replicator.domain.views.ConnectionParams;
import com.closa.replicator.dto.ExecutionoDTO;
import com.closa.replicator.dto.Question;
import com.closa.replicator.dto.Response;
import com.closa.replicator.functions.LoadProps;
import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;
import com.closa.replicator.throwables.exceptions.DatabaseRelatedException;
import com.closa.replicator.throwables.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReplicatorService {
    @Autowired
    RPRequestRepo rqRepo;
    @Autowired
    GetConnections getConnections;
    @Autowired
    LoadProps loadProps;
    @Autowired
    MetaDataEngine metaEngine;
    @Autowired
    UtilsService utilsService;
    final Logger log = LoggerFactory.getLogger(this.getClass());

    public ExecutionoDTO executeRequest(Question iDto) throws SQLException, AppException {
        ExecutionoDTO oDto = new ExecutionoDTO();
        Optional<RPRequest> oRq = rqRepo.getByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            throw new ItemNotFoundException("Request does not exist = " +  iDto.getRequestCode().toLowerCase());
        }
/**
 * Get the request details
 */
        RPRequest rq = oRq.get();
/**
 * The the connections
 */
        ConnectionParams cp = loadProps.loadProps(rq);
        Connections conns = getConnections.GetConnections(cp);
        List<String> remoteCreates = new ArrayList<>();
        Statement stmtF = conns.getConFrom().createStatement();
        String sqlF = null;
        log.info("Within remoteExtract. Building DDL Statements... ");
        for (RPFile file : rq.getFileList()) {
            sqlF = "CREATE TABLE QTEMP." +
                    file.getFileName() + " AS ( " +
                    "SELECT A.* FROM " + file.getRequest().getSourceSchema() + "." + file.getFileName() + " A " +
                    utilsService.buildExtractionCriteria(file, rq.getTargetSystem(),
                            rq.getSourceTempSchema()) + " ) WITH DATA ";
            remoteCreates.add(sqlF);
            log.info(sqlF);
        }
        log.info("Within remoteExtract. DDL Statements built ");
        log.info("Within remoteExtract. Executing remote Statements... ");
        for (String one : remoteCreates) {
            stmtF.execute(one);
            System.out.println("Execution for " + one);
        }
        log.info("Within remoteExtract. Remote Statements executed ");
        WorkRequest wRq = null;
        List<String> ddlStatement = null;
        Statement stmtT = conns.getConTo().createStatement();
        log.info("Within remoteExtract. Extracting remote Metadata... ");
        for (RPFile file : rq.getFileList()) {
            wRq = new WorkRequest();
            wRq = metaEngine.getMetadata(conns.getConFrom(), rq, file.getFileName());
            ddlStatement = utilsService.genDDL(wRq);
            log.info("Within remoteExtract. Creating Local Objects from Metadata... ");
            for (String oneSql : ddlStatement) {
                try {
                    stmtT.executeUpdate(oneSql);
                } catch (SQLException e) {
                    oDto.getMessageList().add(("DDL statement incorrect"));
                    oDto.getMessageList().add((oneSql));
                    throw new DatabaseRelatedException("DDL create");
                }
            }
            utilsService.copyData(conns.getConFrom(), conns.getConTo(), wRq, file.getFileName());
        }
        conns.getConFrom().close();
        conns.getConTo().close();

        return oDto;
    }

    public Response buildSourceData(Question iDto) throws SQLException {
        Response oDto = new Response();
        Optional<RPRequest> oRq = rqRepo.getByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            return null;
        }
/**
 * Get the request details
 */
        RPRequest rq = oRq.get();
/**
 * The the connections
 */
        ConnectionParams cp = loadProps.loadProps(rq);
        Connections conns = getConnections.GetConnections(cp);
        List<String> remoteCreates = new ArrayList<>();
        Statement stmt = conns.getConFrom().createStatement();
        for (RPFile file : rq.getFileList()) {
            remoteCreates.add("CREATE TABLE QTEMP." +
                    file.getFileName() + " AS ( " +
                    "SELECT * FROM " + file.getRequest().getSourceSchema() + "." + file.getFileName() +
                    utilsService.buildExtractionCriteria(file, rq.getTargetSystem(),
                            rq.getSourceTempSchema()) + " ) WITH DATA ");
        }
        for (String one : remoteCreates) {
            stmt.execute(one);
            System.out.println("Execution for " + one);
        }
        oDto.getListOfStrings().addAll(remoteCreates);
//    conns.getConFrom().close();
        return oDto;
    }
}
