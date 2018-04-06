package net.machtalk.xcloud.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.ast.Chunk;
import org.luaj.vm2.ast.Exp;
import org.luaj.vm2.ast.Stat;
import org.luaj.vm2.ast.Visitor;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.parser.LuaParser;
import org.luaj.vm2.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhaop
 * Date: 17-2-14
 * Time: 下午1:57
 * To change this template use File | Settings | File Templates.
 */
public class TestLua {

    public static void main(String[] args) {
        try {
            final String file = "src/Main.lua";


            Globals globals = JsePlatform.standardGlobals();

            LuaValue chunk1 = globals.loadfile(file);
            chunk1.call();
            String para = "aabbaaaaaa";
            //chunk1.method("from_private", LuaValue.valueOf(para));
            Varargs res = globals.invokemethod("from_private", LuaValue.valueOf(para));
            //Varargs res = chunk1.invokemethod("from_private", LuaValue.valueOf(para));

            System.out.println(res.tojstring());

            // Create a LuaParser. This will fill in line and column number
            // information for most exceptions.
            LuaParser parser = new LuaParser(new FileInputStream(file));

            // Perform the parsing.
            Chunk chunk = parser.Chunk();


            // Print out line info for all function definitions.
            chunk.accept( new Visitor() {
                public void visit(Exp.AnonFuncDef exp) {
                    System.out.println("Anonymous function definition at "
                            + exp.beginLine + "." + exp.beginColumn + ","
                            + exp.endLine + "." + exp.endColumn);
                }

                public void visit(Stat.FuncDef stat) {
                    System.out.println("Function definition '" + stat.name.name.name + "' at "
                            + stat.beginLine + "." + stat.beginColumn + ","
                            + stat.endLine + "." + stat.endColumn);

                    System.out.println("\tName location "
                            + stat.name.beginLine + "." + stat.name.beginColumn + ","
                            + stat.name.endLine + "." + stat.name.endColumn);
                }

                public void visit(Stat.LocalFuncDef stat) {
                    System.out.println("Local function definition '" + stat.name.name + "' at "
                            + stat.beginLine + "." + stat.beginColumn + ","
                            + stat.endLine + "." + stat.endColumn);
                }
            } );

        } catch ( ParseException e ) {
            System.out.println("parse failed: " + e.getMessage() + "\n"
                    + "Token Image: '" + e.currentToken.image + "'\n"
                    + "Location: " + e.currentToken.beginLine + ":" + e.currentToken.beginColumn
                    + "-" + e.currentToken.endLine + "," + e.currentToken.endColumn);

        } catch ( IOException e ) {
            System.out.println( "IOException occurred: "+e );
            e.printStackTrace();
        }
    }

}
