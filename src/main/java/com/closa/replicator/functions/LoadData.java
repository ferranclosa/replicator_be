package com.closa.replicator.functions;

import com.closa.replicator.dao.RPFileRepo;
import com.closa.replicator.dao.RPRequestRepo;
import com.closa.replicator.dao.RPScriptRepo;
import com.closa.replicator.domain.*;
import com.closa.replicator.domain.enums.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LoadData implements CommandLineRunner {
    @Autowired
    RPFileRepo rpFileRepo;
    @Autowired
    RPRequestRepo rpRequestRepo;

    @Autowired
    RPScriptRepo scriptRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        RPRequest rpRequest = new RPRequest();
        rpRequest.setRequestCode("HUB");
        rpRequest.setRequestDescription("HUB for testing HIE");
        rpRequest.setSourceSystem("as400");
        rpRequest.setSourceURL("poitou0a.systems.uk.hsbc;database name:poitou");
        rpRequest.setSourceUser("hubqry");
        rpRequest.setSourceCred("QRYHUB02");
        rpRequest.setSourceSchema("hfrhubfp");
        rpRequest.setSourceTempSchema("qtemp");
        rpRequest.setTargetSystem("mysql");
        rpRequest.setTargetURL("jdbc:mysql://localhost:3306/hub_hie?zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Europe/Paris");
        rpRequest.setTargetUser("root");
        rpRequest.setTargetCred("Martona78");
        rpRequest.setTargetDriver("com.mysql.cj.jdbc.Driver");
        rpRequest.setTargetSchema("doreen");
        rpRequest.setTargetDropBefore(true);
        rpRequest.setTargetClearBefore(true);
        rpRequest.setBatchSize(5000);
        rpRequestRepo.save(rpRequest);
        RPFile rpFile = null;
        RPExtraction rpExtraction = null;
        RPMethVALUES rpValues = null;
        RPMethMANY rpMany = null;
        RPMethWHERE rpMethWHERE = null;

        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileName("SSGMCPP");
        rpFile.setFileOrder(3);
        rpFile.addExtractionRule(new RPMethALL());
        rpFileRepo.save(rpFile);

        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileName("SSSTDPP");
        rpFile.setFileOrder(5);
        rpFile.addExtractionRule(new RPMethWHERE(" YHTBID in ('CT', 'MS', 'CO', 'VV')"));
        rpFile.addAScript(new RPScript(
                rpFile,
                ExecutionCycle.POP,
                ScriptTime.AFTER,
                "update qtemp.ssstdpp set YHGMAB= '' where yhgmab  <> 'HBFR'"));
        rpFile.addAScript(new RPScript(
                rpFile,
                ExecutionCycle.POP,
                ScriptTime.AFTER,
                "update qtemp.ssstdpp set YHCTCD= '' where yhctcd  <> 'FR'"));

        rpFileRepo.save(rpFile);

        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileName("SSGHCLP");
        rpFile.setFileOrder(10);
        rpFile.addExtractionRule(new RPMethVALUES("VQCSTY", "N"));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileName("SSBRCPP");
        rpFile.setFileOrder(20);
        rpFile.addExtractionRule(new RPMethWHERE(" XIBRNO in (24, 811)"));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(100);
        rpFile.setFileName("SSCUSTP");
        rpFile.addExtractionRule(new RPMethMANY("ZGGHCL", "SSGHCLP", "VQGHCL", LinkType.AND, 1));
        rpFile.addExtractionRule((new RPMethWHERE("ZGDCB in (24, 811)")));
//rpFile.addExtractionRule(new RPMethMANY("ZGDCB", "SSBRCPP", "XIBRNO", LinkType.AND, 2));
//rpFile.addExtractionRule(new RPMethLIMIT(150, LinkType.AND, 3));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(200);
        rpFile.setFileName("SSNICIP");
        rpFile.addExtractionRule(new RPMethMANY("XUCTCD", "SSCUSTP", "ZGCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("XUDCG", "SSCUSTP", "ZGDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("XUDCB", "SSCUSTP", "ZGDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("XUDCS", "SSCUSTP", "ZGDCS", LinkType.AND, 4));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(220);
        rpFile.setFileName("SSCPFCP");
        rpFile.addExtractionRule(new RPMethMANY("ZXCTCD", "SSCUSTP", "ZGCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("ZXDCG", "SSCUSTP", "ZGDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("ZXDCB", "SSCUSTP", "ZGDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("ZXDCS", "SSCUSTP", "ZGDCS", LinkType.AND, 4));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(240);
        rpFile.setFileName("SSCLMTP");
        rpFile.addExtractionRule(new RPMethMANY("ZFCTCD", "SSCPFCP", "ZXCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("ZFDCG", "SSCPFCP", "ZXDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("ZFDCB", "SSCPFCP", "ZXDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("ZFDCS", "SSCPFCP", "ZXDCS", LinkType.AND, 4));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(300);
        rpFile.setFileName("SSADDRP");
        rpFile.addExtractionRule(new RPMethMANY("ZBCTCD", "SSCUSTP", "ZGCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("ZBDCG", "SSCUSTP", "ZGDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("ZBDCB", "SSCUSTP", "ZGDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("ZBDCS", "SSCUSTP", "ZGDCS", LinkType.AND, 4));
//rpFile.addExtractionRule(new RPMethLIMIT(6, LinkType.AND, 5));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(400);
        rpFile.setFileName("SS@ADRP");
        rpFile.addExtractionRule(new RPMethMANY("ADRCTCD", "SSADDRP", "ZBCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("ADRDCG", "SSADDRP", "ZBDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("ADRDCB", "SSADDRP", "ZBDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("ADRDCS", "SSADDRP", "ZBDCS", LinkType.AND, 4));
        rpFile.addExtractionRule(new RPMethMANY("ADRADID", "SSADDRP", "ZBADID", LinkType.AND, 5));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(500);
        rpFile.setFileName("DDACMSP");
        rpFile.addExtractionRule(new RPMethMANY("DFCTCD", "SSCUSTP", "ZGCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("DFDCG", "SSCUSTP", "ZGDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("DFDCB", "SSCUSTP", "ZGDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("DFDCS", "SSCUSTP", "ZGDCS", LinkType.AND, 4));
        rpFile.addExtractionRule(new RPMethWHERE(" DFSTUS <> '5'", LinkType.AND, 6));
        rpFile.addExtractionRule(new RPMethWHERE(" DFRLBL <> 0", LinkType.AND, 7));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(600);
        rpFile.setFileName("DDSMCPP");
        rpFile.addExtractionRule(new RPMethMANY("DICTCD", "DDACMSP", "DFCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("DIGMAB", "DDACMSP", "DFGMAB", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("DIACB", "DDACMSP", "DFACB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("DIACS", "DDACMSP", "DFACS", LinkType.AND, 4));
        rpFile.addExtractionRule(new RPMethMANY("DIACX", "DDACMSP", "DFACX", LinkType.AND, 5));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(620);
        rpFile.setFileName("DDSTMTP");
        rpFile.addExtractionRule(new RPMethMANY("DHCTCD", "DDACMSP", "DFCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("DHGMAB", "DDACMSP", "DFGMAB", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("DHACB", "DDACMSP", "DFACB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("DHACS", "DDACMSP", "DFACS", LinkType.AND, 4));
        rpFile.addExtractionRule(new RPMethMANY("DHACX", "DDACMSP", "DFACX", LinkType.AND, 5));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(600);
        rpFile.setFileName("DCACMSP");
        rpFile.addExtractionRule(new RPMethMANY("D1CTCD", "DDACMSP", "DFCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("D1GMAB", "DDACMSP", "DFGMAB", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("D1ACB", "DDACMSP", "DFACB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("D1ACS", "DDACMSP", "DFACS", LinkType.AND, 4));
        rpFile.addExtractionRule(new RPMethMANY("D1ACX", "DDACMSP", "DFACX", LinkType.AND, 4));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(650);
        rpFile.setFileName("IPPDCPP");
        rpFile.addExtractionRule(new RPMethALL());
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(655);
        rpFile.setFileName("IPCHGPP");
        rpFile.addExtractionRule(new RPMethALL());
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(700);
        rpFile.setFileName("IPACMSP");
        rpFile.addExtractionRule(new RPMethMANY("IGCTCD", "SSCUSTP", "ZGCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("IGDCG", "SSCUSTP", "ZGDCG", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("IGDCB", "SSCUSTP", "ZGDCB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("IGDCS", "SSCUSTP", "ZGDCS", LinkType.AND, 4));
        rpFileRepo.save(rpFile);
        rpFile = new RPFile();
        rpFile.setRequest(rpRequest);
        rpFile.setFileOrder(1000);
        rpFile.setFileName("IPDCMSP");
        rpFile.addExtractionRule(new RPMethMANY("IHCTCD", "IPACMSP", "IGCTCD", LinkType.AND, 1));
        rpFile.addExtractionRule(new RPMethMANY("IHGMAB", "IPACMSP", "IGGMAB", LinkType.AND, 2));
        rpFile.addExtractionRule(new RPMethMANY("IHACB", "IPACMSP", "IGACB", LinkType.AND, 3));
        rpFile.addExtractionRule(new RPMethMANY("IHACS", "IPACMSP", "IGACS", LinkType.AND, 4));
        rpFile.addExtractionRule(new RPMethMANY("IHACX", "IPACMSP", "IGACX", LinkType.AND, 5));
        rpFileRepo.save(rpFile);
    }
}
