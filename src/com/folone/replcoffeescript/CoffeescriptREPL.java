package com.folone.replcoffeescript;

import com.folone.Evaluator;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.jcoffeescript.Option;
import org.jcoffeescript.JCoffeeScriptCompiler;
import java.util.LinkedList;
import java.util.Collection;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CoffeescriptREPL extends Service {

    private final Evaluator.Stub evaluator = new Evaluator.Stub() {
        private JCoffeeScriptCompiler compiler;
        private Context context;
        private Scriptable scope;

        public String evaluate(String script) throws RemoteException {
          if (context == null) {
            context = Context.enter();
            scope = context.initStandardObjects();
          }

          try {
            return Context.toString(context.evaluateString(scope, compile(script), "<cmd>", 1, null));
          } catch (Exception e) {
            return e.getMessage();
          }
        }

        private String compile(String script) throws Exception {
          if (compiler == null) {
            Collection<Option> options = new LinkedList<Option>();
            options.add(Option.BARE);
            compiler = new JCoffeeScriptCompiler(options);
          }

          return compiler.compile(script);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return evaluator;
    }

}
