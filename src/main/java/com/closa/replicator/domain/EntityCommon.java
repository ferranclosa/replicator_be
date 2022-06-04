/*
 * COPYRIGHT. HSBC HOLDINGS PLC 2018. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of HSBC Holdings plc.
 */
package com.closa.replicator.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public interface EntityCommon extends Serializable {
    /**
     * @return
     * @author fclosa
     */
    static final long serialVersionUID = -4272697409975053016L;

    default String toJson() {
        ObjectMapper om = new ObjectMapper();
        String theJson = null;
        try {
            theJson = om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            this.toString();
        }
        return theJson;
    }
}
