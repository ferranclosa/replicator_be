package com.closa.replicator.services;

import com.closa.replicator.dao.RPFileRepo;
import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.domain.RPFile;
import com.closa.replicator.domain.RPRequest;
import com.closa.replicator.dto.*;
import com.closa.replicator.functions.validators.Validators;
import com.closa.replicator.throwables.AppException;
import com.closa.replicator.throwables.MessageCode;
import com.closa.replicator.throwables.exceptions.ItemAlreadyExistsException;
import com.closa.replicator.throwables.exceptions.ItemNotFoundException;
import com.closa.replicator.throwables.exceptions.ValidationJsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CrudService {

    @Autowired
    RPRequestRepo requestRepo;

    @Autowired
    RPFileRepo fileRepo;

    @Transactional(readOnly = true)
    public RequestReadoDTO readARequest(RequestReadiDTO iDto) throws AppException {
        RequestReadoDTO oDto = new RequestReadoDTO();

        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDto.setResponseCode(MessageCode.APP0003.getrCode());
            oDto.getMessageList().add(MessageCode.APP0003.getmMsg());
            oDto.getMessageList().addAll(e.getMultiExceptions());
            return oDto;
        }

        Optional<RPRequest> oRq = requestRepo.findByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            throw new ItemNotFoundException(iDto.getRequestCode());
        }
        RPRequest rq = oRq.get();
        oDto.setBatchSize(rq.getBatchSize());
        oDto.setTargetDropBefore(rq.getTargetDropBefore());
        oDto.setTragetClearBefore(rq.getTargetClearBefore());
        oDto.setRequestDescription(rq.getRequestDescription());
        oDto.setRequestCode(rq.getRequestCode());
        oDto.setSourceSystem(rq.getSourceSystem());
        oDto.setSourceSchema(rq.getSourceSchema());
        oDto.setSourceURL(rq.getSourceURL());
        oDto.setSourceDriver(rq.getSourceDriver());
        oDto.setSourceUser(rq.getSourceUser());
        oDto.setSourceCred(rq.getSourceCred());
        oDto.setSourceTempSchema(rq.getSourceTempSchema());
        oDto.setTargetDriver(rq.getTargetDriver());
        oDto.setTargetSystem(rq.getTargetSystem());
        oDto.setTargetURL(rq.getTargetURL());
        oDto.setTargetSchema(rq.getTargetSchema());
        oDto.setTargetUser(rq.getTargetUser());
        oDto.setTargetCred(rq.getTargetCred());

        oDto.setResponseCode(MessageCode.APP0000.getrCode());
        oDto.getMessageList().add(MessageCode.APP0000.getmMsg());

        return oDto;
    }

    @Transactional
    public RequestDeleteoDTO deleteARequest(RequestDeleteiDTO iDto) throws ItemNotFoundException {
        RequestDeleteoDTO oDTO = new RequestDeleteoDTO();
        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDTO.setResponseCode(MessageCode.APP0003.getrCode());
            oDTO.getMessageList().add(MessageCode.APP0003.getmMsg());
            oDTO.getMessageList().addAll(e.getMultiExceptions());
            return oDTO;
        }

        Optional<RPRequest> oRq = requestRepo.findByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            throw new ItemNotFoundException(iDto.getRequestCode());
        }
        requestRepo.delete(oRq.get());

        oDTO.setResponseCode(MessageCode.APP0000.getrCode());
        oDTO.getMessageList().add(MessageCode.APP0000.getmMsg());
        oDTO.getMessageList().add(iDto.getRequestCode() + " deleted.");

        return oDTO;
    }

    @Transactional
    public RequestCreateoDTO createRequest(RequestCreateiDTO iDto) throws ItemAlreadyExistsException, ValidationJsonException {

        RequestCreateoDTO oDto = new RequestCreateoDTO();
        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDto.setResponseCode(MessageCode.APP0003.getrCode());
            oDto.getMessageList().add(MessageCode.APP0003.getmMsg());
            oDto.getMessageList().addAll(e.getMultiExceptions());
            return oDto;
        }

        Optional<RPRequest> oRq = requestRepo.findByRequestCode(iDto.getRequestCode());
        if (oRq.isPresent()) {
            throw new ItemAlreadyExistsException(iDto.getRequestCode());
        }
        RPRequest rpRequest = new RPRequest();
        rpRequest.setRequestCode(iDto.getRequestCode());
        rpRequest.setRequestDescription(iDto.getRequestDescription());
        rpRequest.setSourceSystem(String.valueOf(iDto.getSourceSystem()));
        rpRequest.setSourceURL(iDto.getSourceURL());
        rpRequest.setSourceUser(iDto.getSourceUser());
        rpRequest.setSourceCred(iDto.getSourceCred());
        rpRequest.setSourceSchema(iDto.getSourceSchema());
        rpRequest.setSourceTempSchema(iDto.getSourceTempSchema());
        rpRequest.setTargetSystem(String.valueOf(iDto.getTargetSystem()));
        rpRequest.setTargetURL(iDto.getTargetURL());
        rpRequest.setTargetUser(iDto.getTargetUser());
        rpRequest.setTargetCred(iDto.getTargetCred());
        rpRequest.setTargetDriver(iDto.getTargetDriver());
        rpRequest.setTargetSchema(iDto.getTargetSchema());
        rpRequest.setTargetDropBefore(iDto.getTargetDropBefore());
        rpRequest.setTargetClearBefore(iDto.getTragetClearBefore());
        rpRequest.setBatchSize(iDto.getBatchSize());
        requestRepo.save(rpRequest);

        oDto.setResponseCode(MessageCode.APP0000.getrCode());
        oDto.getMessageList().add(MessageCode.APP0000.getmMsg());
        oDto.getMessageList().add(iDto.getRequestCode() + " created.");

        return oDto;
    }

    @Transactional(readOnly = true)
    public RequestListoDTO listRequests() throws AppException {
        RequestListoDTO oDto = new RequestListoDTO();
        oDto.getRequestList().addAll(requestRepo.listOfRequests());
        oDto.setResponseCode(MessageCode.APP0000.getrCode());
        oDto.getMessageList().add(MessageCode.APP0000.getmMsg());
        return oDto;
    }

    @Transactional
    public FileCreateoDto addFileToRequest(FileCreateiDto iDto) throws AppException {
        FileCreateoDto oDto = new FileCreateoDto();
        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDto.setResponseCode(MessageCode.APP0003.getrCode());
            oDto.getMessageList().add(MessageCode.APP0003.getmMsg());
            oDto.getMessageList().addAll(e.getMultiExceptions());
            return oDto;
        }

        Optional<RPRequest> oRq = requestRepo.findByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            throw new ItemNotFoundException(iDto.getRequestCode());
        }
        Optional<RPFile> oFl = fileRepo.findByRequestAndFile(oRq.get(), iDto.getFileName().toLowerCase());
        if (oFl.isPresent()) {
            throw new ItemAlreadyExistsException(iDto.getFileName() + " in " + iDto.getRequestCode());
        }

        RPFile file = new RPFile();
        file.setRequest(oRq.get());
        file.setFileName(iDto.getFileName());
        file.setFileOrder(iDto.getFileOrder());
        fileRepo.save(file);
        oDto.setResponseCode(MessageCode.APP0000.getrCode());
        oDto.getMessageList().add(MessageCode.APP0000.getmMsg());
        return oDto;
    }

    @Transactional
    public FileUpdateoDto changeFile(FileUpdateiDto iDto) throws AppException {
        FileUpdateoDto oDto = new FileUpdateoDto();
        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDto.setResponseCode(MessageCode.APP0003.getrCode());
            oDto.getMessageList().add(MessageCode.APP0003.getmMsg());
            oDto.getMessageList().addAll(e.getMultiExceptions());
            return oDto;
        }

        Optional<RPRequest> oRq = requestRepo.findByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            throw new ItemNotFoundException(iDto.getRequestCode());
        }
        Optional<RPFile> oFl = fileRepo.findByRequestAndFile(oRq.get(), iDto.getFileName().toLowerCase());
        if (!oFl.isPresent()) {
            throw new ItemNotFoundException(iDto.getFileName() + " in " + iDto.getRequestCode());
        }

        RPFile file = oFl.get();
        file.setFileOrder(iDto.getFileOrder());
        fileRepo.save(file);
        oDto.setResponseCode(MessageCode.APP0000.getrCode());
        oDto.getMessageList().add(MessageCode.APP0000.getmMsg());
        return oDto;
    }

    public FileDeleteoDto removeFile(FileDeleteiDto iDto) throws ItemNotFoundException {
        FileDeleteoDto oDto = new FileDeleteoDto();
        try {
            Validators.validate(iDto);
        } catch (ValidationJsonException e) {
            oDto.setResponseCode(MessageCode.APP0003.getrCode());
            oDto.getMessageList().add(MessageCode.APP0003.getmMsg());
            oDto.getMessageList().addAll(e.getMultiExceptions());
            return oDto;
        }

        Optional<RPRequest> oRq = requestRepo.findByRequestCode(iDto.getRequestCode());
        if (!oRq.isPresent()) {
            throw new ItemNotFoundException(iDto.getRequestCode());
        }
        Optional<RPFile> oFl = fileRepo.findByRequestAndFile(oRq.get(), iDto.getFileName().toLowerCase());
        if (!oFl.isPresent()) {
            throw new ItemNotFoundException(iDto.getFileName() + " in " + iDto.getRequestCode());
        }

        fileRepo.delete(oFl.get());
        oDto.setResponseCode(MessageCode.APP0000.getrCode());
        oDto.getMessageList().add(MessageCode.APP0000.getmMsg());
        return oDto;
    }
}
