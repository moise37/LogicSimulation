package com.im.logicsimulator;

import java.util.HashSet;

public interface RemoveConnection {
    HashSet<Wire> getWires();
    void removeTool(Tool toolToDelete);
}
