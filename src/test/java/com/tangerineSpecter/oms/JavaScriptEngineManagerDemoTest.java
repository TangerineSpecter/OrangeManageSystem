package com.tangerinespecter.oms;

import cn.hutool.core.convert.Convert;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class JavaScriptEngineManagerDemoTest {

    public static void main(String[] args) {
        ThsAesJs();
    }

    /**
     * 获取同花顺加密cookie
     *
     * @return
     */
    public static String ThsAesJs() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {
            // 读js文件
            String jsFile = "";
            FileInputStream fileInputStream = new FileInputStream(new File(jsFile));
            Reader scriptReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            engine.eval(scriptReader);
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                System.out.println(in.invokeFunction("v"));
                return Convert.toStr(in.invokeFunction("v"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
