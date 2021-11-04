package com.premium.calc.service.vo;

import java.util.Collection;

/**
 * Represents object covered by policy, usually property.<br/>
 */
public class PolicyObject {
    private final String name;
    /** Collection of particular subobjects  */
    private final Collection<PolicySubObject> subObjects;

    public PolicyObject(String name, Collection<PolicySubObject> subObjects) {
        this.name = name;
        this.subObjects = subObjects;
    }

    public String getName() {
        return name;
    }

    public Collection<PolicySubObject> getSubObjects() {
        return subObjects;
    }
}
