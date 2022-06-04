package com.closa.replicator.domain.enums;

import java.util.Comparator;

public class ExecCycleComparator implements Comparable<ExecutionCycle> {
    private String description;
    private int naturalOrder;

    public ExecCycleComparator() {
    }

    public int getNaturalOrder() {
        return naturalOrder;
    }

    @Override
    public int compareTo(ExecutionCycle o) {
        return 0;
               // this.getNaturalOrder().compareTo(o.getNaturalOrder());
    }


}
