

import java.util.HashMap;
import java.util.Map;

public class Operation {
    public static Map<String, Integer> getOperations(){
        Map<String, Integer> operations = new HashMap<>();
        operations.put("nop", 0x00);
        operations.put("push", 0x01);
        operations.put("pop", 0x02);
        operations.put("popn", 0x03);
        operations.put("dup", 0x04);
        operations.put("loca", 0x0a);
        operations.put("arga", 0x0b);
        operations.put("globa", 0x0c);
        operations.put("load.8", 0x10);
        operations.put("load.16", 0x11);
        operations.put("load.32", 0x12);
        operations.put("load.64", 0x13);
        operations.put("store.8", 0x14);
        operations.put("store.16", 0x15);
        operations.put("store.32", 0x16);
        operations.put("store.64", 0x17);
        operations.put("alloc", 0x18);
        operations.put("free", 0x19);
        operations.put("stackalloc", 0x1a);
        operations.put("add.i", 0x20);
        operations.put("sub.i", 0x21);
        operations.put("mul.i", 0x22);
        operations.put("div.i", 0x23);
        operations.put("add.f", 0x24);
        operations.put("sub.f", 0x25);
        operations.put("mul.f", 0x26);
        operations.put("div.f", 0x27);
        operations.put("div.u", 0x28);
        operations.put("shl", 0x29);
        operations.put("shr", 0x2a);
        operations.put("and", 0x2b);
        operations.put("or", 0x2c);
        operations.put("xor", 0x2d);
        operations.put("not", 0x2e);
        operations.put("cmp.i", 0x30);
        operations.put("cmp.u", 0x31);
        operations.put("cmp.f", 0x32);
        operations.put("neg.i", 0x34);
        operations.put("neg.f", 0x35);
        operations.put("itof", 0x36);
        operations.put("ftoi", 0x37);
        operations.put("shrl", 0x38);
        operations.put("set.lt", 0x39);
        operations.put("set.gt", 0x3a);
        operations.put("br", 0x41);
        operations.put("br.false", 0x42);
        operations.put("br.true", 0x43);
        operations.put("call", 0x48);
        operations.put("ret", 0x49);
        operations.put("callname", 0x4a);
        operations.put("scan.i", 0x50);
        operations.put("scan.c", 0x51);
        operations.put("scan.f", 0x52);
        operations.put("print.i", 0x54);
        operations.put("print.c", 0x55);
        operations.put("print.f", 0x56);
        operations.put("print.s", 0x57);
        operations.put("println", 0x58);
        operations.put("panic", 0xfe);
        return operations;
    }
}
