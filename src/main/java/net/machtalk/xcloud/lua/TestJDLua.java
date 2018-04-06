package net.machtalk.xcloud.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by zhaop on 2017/5/3.
 */
public class TestJDLua {
    public static void main(String[] args) {
        try {
            final String file = "src/demo.lua";


            Globals globals = JsePlatform.standardGlobals();

            String testAs = "{\"code\":0, \"streams\":[{\"stream_id\":\"beep_switch\",\"current_value\":\"0\"},{\"stream_id\":\"light\",\"current_value\":\"on\"}]}";

            LuaValue luachuck = globals.loadfile(file).call();
            //LuaValue luachuck = globals.load(new InputStreamReader(new FileInputStream(new File(file))), "chunkname").call();

            LuaValue func = globals.get(LuaValue.valueOf("jds2pri"));

            Varargs res = func.invoke(LuaValue.valueOf(testAs));

            //chunk1.method("from_private", LuaValue.valueOf(para));
            //Varargs res = globals.invokemethod("toPrivate", LuaValue.valueOf(testAs));
            //Varargs res1 = globals.invokemethod("fromPrivate", LuaValue.valueOf(para));

            System.out.println(res.tojstring());
            //System.out.println(res1.tojstring());
        } catch ( Exception e ) {
            System.out.println( "IOException occurred: "+e );
            e.printStackTrace();
        }
    }
}
