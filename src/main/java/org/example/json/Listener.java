package org.example.json;

import java.util.*;

public class Listener extends JSONBaseListener {


    private Object result;
    private final Stack<Object> stack = new Stack<>();

    public Object getResult() {
        return result;
    }

    @Override
    public void enterJson(JSONParser.JsonContext ctx) {
        stack.clear();
    }

    @Override
    public void exitJson(JSONParser.JsonContext ctx) {
        result = stack.pop();
    }

    @Override
    public void enterObj(JSONParser.ObjContext ctx) {
        stack.push(new HashMap<>());
    }

    @Override
    public void exitPair(JSONParser.PairContext ctx) {
        Object value = stack.pop();
        String key = ctx.STRING().getText().replaceAll("^\"|\"$", "");
        Map<String, Object> obj = (Map<String, Object>) stack.peek();
        obj.put(key, value);
    }

    @Override
    public void exitArr(JSONParser.ArrContext ctx) {
        List<Object> array = new ArrayList<>();
        while (!stack.isEmpty() && !(stack.peek() instanceof Map || stack.peek() instanceof List)) {
            array.add(0, stack.pop());
        }
        stack.push(array);
    }

    @Override
    public void exitValue(JSONParser.ValueContext ctx) {
        if (ctx.STRING() != null) {
            stack.push(ctx.STRING().getText().replaceAll("^\"|\"$", ""));
        } else if (ctx.NUMBER() != null) {
            stack.push(Double.parseDouble(ctx.NUMBER().getText()));
        } else if (ctx.obj() != null || ctx.arr() != null) {
            // Object or array is already on the stack
        } else if (ctx.getText().equals("true")) {
            stack.push(true);
        } else if (ctx.getText().equals("false")) {
            stack.push(false);
        } else if (ctx.getText().equals("null")) {
            stack.push(null);
        }
    }
}


