#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>

//输入串
char line[1002];
//符号栈
char sign[1002];
//输入串的读入位置
int topL = 0;
//符号栈的栈顶
int topS = 0;
//符号栈的最后一个终结符号
int lastS = 0;
//算符优先矩阵
int priority[5][5] = {
	1, -1, -1, 1, -1, 
	1, 1, -1, 1, -1, 
	-1, -1, -1, 0, -1, 
	1, 1, 2, 1, 2, 
	1, 1, 2, 1, 2 };

//算符优先判断
int compare(char a, char b){
	int x, y;
	if(a == '+') x = 0;
	else if(a == '*') x = 1;
	else if(a == '(') x = 2;
	else if(a == ')') x = 3;
	else if(a == 'i') x = 4;
	else return 2;

	if(b == '+') y = 0;
	else if(b == '*') y = 1;
	else if(b == '(') y = 2;
	else if(b == ')') y = 3;
	else if(b == 'i') y = 4;
	else return 2;

	return priority[x][y];
}

//找符号栈里的最后一个终结符号
void findLastTerminal(){
	int l = topS;
	while(sign[l] == 'E'){
		l--;
	}
	lastS = l;
}

//规约
int doUp(){
	//E -> E+E
	//E -> E*E
	//E -> i
	if(sign[topS] == 'i') {
		sign[topS] = 'E';
		printf("R\n");
	}
	else if(topS >= 2 && sign[topS] == 'E' && (sign[topS-1]=='+'||sign[topS-1]=='*') && sign[topS-2]=='E'){
		sign[topS-1] = '\0';
		sign[topS] = '\0';
		topS = topS-2;
		printf("R\n");
	}
	else if(topS >= 2 && sign[topS] == ')' && sign[topS-1]=='E' && sign[topS-2]=='('){
		sign[topS-1] = '\0';
		sign[topS] = '\0';
		sign[topS-2] = 'E';
		topS = topS-2;
		printf("R\n");
	}
	else{
		// printf("RE\n");
		return 0;
	} 
	return 1;
}

//开始算符优先分析
void begin(){
	while(line[topL]!='\n'){
		findLastTerminal();
		int p = compare(sign[lastS], line[topL]);

		//如果符号栈内优先级低，或者符号栈里只有#，入栈，输出I
		if(p == -1 || sign[lastS] == '#'){
			sign[++topS] = line[topL];
			printf("I%c\n", line[topL++]);
		}

		//如果符号栈内优先级高，规约
		else if(p == 1 || line[topL] == '#'){
			if(doUp() == 0) break;
		}

		//如果优先级相同，则删除这两个符号
		else if(p == 0){
			//删除符号栈里最后一个终结符号，并且略过输入串的现在这个符号，继续读入输入串的下一个
			for(int i=lastS; i<topS; i++) sign[i] = sign[i+1];
			topL++;
			printf("I)\n");
			printf("R\n");
		}

		//不能识别或无法比较符号优先关系的栈顶和读入符号，输出一行E
		else{
			printf("E\n");
			break;
		}
		if(sign[lastS] == '#' && line[topL] == '#') break;
	}
}

int main(int argc, char *argv[]){
	FILE *file = fopen(argv[1],"r");
	if(file == NULL) printf("error\n"); 
	fgets(line,1000,file);
	//让符号栈的第一个是#，输入串的最后一个是#
	sign[0] = '#';
	line[strlen(line)-2] = '#';

	//设置算符优先矩阵
	//暂时手动输入

	//开始分析
	begin();

	//判断是否结束失败
	if(sign[0] != '#' || sign[1] != 'E' || strlen(sign) != 2) printf("RE\n");

	//结束准备
	fclose(file);
	return 0;
}
