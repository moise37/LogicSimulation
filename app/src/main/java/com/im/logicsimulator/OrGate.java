package com.im.logicsimulator;
import android.content.res.Resources;

public class OrGate extends TwoInputTool implements Node {
    public OrGate(Resources res, int image, GridRect grid) {
        super(res, image, grid);
    }

    public boolean eval() {
        if (this.a == null && this.b == null) {
            return false;
        } else if (this.a != null && this.b == null) {
            return a.eval();
        } else if (this.a == null) {
            return b.eval();
        } else {
            return a.eval() || b.eval();
        }
    }
}
