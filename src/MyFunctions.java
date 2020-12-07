

import java.util.List;

public class MyFunctions {
    //运算符指令
    //目前只支持int
    public static void operatorInstructions(TokenType calculate, List<Instruction> instructions) {
        Instruction instruction;
        switch (calculate) {
            //+
            case PLUS:
                instruction = new Instruction("add.i", null);
                instructions.add(instruction);
                break;
            //-
            case MINUS:
                instruction = new Instruction("sub.i", null);
                instructions.add(instruction);
                break;
            //*
            case MUL:
                instruction = new Instruction("mul.i", null);
                instructions.add(instruction);
                break;
            ///
            case DIV:
                instruction = new Instruction("div.i", null);
                instructions.add(instruction);
                break;
            //==
            case EQ:
                instruction = new Instruction("cmp.i", null);
                instructions.add(instruction);
                instruction = new Instruction("not", null);
                instructions.add(instruction);
                break;
            //!=
            case NEQ:
                instruction = new Instruction("cmp.i", null);
                instructions.add(instruction);
                break;
            //<
            case LT:
                instruction = new Instruction("cmp.i", null);
                instructions.add(instruction);
                instruction = new Instruction("set.lt", null);
                instructions.add(instruction);
                break;
            //>
            case GT:
                instruction = new Instruction("cmp.i", null);
                instructions.add(instruction);
                instruction = new Instruction("set.gt", null);
                instructions.add(instruction);
                break;
            //<=
            case LE:
                instruction = new Instruction("cmp.i", null);
                instructions.add(instruction);
                instruction = new Instruction("set.gt", null);
                instructions.add(instruction);
                instruction = new Instruction("not", null);
                instructions.add(instruction);
                break;
            //>=
            case GE:
                instruction = new Instruction("cmp.i", null);
                instructions.add(instruction);
                instruction = new Instruction("set.lt", null);
                instructions.add(instruction);
                instruction = new Instruction("not", null);
                instructions.add(instruction);
                break;
            default:
                break;
        }

    }

    /**
     * 根据函数名字得到函数存储的id
     * @param name
     * @param functionTable
     * @return
     */
    public static int getFunctionId(String name, List<Function> functionTable){
        for (int i=0 ; i<functionTable.size(); i++) {
            if (functionTable.get(i).getName().equals(name)) return i;
        }
        return -1;
//        for (Function function : functionTable) {
//            if (function.getName().equals(name)) return function.getId();
//        }
//        return -1;
    }

    /**
     * 判断函数有没有返回值
     * @param name
     * @param functionTable
     * @return
     */
    public static boolean functionHasReturn(String name, List<Function> functionTable) {
        //如果是库函数
        if (name.equals("getint") || name.equals("getdouble") || name.equals("getchar"))
                return true;
        //如果是自定义函数
        for (Function function : functionTable) {
            if (function.getName().equals(name)) {
                if (function.getReturnType().equals("int") || function.getReturnType().equals("double")) return true;
            }
        }
        return false;
    }
}
