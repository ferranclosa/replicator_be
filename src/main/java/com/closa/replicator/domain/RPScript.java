package com.closa.replicator.domain;

import com.closa.replicator.domain.enums.ExecutionCycle;
import com.closa.replicator.domain.enums.ScriptTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="rp_script")
public class RPScript  implements EntityCommon{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SCRIPT_ID")
    @TableGenerator(name = "SCRIPT_ID", table = "GEN_ID",
            pkColumnName = "key_name", valueColumnName = "key_val",
            pkColumnValue = "SCRIPT_ID", initialValue = 1, allocationSize = 5)

    private Long id;

    public RPScript() {
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private RPFile rpFile;

    @Enumerated( value = EnumType.STRING)
    private ExecutionCycle rpCycle;

    @Enumerated(value= EnumType.STRING)
    private ScriptTime executionTime;

    @Column( nullable= false)
    private String executionSQL;



    public RPFile getRpFile() {
        return rpFile;
    }

    public void setRpFile(RPFile rpFile) {
        this.rpFile = rpFile;
    }


    public void setExecutionTime(ScriptTime executionTime) {
        this.executionTime = executionTime;
    }

    public RPScript(RPFile rpFile, ExecutionCycle rpCycle, ScriptTime executionTime, String executionSQL) {
        this.rpFile = rpFile;
        this.rpCycle = rpCycle;
        this.executionTime = executionTime;
        this.executionSQL = executionSQL;
    }

    public ScriptTime getExecutionTime() {
        return executionTime;
    }

    public String getExecutionSQL() {
        return executionSQL;
    }

    public ExecutionCycle getRpCycle() {
        return rpCycle;
    }

    public void setRpCycle(ExecutionCycle rpCycle) {
        this.rpCycle = rpCycle;
    }

    public void setExecutionSQL(String executionSQL) {
        this.executionSQL = executionSQL;
    }
}
