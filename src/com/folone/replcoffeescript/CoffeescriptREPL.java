package com.folone.replcoffeescript;

import com.folone.Evaluator;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import org.jcoffeescript.JCoffeeScriptCompiler;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CoffeescriptREPL extends Service {

    private final Evaluator.Stub evaluator = new Evaluator.Stub() {
        private JCoffeeScriptCompiler coffee = new JCoffeeScriptCompiler();
        private ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("JavaScript");

        public String evaluate(String script) throws RemoteException {

            String result = "Something went wrong.";
            try {
                result = engine.eval(coffee.compile(script)).toString();
            } catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return evaluator;
    }

}
