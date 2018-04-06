package net.machtalk.xcloud.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 17-2-14
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class TestLua1 {

    /**
     * 参考文档
     * http://www.luaj.org/luaj/3.0/README.html
     * @param args
     */
    public static void main(String[] args) {
        try {
            final String file = "src/Main.lua";


            Globals globals = JsePlatform.standardGlobals();

            String para = "aabbaaaaaa";
            LuaValue chunk = globals.loadfile(file);
            chunk.call();

            //chunk1.method("from_private", LuaValue.valueOf(para));
            Varargs res = globals.invokemethod("to_private", LuaValue.valueOf(para));
            Varargs res1 = globals.invokemethod("from_private", LuaValue.valueOf(para));

            System.out.println(res.tojstring());
            System.out.println(res1.tojstring());
            String did = "gome1234567";
            Integer busModelId = 1222;
            String gid = "cacaca";
            String cache = "{\"cmd\":\"cache\",\"data\":{\"type\":\"device\",\"did\":\"" + did + "\",\"data\":{\"busModelId\":" + busModelId + ",\"gid\":\"" + gid +"\"}}}";
            System.out.println(cache);
        } catch ( Exception e ) {
            System.out.println( "IOException occurred: "+e );
            e.printStackTrace();
        }
    }

}
