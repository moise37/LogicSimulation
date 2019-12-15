package com.im.logicsimulator;

import java.io.Serializable;

public interface Node extends Serializable {
    float getOutputPositionX();
    float getOutputPositionY();
    boolean eval();
}
