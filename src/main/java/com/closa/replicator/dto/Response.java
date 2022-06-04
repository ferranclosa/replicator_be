package com.closa.replicator.dto;
import com.closa.replicator.domain.Connections;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.domain.views.ConnectionParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
private ConnectionParams connectionParams;
@JsonInclude(JsonInclude.Include.NON_NULL)
private ConnectionDTO metaFrom;
@JsonInclude(JsonInclude.Include.NON_NULL)
private ConnectionDTO metaTo;
private List<String> listOfStrings = new ArrayList<>();
@JsonInclude(JsonInclude.Include.NON_NULL)
private RPRequest request ;
public RPRequest getRequest() {
return request;
}
public void setRequest(RPRequest request) {
this.request = request;
}
public List<String> getListOfStrings() {
return listOfStrings;
}
public void setListOfStrings(List<String> listOfStrings) {
this.listOfStrings = listOfStrings;
}
public Response() {
}
public ConnectionDTO getMetaFrom() {
return metaFrom;
}
public void setMetaFrom(ConnectionDTO metaFrom) {
this.metaFrom = metaFrom;
}
public ConnectionDTO getMetaTo() {
return metaTo;
}
public void setMetaTo(ConnectionDTO metaTo) {
this.metaTo = metaTo;
}
public ConnectionParams getConnectionParams() {
return connectionParams;
}
public void setConnectionParams(ConnectionParams connectionParams) {
this.connectionParams = connectionParams;
}
}
