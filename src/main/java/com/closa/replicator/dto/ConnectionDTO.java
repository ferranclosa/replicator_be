package com.closa.replicator.dto;
import java.time.LocalDateTime;
public class ConnectionDTO {
private boolean readOnly;
private String hostname;
private String url;
private String systemName;
private boolean closed;
private String userName;
private String locale;
private boolean connected;
private String timeZone;
private int loginTimeout;
private boolean loginTimeoutSet;
private LocalDateTime signonDate;
public ConnectionDTO() {
}
public boolean isReadOnly() {
return readOnly;
}
public void setReadOnly(boolean readOnly) {
this.readOnly = readOnly;
}
public String getHostname() {
return hostname;
}
public void setHostname(String hostname) {
this.hostname = hostname;
}
public String getUrl() {
return url;
}
public void setUrl(String url) {
this.url = url;
}
public String getSystemName() {
return systemName;
}
public void setSystemName(String systemName) {
this.systemName = systemName;
}
public boolean isClosed() {
return closed;
}
public void setClosed(boolean closed) {
this.closed = closed;
}
public String getUserName() {
return userName;
}
public void setUserName(String userName) {
this.userName = userName;
}
public String getLocale() {
return locale;
}
public void setLocale(String locale) {
this.locale = locale;
}
public boolean isConnected() {
return connected;
}
public void setConnected(boolean connected) {
this.connected = connected;
}
public String getTimeZone() {
return timeZone;
}
public void setTimeZone(String timeZone) {
this.timeZone = timeZone;
}
public int getLoginTimeout() {
return loginTimeout;
}
public void setLoginTimeout(int loginTimeout) {
this.loginTimeout = loginTimeout;
}
public boolean isLoginTimeoutSet() {
return loginTimeoutSet;
}
public void setLoginTimeoutSet(boolean loginTimeoutSet) {
this.loginTimeoutSet = loginTimeoutSet;
}
public LocalDateTime getSignonDate() {
return signonDate;
}
public void setSignonDate(LocalDateTime signonDate) {
this.signonDate = signonDate;
}
}
