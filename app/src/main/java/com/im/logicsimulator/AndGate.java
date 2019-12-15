package com.im.logicsimulator;

import android.content.res.Resources;

public class AndGate extends TwoInputTool implements Node {

    public AndGate(Resources res, int image, GridRect grid) {
        super(res, image, grid);
    }

    public boolean eval() {
        if (this.a == null || this.b == null) {
            return false;
        }
        else{
            return a.eval() && b.eval();
        }
    }
}
