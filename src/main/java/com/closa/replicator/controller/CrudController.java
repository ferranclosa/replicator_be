package com.closa.replicator.controller;

import com.closa.replicator.dao.RPFileRepo;
import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.domain.enums.SupportedSystems;
import com.closa.replicator.dto.*;
import com.closa.replicator.functions.validators.Validators;
import com.closa.replicator.services.CrudService;
import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;
import com.closa.replicator.throwables.exceptions.ValidationJsonException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping(value="/crud/v1")
public class CrudController {
    @Autowired
    RPRequestRepo requestRepo;

    @Autowired
    RPFileRepo fileRepo;

    @Autowired
    CrudService crudService;

    @CrossOrigin
    @PostMapping(value = "readRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestReadoDTO provideRequests (@RequestBody RequestReadiDTO iDto){
        RequestReadoDTO oDto = new RequestReadoDTO();

        try {
            oDto = crudService.readARequest(iDto);
        } catch (AppException e){
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

    @PostMapping(value = "createRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestCreateoDTO createRequest (@RequestBody RequestCreateiDTO iDto){
        RequestCreateoDTO oDto = new RequestCreateoDTO();
        try {
            oDto = crudService.createRequest(iDto);
        } catch (AppException e){
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

    @PostMapping(value = "updateRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestUpdateoDTO updateRequest (@RequestBody RequestUpdateiDTO iDto){
        RequestUpdateoDTO oDto = new RequestUpdateoDTO();

        return oDto;
    }
    @CrossOrigin
    @PostMapping(value = "deleteRequest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestDeleteoDTO deleteRequest (@RequestBody RequestDeleteiDTO iDto){
        RequestDeleteoDTO oDto = new RequestDeleteoDTO();

        try {
            oDto = crudService.deleteARequest(iDto);
        } catch (AppException e){
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

    @CrossOrigin
    @GetMapping(value = "listRequests",produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestListoDTO listRequests (){
        RequestListoDTO oDto = new RequestListoDTO();

        try {
            oDto = crudService.listRequests();
        } catch (AppException e){
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
    @PostMapping(value = "createFile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FileCreateoDto createFile (@RequestBody FileCreateiDto iDto){
        FileCreateoDto oDto = new FileCreateoDto();

        try {
            oDto = crudService.addFileToRequest(iDto);
        } catch (AppException e){
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

    @PostMapping(value = "updateFile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FileUpdateoDto updateFile (@RequestBody FileUpdateiDto iDto){
        FileUpdateoDto oDto = new FileUpdateoDto();

        try {
            oDto = crudService.changeFile(iDto);
        } catch (AppException e){
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
    @PostMapping(value = "deleteFile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FileDeleteoDto deleteFile (@RequestBody FileDeleteiDto iDto){
        FileDeleteoDto oDto = new FileDeleteoDto();

        try {
            oDto = crudService.removeFile(iDto);
        } catch (AppException e){
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
    @PostMapping(value = "testDTO", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FileDeleteoDto testDTO (@RequestBody RuleCreateiDTO iDto){
        FileDeleteoDto oDto = new FileDeleteoDto();

        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDto.setResponseCode(MessageCode.APP0003.getrCode());
            oDto.setResponseMessage(MessageCode.APP0003.getmMsg());
            oDto.getMessageList().addAll(e.getMultiExceptions());

        }
        return oDto;

    }
    @CrossOrigin
    @GetMapping(value = "getSupportedSystems",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> listSystems (){
        List<String>  oDto = new ArrayList<>();
        for (SupportedSystems one : SupportedSystems.values()){
            oDto.add(one.toString().toUpperCase());
        }
        return oDto;
    }



}
