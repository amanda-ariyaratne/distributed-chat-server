package org;

import org.kohsuke.args4j.Option;

public class CmdValues {

    @Option(required = true, name = "-n", usage = "n=Server ID")
    private String serverId = "1";

    public String getServerId() {
        return serverId;
    }
}
