package net.machtalk.xcloud.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by zhaop on 2017/5/3.
 */
public class TestGomeLua {
    public static void main(String[] args) {
        try {
            final String file = "src/gome.lua";


            Globals globals = JsePlatform.debugGlobals();

            String testAs = "{\"cmd\":\"opt\",\"mid\":\"12345\",\"as\":{\"1\":0,\"2\":1,\"3\":2,\"4\":2,\"5\":3,\"6\":100}}";


            LuaValue luachuck = globals.loadfile(file).call();
            //LuaValue luachuck = globals.load(new InputStreamReader(new FileInputStream(new File(file))), "chunkname").call();

            Varargs res  = globals.get(LuaValue.valueOf("toPrivate")).invoke(LuaString.valueOf(testAs));
            System.out.println(res.tojstring());
            Varargs res1  = globals.get(LuaValue.valueOf("fromPrivate")).invoke(LuaString.valueOf(res.tojstring()));
            System.out.println(res1.tojstring());


            //Varargs res = globals.invokemethod(LuaValue.valueOf("toPrivate"), LuaString.valueOf(testAs));
            //Varargs res1 = globals.invokemethod("fromPrivate", LuaValue.valueOf(res.tojstring()));

        } catch ( Exception e ) {
            System.out.println( "IOException occurred: "+e );
            e.printStackTrace();
        }

    }

}
