package com.closa.replicator.functions;
import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.domain.views.ConnectionParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class LoadProps {
@Autowired
RPRequestRepo rqRepo;
public  ConnectionParams loadProps(String code) {
Optional<RPRequest> oRq = rqRepo.getByRequestCode(code);
if(oRq.isPresent()){
RPRequest rq = oRq.get();
ConnectionParams cp = new ConnectionParams();
cp.setSourceSystem(rq.getSourceSystem());
cp.setSourceSchema(rq.getSourceSchema());
cp.setSourceUser(rq.getSourceUser());
cp.setSourceCreds(rq.getSourceCred());
cp.setSourceURL(rq.getSourceURL());
cp.setTargetSystem(rq.getTargetSystem());
cp.setTargetSchema(rq.getTargetSchema());
cp.setTargetDriver(rq.getTargetDriver());
cp.setTargetUser(rq.getTargetUser());
cp.setTargetCreds(rq.getTargetCred());
cp.setTargetURL(rq.getTargetURL());
return cp;
}
else return null;
}
public  ConnectionParams loadProps(RPRequest rq) {
ConnectionParams cp = new ConnectionParams();
cp.setSourceSystem(rq.getSourceSystem());
cp.setSourceSchema(rq.getSourceSchema());
cp.setSourceUser(rq.getSourceUser());
cp.setSourceCreds(rq.getSourceCred());
cp.setSourceURL(rq.getSourceURL());
cp.setTargetSystem(rq.getTargetSystem());
cp.setTargetSchema(rq.getTargetSchema());
cp.setTargetDriver(rq.getTargetDriver());
cp.setTargetUser(rq.getTargetUser());
cp.setTargetCreds(rq.getTargetCred());
cp.setTargetURL(rq.getTargetURL());
return cp;
}
}
