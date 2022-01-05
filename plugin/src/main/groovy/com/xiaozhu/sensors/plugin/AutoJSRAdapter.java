package com.ghl.sensors.plugin;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.JSRInlinerAdapter;

public class AutoJSRAdapter extends JSRInlinerAdapter {
    protected AutoJSRAdapter(int api, MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions) {
        super(api, mv, access, name, desc, signature, exceptions);
    }
}
