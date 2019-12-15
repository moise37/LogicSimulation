package com.im.logicsimulator;

import android.content.res.Resources;

public class XNORGate extends TwoInputTool implements Node {

    public XNORGate(Resources res, int image, GridRect grid) {
        super(res, image, grid);
    }

    public boolean eval() {
        if (this.a == null && this.b == null) {
            return true;
        }
        if (this.a == null || this.b == null) {
            return false;
        }
        if (a.eval() && b.eval()) {
            return true;
        }
        return !a.eval() && !b.eval();
    }
}

